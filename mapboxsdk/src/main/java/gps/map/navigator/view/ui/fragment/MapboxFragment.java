package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.maps.MapView;

public abstract class MapboxFragment extends Fragment {

    protected abstract MapView getMapView();

    protected abstract void cleanReferences();

    @Override
    public void onResume() {
        super.onResume();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onPause();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MapView mapView = getMapView();
        if (mapView != null && !mapView.isDestroyed()) {
            mapView.onSaveInstanceState(outState);
        }
    }
}
