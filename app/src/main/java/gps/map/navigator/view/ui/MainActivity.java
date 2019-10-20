package gps.map.navigator.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IViewInteraction;
import gps.map.navigator.view.ui.fragment.BottomDrawerFragment;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    IFragmentController<Fragment> fragmentController;
    @Inject
    IViewInteraction interaction;

    private FloatingActionButton floatingActionButton;
    private BottomAppBar bottomAppBar;
    private IRoute activeRoute;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

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
        prepareUiForMainSceen();

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
        BottomDrawerFragment drawerFragment = new BottomDrawerFragment();
        drawerFragment.show(fragmentManager, drawerFragment.getTag());
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
