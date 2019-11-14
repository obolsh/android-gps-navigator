package gps.map.navigator.model.impl.sdk;

import android.content.Context;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;
import gps.navigator.mapboxsdk.MapSdkInstance;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.geocode.GeocodeStrategy;
import gps.navigator.mapboxsdk.geocode.impl.LocationIqGeocode;
import gps.navigator.mapboxsdk.map.MapProviderStategy;
import gps.navigator.mapboxsdk.map.MapboxMapProvider;

public class MapBoxSdkImpl implements MapSdk {
    @Inject
    @Named("application_context")
    Context context;
    @Inject
    Cache cache;
    @Inject
    Logger logger;

    @Inject
    public MapBoxSdkImpl() {
    }

    @Override
    public void initialize(Context context, Bundle bundle) {
        Mapbox.getInstance(context.getApplicationContext(), context.getString(R.string.mapbox_access_token));
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        MapProviderStategy.getInstance()
                .setStrategy(new MapboxMapProvider(context, cache))
                .showDeviceLocation(placeListener);
    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {
        MapSdkInstance map = MapSdkProvider.getInstance().getMapSdkInstance();
        if (map != null) {
            map.setStyle(mapSettings);
        }
    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {

    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeListener) {

    }

    @Override
    public void findPlace(String query, IPlaceListener placeListener) {
        GeocodeStrategy.getInstance()
                .setStategy(new LocationIqGeocode(context))
                .searchForLocations(query, placeListener);
    }

    @Override
    public IMapPlace getMyLocation() {
        return null;
    }

    @Override
    public IMapPlace getLastPickedPlace() {
        return null;
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {

    }

    @Override
    public void showMap() {
        MapProviderStategy.getInstance()
                .setStrategy(new MapboxMapProvider(context, cache))
                .showInitialLocation(null);
    }
}
