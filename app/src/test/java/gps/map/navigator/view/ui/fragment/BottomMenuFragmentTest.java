package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.navigation.NavigationView;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.R;
import gps.map.navigator.presenter.interfaces.Presenter;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

public class BottomMenuFragmentTest {
    private LayoutInflater inflater;
    private View view;
    private NavigationView navigationView;
    private NavigationView.OnNavigationItemSelectedListener listener;

    private SwitchCompat nightModeView;
    private SwitchCompat trafficModeView;
    private SwitchCompat satelliteModeView;

    private CompoundButton.OnCheckedChangeListener nightModeListener;
    private CompoundButton.OnCheckedChangeListener satelliteModeListener;
    private CompoundButton.OnCheckedChangeListener trafficModeListener;

    private Bundle bundle;
    private Presenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
        bundle = mock(Bundle.class);
        inflater = mock(LayoutInflater.class);
        view = mock(View.class);
        navigationView = mock(NavigationView.class);
        listener = mock(NavigationView.OnNavigationItemSelectedListener.class);
        Menu menu = mock(Menu.class);

        MenuItem nightModeMenu = mock(MenuItem.class);
        MenuItem trafficModeMenu = mock(MenuItem.class);
        MenuItem satelliteModeMenu = mock(MenuItem.class);

        nightModeView = mock(SwitchCompat.class);
        trafficModeView = mock(SwitchCompat.class);
        satelliteModeView = mock(SwitchCompat.class);

        nightModeListener = mock(CompoundButton.OnCheckedChangeListener.class);
        satelliteModeListener = mock(CompoundButton.OnCheckedChangeListener.class);
        trafficModeListener = mock(CompoundButton.OnCheckedChangeListener.class);

        when(inflater
                .inflate(eq(R.layout.fragment_bottomsheet), nullable(ViewGroup.class), anyBoolean()))
                .thenReturn(view);
        when(view.findViewById(eq(R.id.navigation_view))).thenReturn(navigationView);
        when(navigationView.getMenu()).thenReturn(menu);

        when(menu.findItem(eq(R.id.enable_night_mode))).thenReturn(nightModeMenu);
        when(menu.findItem(eq(R.id.enable_satelite))).thenReturn(satelliteModeMenu);
        when(menu.findItem(eq(R.id.enable_traffic))).thenReturn(trafficModeMenu);

        when(nightModeMenu.getActionView()).thenReturn(nightModeView);
        when(satelliteModeMenu.getActionView()).thenReturn(satelliteModeView);
        when(trafficModeMenu.getActionView()).thenReturn(trafficModeView);

        when(presenter.hasNightMode()).thenReturn(true);
        when(presenter.hasSatelliteMode()).thenReturn(true);
        when(presenter.hasTrafficMode()).thenReturn(true);
    }

    private BottomMenuFragment createFragment() {
        BottomMenuFragment fragment = new BottomMenuFragment();
        setInternalState(fragment, "listener", listener);
        setInternalState(fragment, "nightModeListener", nightModeListener);
        setInternalState(fragment, "satelliteModeListener", satelliteModeListener);
        setInternalState(fragment, "trafficModeListener", trafficModeListener);
        setInternalState(fragment, "presenter", presenter);
        return fragment;
    }

    @Test
    public void make_onCreateView_verify() {
        BottomMenuFragment fragment = createFragment();

        View root = fragment.onCreateView(inflater, null, null);
        NavigationView inner_navigationView = getInternalState(fragment, "navigationView");

        assertSame(view, root);
        assertSame(navigationView, inner_navigationView);
    }

    @Test
    public void make_onActivityCreated_verify() {
        BottomMenuFragment fragment = createFragment();
        fragment.onCreateView(inflater, null, null);

        fragment.onActivityCreated(bundle);

        verify(navigationView).setNavigationItemSelectedListener(eq(listener));

        verify(trafficModeView).setChecked(eq(true));
        verify(trafficModeView).setOnCheckedChangeListener(eq(trafficModeListener));

        verify(satelliteModeView).setChecked(eq(true));
        verify(satelliteModeView).setOnCheckedChangeListener(eq(satelliteModeListener));

        verify(nightModeView).setChecked(eq(true));
        verify(nightModeView).setOnCheckedChangeListener(eq(nightModeListener));
    }
}