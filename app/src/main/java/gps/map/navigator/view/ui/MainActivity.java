package gps.map.navigator.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IViewInteraction;
import gps.map.navigator.view.ui.fragment.BottomNavigationDrawerFragment;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;

public class MainActivity extends AppCompatActivity implements IViewInteraction {

    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    FragmentManager fragmentManager;
    @Inject
    Presenter presenterStrategy;
    IRoute activeRoute;

    private final int DEFAULT_SCREEN = 1;
    private final int BUILD_ROUTE_SCREEN = 2;
    private final int FIND_PLACE_SCREEN = 3;
    private final int NAVIGATION_SCREEN = 4;

    private int activeScreenType = DEFAULT_SCREEN;

    BuildRouteFragment buildRouteFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeRoute != null) {
                    MainActivity.this.startNavigate(activeRoute);
                } else {
                    MainActivity.this.buildRoute();
                }
            }
        });
        floatingActionButton.show();
        setSupportActionBar(bottomAppBar);
        fragmentManager = getSupportFragmentManager();
        bottomAppBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        backToMainScreen();
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BottomNavigationDrawerFragment bottomNavDrawerFragment =
                        new BottomNavigationDrawerFragment(this);
                bottomNavDrawerFragment.show(fragmentManager, bottomNavDrawerFragment.getTag());
                break;
            case R.id.app_bar_search:
                findPlaceAndShow();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMeOnMap(View view) {
        presenterStrategy.showMeOnMap(new IPlaceListener() {
            @Override
            public void onPlaceLocated(IMapPlace place) {

            }

            @Override
            public void onPlaceLocationFailed(Exception reason) {

            }
        });
    }

    @Override
    public void buildRoute() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        presenterStrategy.buildRoute(new IPlaceHistoryListener() {
            @Override
            public void onHistoryPlacesFound(List<IMapPlace> placeList) {

            }

            @Override
            public void onHistoryPlacesError(Exception reason) {

            }
        });
        buildRouteFragment = new BuildRouteFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, buildRouteFragment, buildRouteFragment.getTag());
        transaction.commit();
        activeScreenType = BUILD_ROUTE_SCREEN;
    }

    @Override
    public void startNavigate(IRoute route) {
        presenterStrategy.navigate(route, new IRouteListener() {
            @Override
            public void onRouteStarted(IRoute route) {

            }

            @Override
            public void onRouteStopped(IRoute route) {

            }

            @Override
            public void onRouteError(IRoute route, Exception reason) {

            }
        });
    }

    @Override
    public void findPlaceAndShow() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        floatingActionButton.hide();
        presenterStrategy.findAndShowPlace(new IPlaceShowListener() {
            @Override
            public void onPlaceShown(IMapPlace place) {

            }

            @Override
            public void onPlaceShowFailed(Exception reason) {

            }
        });
        activeScreenType = FIND_PLACE_SCREEN;
    }

    @Override
    public void findPlace() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        floatingActionButton.hide();
        presenterStrategy.findPlace(new IPlaceListener() {
            @Override
            public void onPlaceLocated(IMapPlace place) {

            }

            @Override
            public void onPlaceLocationFailed(Exception reason) {

            }
        });
        activeScreenType = FIND_PLACE_SCREEN;
    }

    @Override
    public void enableTrafficMode(boolean enable) {
        presenterStrategy.enableTraffic(enable);
    }

    @Override
    public void enableNightMode(boolean enable) {
        presenterStrategy.enableNightMode(enable);
    }

    @Override
    public void backToMainScreen() {
        floatingActionButton.show();
        bottomAppBar.setVisibility(View.VISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        if (BUILD_ROUTE_SCREEN == activeScreenType) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(buildRouteFragment);
            transaction.commit();
            activeScreenType = DEFAULT_SCREEN;
        }
    }

    @Override
    public void backToBuildRouteScreen() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        if (FIND_PLACE_SCREEN == activeScreenType) {
            buildRouteFragment = new BuildRouteFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, buildRouteFragment, buildRouteFragment.getTag());
            transaction.commit();
            activeScreenType = BUILD_ROUTE_SCREEN;
        }
    }
}
