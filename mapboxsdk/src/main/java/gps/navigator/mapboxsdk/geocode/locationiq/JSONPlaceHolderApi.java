package gps.navigator.mapboxsdk.geocode.locationiq;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("v1/search.php?&format=json")
    public Call<List<Place>> getPlaceForQuery(@Query("key") String token, @Query("q") String query);

    @GET("v1/reverse.php?&format=json")
    public Call<Place> getPlaceForLocation(@Query("key") String token, @Query("lat") double lat, @Query("lon") double lon);
}
