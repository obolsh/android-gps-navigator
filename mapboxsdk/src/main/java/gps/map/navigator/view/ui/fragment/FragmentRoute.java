package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapView;

import javax.inject.Inject;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Cache;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.MapRouteBuilderCallback;


public class FragmentRoute extends MapboxFragment {


    @Nullable
    private MapView mapView;
    @Inject
    Cache cache;
    @Inject
    Logger logger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.route_fragment, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new MapRouteBuilderCallback(getContext(), cache, logger, mapView));
        return root;
    }

    @Override
    protected MapView getMapView() {
        return mapView;
    }

    @Override
    protected void cleanReferences() {
    }
}
