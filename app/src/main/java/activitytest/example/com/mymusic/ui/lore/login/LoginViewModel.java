package activitytest.example.com.mymusic.ui.lore.login;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.lore.login.bean.LoginBean;
import activitytest.example.com.mymusic.ui.lore.login.repository.LoginRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getLoginRepository ();
    }


    public LiveData<Resource<UserInfo>> loginIng(LoginBean loginBean){
        return loginRepository.loginIng ( loginBean );
    }

    @Override
    protected void onCleared() {
        super.onCleared ();
        loginRepository = null;
    }
}
