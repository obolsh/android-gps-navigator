package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.maps.MapView;

import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.MapViewInstance;
import gps.navigator.mapboxsdk.MapViewProvider;

public abstract class MapboxFragment extends Fragment {


    protected abstract MapView getMapView();

    protected abstract void cleanReferences();

    protected void cacheMapViewInstance() {
        MapViewProvider provider = MapViewProvider.getInstance();
        MapViewInstance instance = provider.getMapViewInstance();
        if (instance == null) {
            instance = new MapViewInstance(getMapView());
            provider.setMapViewInstance(instance);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onDestroy();
        }
        cleanup();
    }



    private void cleanup() {
        MapSdkProvider.getInstance().setMapSdkInstance(null);
        MapViewProvider.getInstance().setMapViewInstance(null);
        cleanReferences();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MapView mapView = getMapView();
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }
}
