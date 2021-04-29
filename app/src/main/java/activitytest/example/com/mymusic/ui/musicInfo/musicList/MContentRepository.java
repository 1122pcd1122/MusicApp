package activitytest.example.com.mymusic.ui.musicInfo.musicList;

import android.util.Log;

import activitytest.example.com.mymusic.bean.content.Content;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MContentRepository {
    private static MContentRepository mContentRepository = null;
    private RetrofitClient retrofitClient;
    private final String TAG = MContentRepository.class.getName ();

    public static MContentRepository getmContentRepository() {
        if (mContentRepository == null){
            mContentRepository = new MContentRepository ();
        }
        return mContentRepository;
    }

    public MContentRepository() {
         retrofitClient = RetrofitClient.getInstance ();
    }

    /**
     * 获取查询结果
     * @param key
     * @return
     */
    public LiveData<Resource<Content>> listContent(String key){
        MutableLiveData<Resource<Content>> contentMutableLiveData = new MutableLiveData<> ();
        ApiService service = retrofitClient.createService ( ApiService.class );
        Call<Content> contentCall = service.listContent ( key );
        contentCall.enqueue ( new Callback<Content> () {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                if (response.code () == Status.SUCCESS.getCode ()){
                    Resource<Content> resource = new Resource<> (Status.SUCCESS,response.body (),response.message ());
                    contentMutableLiveData.postValue ( resource );
                }else if (response.code () == Status.LOADING.getCode ()){
                    Resource<Content> resource = new Resource<> (Status.LOADING,response.body (),response.message ());
                    contentMutableLiveData.postValue ( resource );
                }
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Resource<Content> resource = new Resource<> (Status.ERROR,null,t.getMessage ());
                contentMutableLiveData.postValue ( resource );
            }
        } );

        return contentMutableLiveData;
    }

    /**
     * 获取音乐详情
     * @param rid
     * @return
     */
    public LiveData<Resource<MusicInfo>> getMusicInfo(long rid) {
        MutableLiveData<Resource<MusicInfo>> resourceMutableLiveData = new MutableLiveData<> ();
        ApiService service = retrofitClient.createService ( ApiService.class );
        Call<MusicInfo> musicInfo = service.getMusicInfo ( rid );
        musicInfo.enqueue ( new Callback<MusicInfo> () {
            @Override
            public void onResponse(Call<MusicInfo> call, Response<MusicInfo> response) {
                if (response.body ().getCode () == Status.SUCCESS.getCode ()){
                    Resource<MusicInfo> resource = new Resource<> (Status.SUCCESS,response.body (),response.message ());
                    resourceMutableLiveData.postValue ( resource );
                }else if (response.body ().getCode () == Status.LOADING.getCode ()){
                    Resource<MusicInfo> resource = new Resource<> (Status.LOADING,response.body (),response.message ());
                    resourceMutableLiveData.postValue ( resource );
                }
            }

            @Override
            public void onFailure(Call<MusicInfo> call, Throwable t) {
                Resource<MusicInfo> resource = new Resource<> (Status.ERROR,null,t.getMessage ());
                resourceMutableLiveData.postValue ( resource );
            }
        } );
        return resourceMutableLiveData;
    }


    public void postPlayList(PlayList playList){
        ApiService service = retrofitClient.createService ( ApiService.class );
        Call<PlayList> playListCall = service.postPlayList ( playList );
        playListCall.enqueue ( new Callback<PlayList> () {
            @Override
            public void onResponse(Call<PlayList> call, Response<PlayList> response) {
                if (response.code () == 200){
                    Log.d ( TAG,"成功" );
                }
            }

            @Override
            public void onFailure(Call<PlayList> call, Throwable t) {
                Log.d ( TAG,"失败" );
            }
        } );
    }
}
