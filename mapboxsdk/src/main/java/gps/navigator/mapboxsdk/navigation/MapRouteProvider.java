package gps.navigator.mapboxsdk.navigation;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.StyleProvider;
import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRouteProvider implements INavigationProvider {

    private static final String PERSON_ICON_ID = "PERSON_ICON_ID";
    private static final String PERSON_SOURCE_ID = "PERSON_SOURCE_ID";
    private static final String PERSON_LAYER_ID = "PERSON_LAYER_ID";
    private static final String DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID = "DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID";

    private static final String SYMBOL_ICON_ID = "SYMBOL_ICON_ID";
    private static final String MARKER_SOURCE_ID = "MARKER_SOURCE_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String DASHED_DIRECTIONS_LINE_LAYER_ID = "DASHED_DIRECTIONS_LINE_LAYER_ID";
    private Context context;
    private Cache cache;
    private MapboxMap mapboxMap;

    public MapRouteProvider(Context context, Cache cache, MapboxMap mapboxMap) {
        this.context = context;
        this.cache = cache;
        this.mapboxMap = mapboxMap;
    }

    @Override
    public void buildRoute(@Nullable Point origin, @Nullable Point destination, @Nullable RouteReadyListener listener) {
        MapboxDirections client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                .accessToken(context.getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new ResponseCallback(listener));
    }

    @Override
    public void drawRoute(@Nullable final DirectionsRoute route) {
        mapboxMap.setStyle(getStyle(), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                List<Feature> directionsRouteFeatureList = new ArrayList<>();
                LineString lineString = LineString.fromPolyline(route.geometry(), Constants.PRECISION_6);
                List<Point> lineStringCoordinates = lineString.coordinates();
                for (int i = 0; i < lineStringCoordinates.size(); i++) {
                    directionsRouteFeatureList.add(Feature.fromGeometry(
                            LineString.fromLngLats(lineStringCoordinates)));
                }
                FeatureCollection dashedLineDirectionsFeatureCollection = FeatureCollection.fromFeatures(directionsRouteFeatureList);
                GeoJsonSource source = style.getSourceAs(DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID);
                if (source != null) {
                    source.setGeoJson(dashedLineDirectionsFeatureCollection);
                }
                mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(
                        new LatLngBounds.Builder()
                                .include(getOrigin())
                                .include(getDestination())
                                .build(), 150), 5000);
            }


            private LatLng getOrigin() {
                IMapPlace place = cache.getLastRoute().getOrigin();
                return new LatLng(place.getLatitude(), place.getLongitude());
            }

            private LatLng getDestination() {
                IMapPlace place = cache.getLastRoute().getDestination();
                return new LatLng(place.getLatitude(), place.getLongitude());
            }
        });
    }

    private Style.Builder getStyle() {
        return new Style.Builder().fromUri(getCachedStyle())
                .withImage(PERSON_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                        context.getResources().getDrawable(R.drawable.my_location_icon))) //R.drawable.mapbox_mylocation_bg_shape
                .withSource(new GeoJsonSource(PERSON_SOURCE_ID, Feature.fromGeometry(getOrigin())))
                .withLayer(new SymbolLayer(PERSON_LAYER_ID, PERSON_SOURCE_ID).withProperties(
                        PropertyFactory.iconImage(PERSON_ICON_ID),
//                        PropertyFactory.iconSize(1f),
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true)
                ))

                // Set up the image, source, and layer for the potential destination markers
                .withImage(SYMBOL_ICON_ID, BitmapFactory.decodeResource(
                        context.getResources(), R.drawable.mapbox_marker_icon_default))
                .withSource(new GeoJsonSource(MARKER_SOURCE_ID, Feature.fromGeometry(getDestination())))
                .withLayer(new SymbolLayer(LAYER_ID, MARKER_SOURCE_ID).withProperties(
                        PropertyFactory.iconImage(SYMBOL_ICON_ID),
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconOffset(new Float[]{0f, -4f})
                ))

                // Set up the source and layer for the direction route LineLayer
                .withSource(new GeoJsonSource(DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID))
                .withLayerBelow(
                        new LineLayer(DASHED_DIRECTIONS_LINE_LAYER_ID, DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID)
                                .withProperties(
                                        PropertyFactory.lineWidth(7f),
                                        PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                                        PropertyFactory.lineColor(Color.parseColor("#2096F3"))
                                ), PERSON_LAYER_ID);

    }

    private String getCachedStyle() {
        return new StyleProvider().getActiveStyle(cache);
    }

    private Point getOrigin() {
        IMapPlace place = cache.getLastRoute().getOrigin();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private Point getDestination() {
        IMapPlace place = cache.getLastRoute().getDestination();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private static class ResponseCallback implements Callback<DirectionsResponse> {
        @Nullable
        private RouteReadyListener listener;

        private ResponseCallback(@Nullable RouteReadyListener listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
            if (listener != null) {
                DirectionsResponse directionsResponse = response.body();
                if (directionsResponse == null) {
                    listener.onBuildFailed(new Exception("No routes found, make sure you set the right user and access token."));
                } else if (directionsResponse.routes().size() < 1) {
                    listener.onBuildFailed(new Exception("No routes found"));
                } else {
                    listener.onRouteReady(directionsResponse.routes().get(0));
                }
            }
            invalidate();
        }

        @Override
        public void onFailure(Call<DirectionsResponse> call, Throwable t) {
            if (listener != null) {
                listener.onBuildFailed(new Exception(t));
            }
            invalidate();
        }

        private void invalidate() {
            listener = null;
        }
    }
}
