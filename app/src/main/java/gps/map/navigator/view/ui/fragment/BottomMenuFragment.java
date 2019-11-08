package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class BottomMenuFragment extends BottomSheetDialogFragment {
    @Nullable
    private NavigationView navigationView;

    @Inject
    Presenter presenter;
    @Inject
    NavigationView.OnNavigationItemSelectedListener listener;
    @Inject
    @Named(Constants.NightModeListener)
    CompoundButton.OnCheckedChangeListener nightModeListener;
    @Inject
    @Named(Constants.SatelliteModeListener)
    CompoundButton.OnCheckedChangeListener satelliteModeListener;
    @Inject
    @Named(Constants.TrafficModeListener)
    CompoundButton.OnCheckedChangeListener trafficModeListener;
    @Inject
    Logger logger;

    @Inject
    public BottomMenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        navigationView = view.findViewById(R.id.navigation_view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(listener);
        }
        addCheckboxListeners();
    }

    private void addCheckboxListeners() {
        try {
            if (navigationView != null) {
                Menu menu = navigationView.getMenu();

                addNightModeListener(menu.findItem(R.id.enable_night_mode));
                addSatelliteModeListener(menu.findItem(R.id.enable_satelite));
                addTrafficModeListener(menu.findItem(R.id.enable_traffic));
            }
        } catch (Throwable t) {
            logger.error(t);
        }
    }

    private void addNightModeListener(@NonNull MenuItem menuItem) {
        SwitchCompat view = getView(menuItem);
        view.setChecked(presenter.hasNightMode());
        view.setOnCheckedChangeListener(nightModeListener);
    }

    @NonNull
    private SwitchCompat getView(@NonNull MenuItem menuItem) {
        return (SwitchCompat) menuItem.getActionView();
    }

    private void addSatelliteModeListener(@NonNull MenuItem menuItem) {
        SwitchCompat view = getView(menuItem);
        view.setChecked(presenter.hasSatelliteMode());
        view.setOnCheckedChangeListener(satelliteModeListener);
    }

    private void addTrafficModeListener(@NonNull MenuItem menuItem) {
        SwitchCompat view = getView(menuItem);
        view.setChecked(presenter.hasTrafficMode());
        view.setOnCheckedChangeListener(trafficModeListener);
    }
}
