package activitytest.example.com.mymusic.network;

import java.util.concurrent.TimeUnit;

import activitytest.example.com.mymusic.R;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient = null;

    public static RetrofitClient getInstance(){
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient ();
        }

        return retrofitClient;
    }

    private final Retrofit retrofit;

    public RetrofitClient() {
        Dispatcher dispatcher = new Dispatcher ();
        dispatcher.setMaxRequests ( 1 );
        OkHttpClient okHttpClient = new OkHttpClient.Builder ()
                .dispatcher ( dispatcher )
                .addInterceptor ( new LogInterceptor () )
                .connectTimeout ( 10, TimeUnit.SECONDS )
                .writeTimeout ( 10, TimeUnit.SECONDS )
                .readTimeout ( 10, TimeUnit.SECONDS )
                .build ();

        retrofit = new Retrofit.Builder ()
                .baseUrl ( "http://192.168.43.47:5000/" )
                .addConverterFactory ( GsonConverterFactory.create () )
                .client ( okHttpClient )
                .build ();

    }




    public <T> T createService(Class<T> serviceClass){
        return retrofit.create ( serviceClass );
    }
}
