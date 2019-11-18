package gps.map.navigator.view.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.NavigationReadyCallback;
import gps.navigator.mapboxsdk.interfaces.NavigationStatusListener;


public class FragmentNavigation extends Fragment {

    @Nullable
    private NavigationView navigationView;
    @Inject
    Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationView = view.findViewById(R.id.navigation_view_fragment);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(new NavigationReadyCallback(getActivity(), cache, navigationView,
                new NavigationListener(getActivity())), getInitialPosition());
    }

    private CameraPosition getInitialPosition() {
        return new CameraPosition.Builder()
                .target(getOrigin())
                .zoom(16)
                .build();
    }

    private LatLng getOrigin() {
        IMapPlace place = cache.getLastRoute().getOrigin();
        return new LatLng(place.getLatitude(), place.getLongitude());
    }


    @Override
    public void onStart() {
        super.onStart();
        if (navigationView != null) {
            navigationView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (navigationView != null) {
            navigationView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (navigationView != null) {
            navigationView.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (navigationView != null) {
                navigationView.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (navigationView != null) {
            navigationView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (navigationView != null) {
            navigationView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (navigationView != null) {
            navigationView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (navigationView != null) {
            navigationView.onDestroy();
        }
    }

    private static class NavigationListener implements NavigationStatusListener {

        private Activity activity;

        private NavigationListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onNavigationRunning() {
            activity = null;
        }

        @Override
        public void onNavigationFailed() {
            if (activity != null) {
                activity.onBackPressed();
            }
            activity = null;
        }
    }
}
