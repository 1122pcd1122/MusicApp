package activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList;

import android.util.Log;

import java.util.List;

import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayListRoot;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListRepository {

    private static PlayListRepository playListRepository;
    private final RetrofitClient instance;
    private final String TAG = PlayListRepository.class.getName ();

    public static PlayListRepository getPlayListRepository() {
        if (playListRepository == null){
            playListRepository = new PlayListRepository ();
        }
        return playListRepository;
    }

    public PlayListRepository() {
        instance = RetrofitClient.getInstance ();
    }


    public LiveData<Resource<List<Info>>> getPlayList(String phone){
        MutableLiveData<Resource<List<Info>>> resourceMutableLiveData = new MutableLiveData<> ();
        ApiService service = instance.createService ( ApiService.class );
        Call<PlayListRoot> playList = service.getPlayList ( phone );
        playList.enqueue ( new Callback<PlayListRoot> () {
            @Override
            public void onResponse(Call<PlayListRoot> call, Response<PlayListRoot> response) {
                Log.d ( TAG,"成功" );
                resourceMutableLiveData.postValue ( new Resource<> (Status.SUCCESS,response.body ().getInfo (),response.message ()) );
            }

            @Override
            public void onFailure(Call<PlayListRoot> call, Throwable t) {
                Log.d ( TAG,"失败" );

            }
        } );
        return resourceMutableLiveData;
    }
}
