package gps.map.navigator.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IViewInteraction;
import gps.map.navigator.view.ui.fragment.BottomNavigationDrawerFragment;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.controller.NaviFragmentController;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private BottomAppBar bottomAppBar;
    private FragmentManager fragmentManager;
    private IRoute activeRoute;
    private NaviFragmentController fragmentController;

    @Inject
    IViewInteraction interaction;

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
                moveToNextFragment();
            }
        });
        setSupportActionBar(bottomAppBar);
        fragmentManager = getSupportFragmentManager();

        prepareUiForMainSceen();

        fragmentController = new NaviFragmentController(fragmentManager);
    }

    @Override
    public void onBackPressed() {
        openMainScreenFragment();
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
                openMenuFragment();
                break;
            case R.id.app_bar_search:
                openSearchFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void moveToNextFragment() {
        if (activeRoute != null) {
            openNavigateFragment();
        } else {
            openBuildRouteFragment();
        }
    }

    public void openNavigateFragment() {
        interaction.startNavigate(activeRoute);
    }

    public void openBuildRouteFragment() {
        prepareUiForBuildRoute();
        interaction.buildRoute();
        fragmentController.openFragment(new BuildRouteFragment());
    }

    public void openMainScreenFragment() {
        prepareUiForMainSceen();
        interaction.backToMainScreen();
        fragmentController.backToLastFragment();
    }

    public void openMenuFragment() {
        BottomNavigationDrawerFragment bottomNavDrawerFragment =
                new BottomNavigationDrawerFragment();
        bottomNavDrawerFragment.show(fragmentManager, bottomNavDrawerFragment.getTag());
    }

    public void openSearchFragment() {
        prepareUiForFindPlace();
        interaction.findPlaceAndShow();
    }

    public void prepareUiForBuildRoute() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
    }

    public void prepareUiForFindPlace() {
        bottomAppBar.setVisibility(View.INVISIBLE);
        floatingActionButton.hide();
        interaction.findPlace();
    }

    public void prepareUiForMainSceen() {
        floatingActionButton.show();
        bottomAppBar.setVisibility(View.VISIBLE);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
    }
}
