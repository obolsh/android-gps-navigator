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
import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.callback.FindMyPlaceCallback;
import gps.map.navigator.view.ui.callback.NextCallbackListener;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.viewmodel.DecorController;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, DecorController, Invalidator {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    IFragmentController<Fragment> fragmentController;
    @Inject
    Presenter presenter;

    private FloatingActionButton floatingActionButton;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton showMeOnMap;
    private NextCallbackListener nextCallbackListener;
    private FindMyPlaceCallback findMyPlaceCallback;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUiElements();
        openMapFragment();
    }

    private void setupUiElements() {
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        floatingActionButton = findViewById(R.id.fab);
        showMeOnMap = findViewById(R.id.show_me_on_map_fab);

        addCallbackListeners();
        setSupportActionBar(bottomAppBar);
    }

    private void addCallbackListeners() {
        nextCallbackListener = new NextCallbackListener(fragmentController);
        floatingActionButton.setOnClickListener(nextCallbackListener);
        findMyPlaceCallback = new FindMyPlaceCallback(presenter);
        showMeOnMap.setOnClickListener(findMyPlaceCallback);
    }

    private void openMapFragment() {
        fragmentController.openFragment(new MapFragment());
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
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
                openBottomMenuFragment();
                break;
            case R.id.app_bar_search:
                openFindPlaceFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBottomMenuFragment() {
        BottomMenuFragment drawerFragment = new BottomMenuFragment();
        drawerFragment.show(fragmentManager, drawerFragment.getTag());
    }

    private void openFindPlaceFragment() {
        fragmentController.openFragment(new FindPlaceFragment());
    }

    @Override
    public void setBottomBarVisibility(boolean visible) {
        bottomAppBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setFabAlignmentMode(int mode) {
        bottomAppBar.setFabAlignmentMode(mode);
    }

    @Override
    public void setFabVisibility(boolean visible) {
        if (visible) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    @Override
    public void setShowMeOnMapFabVisibility(boolean visible) {
        if (visible) {
            showMeOnMap.show();
        } else {
            showMeOnMap.hide();
        }
    }

    @Override
    protected void onDestroy() {
        invalidate();
        super.onDestroy();
    }

    @Override
    public void invalidate() {
        if (nextCallbackListener != null) {
            nextCallbackListener.invalidate();
        }
        if (findMyPlaceCallback != null) {
            findMyPlaceCallback.invalidate();
        }
        nextCallbackListener = null;
        findMyPlaceCallback = null;
        floatingActionButton = null;
        bottomAppBar = null;
        showMeOnMap = null;
    }
}
