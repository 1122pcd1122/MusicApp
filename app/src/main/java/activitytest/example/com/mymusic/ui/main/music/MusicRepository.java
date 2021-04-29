package activitytest.example.com.mymusic.ui.main.music;

import java.util.List;

import activitytest.example.com.mymusic.bean.music_list.MusicListRoot;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayList;
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

class MusicRepository {
    private static MusicRepository musicRepository = null;
    private final RetrofitClient instance;

    public static MusicRepository getMusicRepository() {
        if (musicRepository == null){
          musicRepository = new MusicRepository ();
        }
        return musicRepository;
    }

    public MusicRepository() {
        instance = RetrofitClient.getInstance ();
    }

    public LiveData<Resource<MusicListRoot>> getMusicListRoot(){
        MutableLiveData<Resource<MusicListRoot>> musicListRootMutableLiveData = new MutableLiveData<> ();
        ApiService service = instance.createService ( ApiService.class );
        Call<MusicListRoot> musicList = service.getMusicList ();
        musicList.enqueue ( new Callback<MusicListRoot> () {
            @Override
            public void onResponse(Call<MusicListRoot> call, Response<MusicListRoot> response) {
                if (response.body ().getCode () == 200){
                    musicListRootMutableLiveData.postValue (new Resource<> ( Status.SUCCESS,response.body (),response.message () ) );
                }else {
                    musicListRootMutableLiveData.postValue ( new Resource<> (Status.LOADING,response.body (),response.message ()) );
                }
            }

            @Override
            public void onFailure(Call<MusicListRoot> call, Throwable t) {
                musicListRootMutableLiveData.postValue ( new Resource<> (Status.ERROR,null,t.getMessage ()) );
            }
        } );

        return musicListRootMutableLiveData;
    }


    public LiveData<Resource<PlayListRoot>> getVipList() {
        ApiService service = instance.createService ( ApiService.class );
        Call<PlayListRoot> vipList = service.getVipList ();
        MutableLiveData<Resource<PlayListRoot>> mutableLiveData = new MutableLiveData<> ();
        vipList.enqueue ( new Callback<PlayListRoot> () {
            @Override
            public void onResponse(Call<PlayListRoot> call, Response<PlayListRoot> response) {
                Resource<PlayListRoot> playListRootResource = null;
                if (response.code () == 200){
                    playListRootResource = new Resource<> ( Status.SUCCESS, response.body (), response.message () );
                }else {
                    playListRootResource = new Resource<> (Status.LOADING,response.body (),response.message ());
                }
                mutableLiveData.postValue ( playListRootResource );
            }

            @Override
            public void onFailure(Call<PlayListRoot> call, Throwable t) {
                mutableLiveData.postValue ( new Resource<> (Status.ERROR,null,t.getMessage ()) );
            }
        } );
        return mutableLiveData;
    }
}
