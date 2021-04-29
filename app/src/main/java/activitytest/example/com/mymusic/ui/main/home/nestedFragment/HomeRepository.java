package activitytest.example.com.mymusic.ui.main.home.nestedFragment;

import android.util.Log;

import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.RetrofitClient;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private static HomeRepository homeRepository = null;
    private final RetrofitClient instance;
    private final String TAG = HomeRepository.class.getName ();

    public static HomeRepository getHomeRepository() {
        if (homeRepository == null){
            homeRepository = new HomeRepository ();
        }
        return homeRepository;
    }


    public HomeRepository() {
        instance = RetrofitClient.getInstance ();
    }

    public LiveData<Boolean> postIsVip(String isVip){
        ApiService service = instance.createService ( ApiService.class );
        Call<Boolean> booleanCall = service.postVipTag ( isVip );
        MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<> ();
        booleanCall.enqueue ( new Callback<Boolean> () {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                booleanMutableLiveData.postValue ( response.body () );
                Log.d ( TAG,"成功" );
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d ( TAG,"" );
            }
        } );

        return booleanMutableLiveData;
    }
}
