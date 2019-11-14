package gps.navigator.mapboxsdk.geocode.impl;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.impl.data.MapPlace;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.geocode.IGeocode;
import gps.navigator.mapboxsdk.geocode.locationiq.GeocodeApi;
import gps.navigator.mapboxsdk.geocode.locationiq.Place;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationIqGeocode implements IGeocode {
    private Context context;

    public LocationIqGeocode(Context context) {
        this.context = context;
    }

    @Override
    public void searchForLocations(@Nullable String query, @Nullable final IPlaceListener listener) {
        if (query == null || listener == null) {
            return;
        }
        new GeocodeApi()
                .getApi()
                .getPlaceForQuery(context.getString(R.string.location_token), query)
                .enqueue(new Callback<List<Place>>() {
                    @Override
                    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                        List<Place> body = response.body();
                        if (body != null) {
                            List<IMapPlace> tely = new ArrayList<>();
                            IMapPlace gely;
                            Place vely;
                            for (int i = 0; i < body.size(); i++) {
                                vely = body.get(i);
                                gely = new MapPlace();
                                gely.setId(vely.getPlace_id());
                                gely.setAddress(vely.getDisplay_name());
                                gely.setLatitude(vely.getLat());
                                gely.setLongitude(vely.getLon());
                                gely.setTitle(vely.getDisplay_name());
                                tely.add(gely);
                            }
                            listener.onPlacesLocated(tely);
                        } else {
                            listener.onPlaceLocationFailed(new Exception("No response"));
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Place>> call, Throwable t) {
                        listener.onPlaceLocationFailed(new Exception(t));
                    }
                });
    }

    @Nullable
    @Override
    public String getPlaceByLocation(@Nullable IMapPlace place) {
        return null;
    }
}
