package activitytest.example.com.mymusic.ui.listen.repository;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.lyrics.Lyrisc;
import activitytest.example.com.mymusic.bean.musicLocation.MusicLocation;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListenRepository {
    private static ListenRepository listenRepository = null;
    private final RetrofitClient instance;

    public static ListenRepository getListenRepository() {
        if (listenRepository == null){
            listenRepository = new ListenRepository ();
        }
        return listenRepository;
    }

    public ListenRepository() {
        instance = RetrofitClient.getInstance ();
    }


    public LiveData<Resource<MusicLocation>> getMusicLocation(long rid){
        MutableLiveData<Resource<MusicLocation>> resourceMutableLiveData = new MutableLiveData<> ();
        ApiService service = instance.createService ( ApiService.class );
        service.getMusicLocation ( rid ).enqueue ( new Callback<MusicLocation> () {
            @Override
            public void onResponse(Call<MusicLocation> call, Response<MusicLocation> response) {
                if (response.body ().getCode () == Status.SUCCESS.getCode ()){
                    Resource<MusicLocation> musicLocationResource = new Resource<> ( Status.SUCCESS, response.body (), response.message () );
                    resourceMutableLiveData.postValue ( musicLocationResource );
                }else if (response.body ().getCode () == Status.LOADING.getCode ()){
                    Resource<MusicLocation> musicLocationResource = new Resource<> (Status.SUCCESS,response.body (),response.message ());
                    resourceMutableLiveData.postValue ( musicLocationResource );

                }
            }

            @Override
            public void onFailure(Call<MusicLocation> call, Throwable t) {
                Resource<MusicLocation> musicLocationResource = new Resource<> (Status.ERROR,null,t.getMessage ());
                resourceMutableLiveData.postValue ( musicLocationResource );
            }
        } );

        return resourceMutableLiveData;
    }


    public LiveData<Resource<Lyrisc>> getMusicLyric(long rid){
        ApiService service = instance.createService ( ApiService.class );
        Call<Lyrisc> musicLyric = service.getMusicLyric ( rid );
        MutableLiveData<Resource<Lyrisc>> mutableLiveData = new MutableLiveData<> ();

        musicLyric.enqueue ( new Callback<Lyrisc> () {
            @Override
            public void onResponse(Call<Lyrisc> call, Response<Lyrisc> response) {
                Resource<Lyrisc> resource;
                if (response.code () == Status.SUCCESS.getCode ()){
                    resource = new Resource<> ( Status.SUCCESS, response.body (), response.message () );
                }else {
                    resource = new Resource<> ( Status.LOADING, response.body (), response.message () );
                }
                mutableLiveData.postValue ( resource );
            }

            @Override
            public void onFailure(Call<Lyrisc> call, Throwable t) {
                Resource<Lyrisc> resource = new Resource<> (Status.ERROR,null,t.getMessage ());
                mutableLiveData.postValue ( resource );
            }
        } );


        return mutableLiveData;
    }
}
