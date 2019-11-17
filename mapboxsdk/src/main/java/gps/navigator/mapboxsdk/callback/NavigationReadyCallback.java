package gps.navigator.mapboxsdk.callback;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import java.util.List;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.navigator.mapboxsdk.BuildConfig;
import gps.navigator.mapboxsdk.interfaces.NavigationStatusListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationReadyCallback implements OnNavigationReadyCallback {

    private static final String WAS_IN_TUNNEL = "was_in_tunnel";
    private static final String WAS_NAVIGATION_STOPPED = "was_navigation_stopped";

    private Activity activity;
    private final NavigationView navigationView;
    private Cache cache;
    private NavigationStatusListener listener;

    public NavigationReadyCallback(Activity activity, Cache cache, NavigationView navigationView,
                                   NavigationStatusListener listener) {
        this.activity = activity;
        this.navigationView = navigationView;
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    public void onNavigationReady(boolean isRunning) {
        synchronized (navigationView) {
            updateNightMode();
            fetchRoute();
        }
    }

    private void updateNightMode() {
        if (wasNavigationStopped()) {
            updateWasNavigationStopped(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            activity.recreate();
        }
    }

    private void fetchRoute() {
        NavigationRoute.builder(activity)
                .accessToken(BuildConfig.MAPBOX_HASH)
                .origin(getOrigin())
                .destination(getDestination())
                .alternatives(true)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        DirectionsResponse body = response.body();
                        if (body != null) {
                            final List<DirectionsRoute> routes = body.routes();
                            if (!routes.isEmpty()) {
                                startNavigation(routes.get(0));
                            } else {
                                if (listener != null) {
                                    listener.onNavigationFailed();
                                }
                            }
                        } else {
                            if (listener != null) {
                                listener.onNavigationFailed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        if (listener != null) {
                            listener.onNavigationFailed();
                        }
                    }
                });
    }

    private Point getOrigin() {
        IMapPlace place = cache.getLastRoute().getOrigin();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private Point getDestination() {
        IMapPlace place = cache.getLastRoute().getDestination();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private void startNavigation(DirectionsRoute directionsRoute) {
        synchronized (navigationView) {
            if (directionsRoute == null) {
                return;
            }
            NavigationViewOptions options = NavigationViewOptions.builder()
                    .directionsRoute(directionsRoute)
                    .shouldSimulateRoute(shouldSimulateRoute())
                    .navigationListener(new NavigationListener() {
                        @Override
                        public void onCancelNavigation() {
                            navigationView.stopNavigation();
                            stopNavigation();
                        }

                        @Override
                        public void onNavigationFinished() {

                        }

                        @Override
                        public void onNavigationRunning() {

                        }
                    })
                    .progressChangeListener(new ProgressChangeListener() {
                        @Override
                        public void onProgressChange(Location location, RouteProgress routeProgress) {
                            boolean isInTunnel = routeProgress.inTunnel();
                            boolean wasInTunnel = wasInTunnel();
                            if (isInTunnel) {
                                if (!wasInTunnel) {
                                    updateWasInTunnel(true);
                                    updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                }
                            } else {
                                if (wasInTunnel) {
                                    updateWasInTunnel(false);
                                    updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                                }
                            }
                        }
                    })
                    .build();
            try {
                navigationView.startNavigation(options);
                if (listener != null) {
                    listener.onNavigationRunning();
                }
            } catch (Throwable t) {
                if (listener != null) {
                    listener.onNavigationFailed();
                }
            }
        }
    }

    private boolean shouldSimulateRoute() {
        return BuildConfig.DEBUG;
    }

    private void stopNavigation() {
        updateWasNavigationStopped(true);
        updateWasInTunnel(false);
    }

    private boolean wasInTunnel() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean(WAS_IN_TUNNEL, false);
    }

    private void updateWasInTunnel(boolean wasInTunnel) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(WAS_IN_TUNNEL, wasInTunnel);
        editor.apply();
    }

    private void updateCurrentNightMode(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        activity.recreate();
    }

    private boolean wasNavigationStopped() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean(WAS_NAVIGATION_STOPPED, false);
    }

    private void updateWasNavigationStopped(boolean wasNavigationStopped) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(WAS_NAVIGATION_STOPPED, wasNavigationStopped);
        editor.apply();
    }
}
