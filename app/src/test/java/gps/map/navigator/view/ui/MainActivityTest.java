package gps.map.navigator.view.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.android.AndroidInjection;
import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.ICacheBundle;
import gps.map.navigator.model.interfaces.IDecorCache;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class, AndroidInjection.class})
public class MainActivityTest {

    private FragmentManager fragmentManager;
    private IFragmentController<Fragment> fragmentController;
    private Presenter presenter;
    private View.OnClickListener nextCallbackListener;
    private View.OnClickListener findMyPlaceCallback;
    private FloatingActionButton floatingActionButton;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton showMeOnMap;
    private Menu menu;
    private MenuInflater menuInflater;
    private MenuItem menuItem;
    private BottomMenuFragment bottomMenuFragment;
    private ICacheBundle cacheBundle;
    private IDecorCache decorCache;

    @Before
    public void setUp() throws Exception {
        mockStatic(AndroidInjection.class);
        fragmentManager = mock(FragmentManager.class);
        fragmentController = mock(IFragmentController.class);
        presenter = mock(Presenter.class);
        nextCallbackListener = mock(View.OnClickListener.class);
        findMyPlaceCallback = mock(View.OnClickListener.class);
        floatingActionButton = mock(FloatingActionButton.class);
        bottomAppBar = mock(BottomAppBar.class);
        showMeOnMap = mock(FloatingActionButton.class);
        menu = mock(Menu.class);
        menuInflater = mock(MenuInflater.class);
        menuItem = mock(MenuItem.class);
        bottomMenuFragment = mock(BottomMenuFragment.class);
        cacheBundle = mock(ICacheBundle.class);
        decorCache = mock(IDecorCache.class);

        whenNew(BottomMenuFragment.class).withAnyArguments().thenReturn(bottomMenuFragment);
        when(bottomMenuFragment.getTag()).thenReturn("foo");
        when(cacheBundle.getDecorCache()).thenReturn(decorCache);
    }

    private MainActivity createActivity() {
        MainActivity activity = mock(MainActivity.class, CALLS_REAL_METHODS);
        setInternalState(activity, "fragmentManager", fragmentManager);
        setInternalState(activity, "fragmentController", fragmentController);
        setInternalState(activity, "presenter", presenter);

        setInternalState(activity, "nextCallbackListener", nextCallbackListener);
        setInternalState(activity, "findMyPlaceCallback", findMyPlaceCallback);
        setInternalState(activity, "cacheBundle", cacheBundle);

        when(activity.getMenuInflater()).thenReturn(menuInflater);
        makeViewActive(activity);
        return activity;
    }

    private void makeViewActive(MainActivity activity) {
        when(activity.findViewById(eq(R.id.bottom_app_bar))).thenReturn(bottomAppBar);
        when(activity.findViewById(eq(R.id.fab))).thenReturn(floatingActionButton);
        when(activity.findViewById(eq(R.id.show_me_on_map_fab))).thenReturn(showMeOnMap);
    }

    @Test
    public void make_onCreate_initial_start_verify() {
        MainActivity activity = createActivity();

        activity.onCreate(mock(Bundle.class));

        verify(activity).setContentView(eq(R.layout.activity_main));
        verify(floatingActionButton).setOnClickListener(eq(nextCallbackListener));
        verify(showMeOnMap).setOnClickListener(eq(findMyPlaceCallback));
        verify(activity).setSupportActionBar(eq(bottomAppBar));
        verify(cacheBundle, times(2)).readBundle(nullable(Bundle.class));
        verify(fragmentController).openFragment(any(MapFragment.class));
    }

    @Test
    public void make_onCreate_restored_verify() {
        MainActivity activity = createActivity();
        when(cacheBundle.getActiveFragmentTag()).thenReturn("foo");

        activity.onCreate(mock(Bundle.class));

        verify(activity).setContentView(eq(R.layout.activity_main));
        verify(floatingActionButton).setOnClickListener(eq(nextCallbackListener));
        verify(showMeOnMap).setOnClickListener(eq(findMyPlaceCallback));
        verify(activity).setSupportActionBar(eq(bottomAppBar));
        verify(cacheBundle, times(2)).readBundle(nullable(Bundle.class));
        verify(fragmentManager).findFragmentByTag(eq("foo"));
    }

