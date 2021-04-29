package activitytest.example.com.mymusic.ui.lore.login.register.repository;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.ui.lore.login.register.bean.RegisterBean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private static RegisterRepository registerRepository = null;

    private final RetrofitClient retrofitClient;

    public static RegisterRepository getRegisterRepository() {
        if (registerRepository == null){
            registerRepository = new RegisterRepository ();
        }
        return registerRepository;
    }

    public RegisterRepository() {
         retrofitClient = RetrofitClient.getInstance ();
    }

    public LiveData<Resource<UserInfo>> registerIng(RegisterBean registerBean){

        MutableLiveData<Resource<UserInfo>> resourceMutableLiveData = new MutableLiveData<> ();
        ApiService service = retrofitClient.createService ( ApiService.class );
        service.registerIngByJson ( registerBean )
                .enqueue ( new Callback<UserInfo> () {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.code () == Status.SUCCESS.getCode ()){
                    resourceMutableLiveData.postValue ( new Resource<UserInfo> ().success ( response.body (),"注册成功" ) );

                }else if (response.code () == Status.LOADING.getCode ()){
                    resourceMutableLiveData.postValue ( new Resource<UserInfo> ().loading ( response.body (),"注册失败" ) );
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                resourceMutableLiveData.postValue ( new Resource<UserInfo> ().error ( t.getMessage () ) );

            }
        } );

        return resourceMutableLiveData;
    }
}
