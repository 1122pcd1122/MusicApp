package activitytest.example.com.mymusic.ui.main.home;

import activitytest.example.com.mymusic.bean.UserInfo;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> isVip;

    public HomeViewModel() {
        mText = new MutableLiveData<> ();
        isVip = new MutableLiveData<> ();

    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared ();
        mText = null;
    }

    public LiveData<UserInfo> getUserInfo(){
        return null;
    }


    public MutableLiveData<Boolean> isVip(){

        return isVip;
    }
}