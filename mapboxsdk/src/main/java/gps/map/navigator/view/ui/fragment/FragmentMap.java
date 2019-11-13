package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapView;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.MapReadyCallback;
import gps.navigator.mapboxsdk.callback.MapSdkProviderListenerImpl;

public class FragmentMap extends MapboxFragment {
    @Nullable
    private MapView mapView;
    @Inject
    Cache cache;
    @Inject
    MapSdk mapSdk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_location_fragment, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        cacheMapViewInstance();
        mapView.getMapAsync(new MapReadyCallback(new MapSdkProviderListenerImpl(cache, mapSdk)));
        return root;
    }

    @Override
    protected MapView getMapView() {
        return mapView;
    }

    @Override
    protected void cleanReferences() {
        mapView = null;
    }
}
