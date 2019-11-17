package gps.map.navigator.model.impl.sdk;

import android.content.Context;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;

import org.greenrobot.eventbus.EventBus;

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
import gps.navigator.mapboxsdk.BuildConfig;
import gps.navigator.mapboxsdk.event.ChangeSettingsEvent;
import gps.navigator.mapboxsdk.event.MessageEvent;
import gps.navigator.mapboxsdk.event.RequestLocationEvent;
import gps.navigator.mapboxsdk.geocode.GeocodeStrategy;
import gps.navigator.mapboxsdk.geocode.impl.LocationIqGeocode;

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
        Mapbox.getInstance(context.getApplicationContext(), BuildConfig.MAPBOX_HASH);
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        EventBus.getDefault()
                .post(new RequestLocationEvent(MessageEvent.TYPE_SHOW_DEVICE_LOCATION, placeListener));
    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {
        EventBus.getDefault()
                .post(new ChangeSettingsEvent(MessageEvent.TYPE_CHANGE_MAP_SETTINGS, mapSettings));
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
                .setStategy(new LocationIqGeocode())
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
        EventBus.getDefault()
                .post(new RequestLocationEvent(MessageEvent.TYPE_SHOW_LAST_LOCATION, null));
    }
}
