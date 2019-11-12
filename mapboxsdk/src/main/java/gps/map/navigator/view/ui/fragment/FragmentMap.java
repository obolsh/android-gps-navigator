package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.maps.MapView;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.MapViewInstance;
import gps.navigator.mapboxsdk.MapViewProvider;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.MapReadyCallback;
import gps.navigator.mapboxsdk.callback.MapSdkProviderListenerImpl;

public class FragmentMap extends Fragment {
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
        setMapViewInstance();
        mapView.getMapAsync(new MapReadyCallback(new MapSdkProviderListenerImpl(cache)));
        return root;
    }

    private void setMapViewInstance() {
        MapViewProvider provider = MapViewProvider.getInstance();
        MapViewInstance instance = provider.getMapViewInstance();
        if (instance == null) {
            instance = new MapViewInstance(mapView);
            provider.setMapViewInstance(instance);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapView != null) {
            mapView.onDestroy();
        }
        cleanReferences();
    }

    private void cleanReferences() {
        MapSdkProvider.getInstance().setMapSdkInstance(null);
        MapViewProvider.getInstance().setMapViewInstance(null);
        mapView = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }
}
