package gps.navigator.mapboxsdk;

import android.content.Context;

import androidx.annotation.Nullable;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;

import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Deprecated
public class MapboxRouteBuilder {

    private Context context;
    private RouteReadyListener listener;

    public MapboxRouteBuilder(Context context, RouteReadyListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @SuppressWarnings({"MissingPermission"})
    public void buildRoute(Point origin, Point destination) {
        MapboxDirections client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                .accessToken(context.getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new ResponseCallback(listener));
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
