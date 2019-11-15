package gps.navigator.mapboxsdk.geocode.locationiq;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeocodeApi {

    private static final String BASE_URL = "https://eu1.locationiq.com";

    private Retrofit retrofit;

    public GeocodeApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public JSONPlaceHolderApi getApi() {
        return retrofit.create(JSONPlaceHolderApi.class);
    }
}