    @Test
    public void make_onBackPressed_has_backstack_verify() {
        MainActivity activity = createActivity();
        when(fragmentManager.getBackStackEntryCount()).thenReturn(2);

        activity.onBackPressed();

        verify(fragmentManager).popBackStack();
    }

    @Test
    public void make_onBackPressed_no_backstack_verify() {
        MainActivity activity = createActivity();
        when(fragmentManager.getBackStackEntryCount()).thenReturn(0);

        activity.onBackPressed();

        verify(fragmentManager, times(0)).popBackStack();
    }

    @Test
    public void make_onCreateOptionsMenu_verify() {
        MainActivity activity = createActivity();

        activity.onCreateOptionsMenu(menu);

        verify(menuInflater).inflate(eq(R.menu.bottom_appbar_menu), eq(menu));
    }

    @Test
    public void make_onOptionsItemSelected_got_home_verify() {
        MainActivity activity = createActivity();
        when(menuItem.getItemId()).thenReturn(android.R.id.home);

        activity.onOptionsItemSelected(menuItem);

        verify(bottomMenuFragment).show(eq(fragmentManager), eq("foo"));
    }

    @Test
    public void make_onOptionsItemSelected_got_search_verify() {
        MainActivity activity = createActivity();
        when(menuItem.getItemId()).thenReturn(R.id.app_bar_search);

        activity.onOptionsItemSelected(menuItem);

        verify(fragmentController).openFragment(any(FindPlaceFragment.class));
    }

    @Test
    public void make_setBottomBarVisibility_verify() {
        MainActivity activity = createActivity();
        activity.onCreate(null);

        activity.setBottomBarVisibility(true);
        verify(bottomAppBar).setVisibility(View.VISIBLE);
        verify(decorCache).setButtomAppBarActive(eq(true));

        activity.setBottomBarVisibility(false);

        verify(bottomAppBar).setVisibility(View.INVISIBLE);
        verify(decorCache).setButtomAppBarActive(eq(false));
    }

    @Test
    public void make_setFabAlignmentMode_verify() {
        MainActivity activity = createActivity();
        activity.onCreate(null);

        activity.setFabAlignmentMode(123);
        verify(bottomAppBar).setFabAlignmentMode(eq(123));
        verify(decorCache).setFloatingActionButtonAlignent(eq(123));
    }

    @Test
    public void make_setFabVisibility_verify() {
        MainActivity activity = createActivity();
        activity.onCreate(null);

        activity.setFabVisibility(true);
        verify(floatingActionButton).show();
        verify(decorCache).setFloatingActionButtonActive(eq(true));

        activity.setFabVisibility(false);

        verify(floatingActionButton).hide();
        verify(decorCache).setFloatingActionButtonActive(eq(false));
    }

    @Test
    public void make_setShowMeOnMapFabVisibility_verify() {
        MainActivity activity = createActivity();
        activity.onCreate(null);

        activity.setShowMeOnMapFabVisibility(true);
        verify(showMeOnMap).show();
        verify(decorCache).setShowMeOnMapActive(eq(true));

        activity.setShowMeOnMapFabVisibility(false);

        verify(showMeOnMap).hide();
        verify(decorCache).setShowMeOnMapActive(eq(false));
    }

    @Test
    public void make_onDestroy_verify() {
        MainActivity activity = createActivity();
        activity.onCreate(null);

        activity.onDestroy();

        boolean nextCallbackListener_null = getInternalState(activity, "nextCallbackListener") == null;
        boolean findMyPlaceCallback_null = getInternalState(activity, "findMyPlaceCallback") == null;
        boolean floatingActionButton_null = getInternalState(activity, "floatingActionButton") == null;
        boolean bottomAppBar_null = getInternalState(activity, "bottomAppBar") == null;
        boolean showMeOnMap_null = getInternalState(activity, "showMeOnMap") == null;

        assertTrue(nextCallbackListener_null
                && findMyPlaceCallback_null
                && floatingActionButton_null
                && bottomAppBar_null
                && showMeOnMap_null);
    }
}