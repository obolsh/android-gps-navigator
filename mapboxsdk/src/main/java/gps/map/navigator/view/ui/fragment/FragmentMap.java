package gps.map.navigator.view.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.DeviceLocationProvider;
import gps.navigator.mapboxsdk.callback.MapReadyCallback;
import gps.navigator.mapboxsdk.callback.LastLocationProvider;
import gps.navigator.mapboxsdk.callback.MapSettingsProvider;
import gps.navigator.mapboxsdk.event.ChangeSettingsEvent;
import gps.navigator.mapboxsdk.event.MessageEvent;
import gps.navigator.mapboxsdk.event.RequestLocationEvent;

public class FragmentMap extends MapboxFragment {
    @Nullable
    private MapView mapView;
    @Inject
    Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_location_fragment, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        showLastLocation();
        return root;
    }

    private void showLastLocation() {
        try {
            if (mapView != null) {
                mapView.getMapAsync(new MapReadyCallback(new LastLocationProvider(getContext(), cache)));
            }
        } catch (Throwable t) {
            closeActivity();
        }
    }

    private void closeActivity() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override
    protected MapView getMapView() {
        return mapView;
    }

    @Override
    protected void cleanReferences() {
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (MessageEvent.TYPE_SHOW_LAST_LOCATION.equals(event.getMessageId())) {
            showLastLocation();
        } else if (MessageEvent.TYPE_SHOW_DEVICE_LOCATION.equals(event.getMessageId())) {
            showDeviceLocation(((RequestLocationEvent) event).getListener());
        } else if (MessageEvent.TYPE_CHANGE_MAP_SETTINGS.equals(event.getMessageId())) {
            changeMapStyle(((ChangeSettingsEvent)event).getSetting());
        }
    }

    private void showDeviceLocation(IPlaceListener listener) {
        try {
            if (mapView != null) {
                mapView.getMapAsync(new MapReadyCallback(new DeviceLocationProvider(getContext(), cache, listener)));
            }
        } catch (Throwable t) {
            closeActivity();
        }
    }

    private void changeMapStyle(MapSetting setting) {
        try {
            if (mapView != null) {
                mapView.getMapAsync(new MapReadyCallback(new MapSettingsProvider(setting)));
            }
        } catch (Throwable t) {
            closeActivity();
        }
    }
}
