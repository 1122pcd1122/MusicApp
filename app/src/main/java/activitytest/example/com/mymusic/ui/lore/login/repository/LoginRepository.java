package activitytest.example.com.mymusic.ui.lore.login.repository;

import android.util.Log;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.network.ApiService;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.RetrofitClient;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.ui.lore.login.bean.LoginBean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static LoginRepository loginRepository = null;
    private RetrofitClient retrofitClient;
    private final String TAG = this.getClass ().getName ();

    public static LoginRepository getLoginRepository(){
        if (loginRepository == null){
            loginRepository = new LoginRepository ();
        }

        return loginRepository;
    }

    public LoginRepository() {
        retrofitClient = RetrofitClient.getInstance ();
    }

    public LiveData<Resource<UserInfo>> loginIng(LoginBean loginBean){

        MutableLiveData<Resource<UserInfo>> userInfoMutableLiveData = new MutableLiveData<> ();
        ApiService service = retrofitClient.createService ( ApiService.class );
        service.loginIngByJson ( loginBean )
                .enqueue ( new Callback<UserInfo> () {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.d ( TAG,"状态码"+response.code () );
                if (response.body ().getStatus () == Status.SUCCESS.getCode ()){
                    userInfoMutableLiveData.postValue ( new Resource<UserInfo> ().success ( response.body (), "登录成功" ));
                }else if (response.body ().getStatus () == Status.LOADING.getCode ()){
                    //登录失败
                    userInfoMutableLiveData.postValue ( new Resource<UserInfo> ().loading ( response.body (), "登录失败" ) );
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d ( TAG,t.getMessage ()+"..." );
                userInfoMutableLiveData.postValue ( new Resource<UserInfo> ().error ( "无网络") );
            }
        } );

        return userInfoMutableLiveData;
    }

}
