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

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.strategy.PresenterStrategy;
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
    PresenterStrategy presenterStrategy;
    IRoute activeRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        presenterStrategy = PresenterStrategy.getInstance();
        bottomAppBar.setVisibility(View.VISIBLE);
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
        BuildRouteFragment buildRouteFragment = new BuildRouteFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, buildRouteFragment, buildRouteFragment.getTag());
        transaction.commit();
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
    }

    @Override
    public void backToBuildRouteScreen() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
    }
}
