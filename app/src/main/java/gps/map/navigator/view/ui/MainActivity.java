package gps.map.navigator.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.ICacheBundle;
import gps.map.navigator.model.interfaces.IDecorCache;
import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.presenter.interfaces.Presenter;
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
    @Inject
    @Named(Constants.NextCallbackListener)
    View.OnClickListener nextCallbackListener;
    @Inject
    @Named(Constants.FindMyPlaceCallback)
    View.OnClickListener findMyPlaceCallback;
    @Inject
    ICacheBundle cacheBundle;
    @Nullable
    private FloatingActionButton floatingActionButton;
    @Nullable
    private BottomAppBar bottomAppBar;
    @Nullable
    private FloatingActionButton showMeOnMap;

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
        restoreUiElementsFromBundle(savedInstanceState);
        if (!restoreFragmentFromBundle(savedInstanceState)) {
            openMapFragment();
        }
    }

    private void setupUiElements() {
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        floatingActionButton = findViewById(R.id.fab);
        showMeOnMap = findViewById(R.id.show_me_on_map_fab);

        addCallbackListeners();
        setSupportActionBar(bottomAppBar);
    }

    private void addCallbackListeners() {
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(nextCallbackListener);
        }
        if (showMeOnMap != null) {
            showMeOnMap.setOnClickListener(findMyPlaceCallback);
        }
    }

    private void restoreUiElementsFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            cacheBundle.readBundle(savedInstanceState);
            IDecorCache decorCache = cacheBundle.getDecorCache();
            if (decorCache != null) {
                setFabVisibility(decorCache.isFloatingActionButtonActive());
                setBottomBarVisibility(decorCache.isButtomAppBarActive());
                setShowMeOnMapFabVisibility(decorCache.isShowMeOnMapActive());
                setFabAlignmentMode(decorCache.getFloatingActionButtonAlignent());
            }
        }
    }

    private void openMapFragment() {
        fragmentController.openFragment(new MapFragment());
    }

    private boolean restoreFragmentFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            cacheBundle.readBundle(savedInstanceState);
            String activeFragmentTag = cacheBundle.getActiveFragmentTag();
            if (activeFragmentTag != null && !activeFragmentTag.isEmpty()) {
                fragmentManager.findFragmentByTag(activeFragmentTag);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
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
        if (bottomAppBar != null) {
            bottomAppBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            cacheBundle.getDecorCache().setButtomAppBarActive(visible);
        }
    }

    @Override
    public void setFabAlignmentMode(int mode) {
        if (bottomAppBar != null) {
            bottomAppBar.setFabAlignmentMode(mode);
            cacheBundle.getDecorCache().setFloatingActionButtonAlignent(mode);
        }
    }

    @Override
    public void setFabVisibility(boolean visible) {
        if (floatingActionButton != null) {
            if (visible) {
                floatingActionButton.show();
            } else {
                floatingActionButton.hide();
            }
            cacheBundle.getDecorCache().setFloatingActionButtonActive(visible);
        }
    }

    @Override
    public void setShowMeOnMapFabVisibility(boolean visible) {
        if (showMeOnMap != null) {
            if (visible) {
                showMeOnMap.show();
            } else {
                showMeOnMap.hide();
            }
            cacheBundle.getDecorCache().setShowMeOnMapActive(visible);
        }
    }

    @Override
    protected void onDestroy() {
        invalidate();
        super.onDestroy();
    }

    @Override
    public void invalidate() {
        if (nextCallbackListener != null && nextCallbackListener instanceof Invalidator) {
            ((Invalidator) nextCallbackListener).invalidate();
        }
        if (findMyPlaceCallback != null && findMyPlaceCallback instanceof Invalidator) {
            ((Invalidator) findMyPlaceCallback).invalidate();
        }
        nextCallbackListener = null;
        findMyPlaceCallback = null;
        floatingActionButton = null;
        bottomAppBar = null;
        showMeOnMap = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.DecorCache, cacheBundle.getDecorCache());
        bundle.putString(Constants.FragmentTagCache, fragmentController.getActiveFragmentTag());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (canFindMyPlaceNow(requestCode, grantResults)) {
            findMyPlaceCallback.onClick(null);
        }
    }

    private boolean canFindMyPlaceNow(int requestCode, int[] grantResults) {
        return requestCode == Constants.REQUEST_ACCESS_FINE_LOCATION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
