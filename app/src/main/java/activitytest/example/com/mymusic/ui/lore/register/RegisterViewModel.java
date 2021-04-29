package activitytest.example.com.mymusic.ui.lore.register;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.lore.login.register.bean.RegisterBean;
import activitytest.example.com.mymusic.ui.lore.login.register.repository.RegisterRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel{

    private RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository = RegisterRepository.getRegisterRepository ();
    }


    public LiveData<Resource<UserInfo>> registerIng(RegisterBean registerBean){
        return registerRepository.registerIng ( registerBean );
    }


    @Override
    protected void onCleared() {
        super.onCleared ();
        registerRepository = null;
    }
}
