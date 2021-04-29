package activitytest.example.com.mymusic.ui.musicInfo.searchMusic;

import activitytest.example.com.mymusic.bean.advice.Advice;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private static SearchRepository searchRepository = null;
    private final RetrofitClient instance;

    public static SearchRepository getSearchRepository() {
        if (searchRepository == null){
            searchRepository = new SearchRepository ();
        }

        return searchRepository;
    }

    public SearchRepository() {
        instance = RetrofitClient.getInstance ();
    }


    public LiveData<Resource<Advice>> listAdvice(String key){
        ApiService service = instance.createService ( ApiService.class );
        MutableLiveData<Resource<Advice>> listMutableLiveData = new MutableLiveData<> ();
        Call<Advice> listCall = service.listAdvice ( key );
        listCall.enqueue ( new Callback<Advice> () {
            @Override
            public void onResponse(Call<Advice> call, Response<Advice> response) {
                Advice body = response.body ();
                Resource<Advice> listResource = null;
                if (response.code () == Status.SUCCESS.getCode ()){
                    listResource = new Resource<> ( Status.SUCCESS, body, "请求成功" );
                }else if (response.code () == Status.LOADING.getCode ()){
                    listResource = new Resource<> (Status.LOADING,body,"网络缓慢");
                }
                listMutableLiveData.postValue ( listResource );
            }

            @Override
            public void onFailure(Call<Advice> call, Throwable t) {
                Resource<Advice> adviceResource = new Resource<Advice> (Status.ERROR,null,t.getMessage ());
                listMutableLiveData.postValue ( adviceResource );
            }
        } );

        return listMutableLiveData;

    }
}
