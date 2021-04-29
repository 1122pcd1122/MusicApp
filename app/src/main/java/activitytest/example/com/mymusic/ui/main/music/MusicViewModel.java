package activitytest.example.com.mymusic.ui.main.music;

import java.util.List;

import activitytest.example.com.mymusic.bean.music_list.MusicListRoot;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.bean.playList.PlayListRoot;
import activitytest.example.com.mymusic.network.Resource;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MusicViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final MusicRepository musicRepository;

    public MusicViewModel() {
        musicRepository = MusicRepository.getMusicRepository ();
        mText = new MutableLiveData<> ();
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    public LiveData<Resource<MusicListRoot>> getMusicList(){
        return musicRepository.getMusicListRoot ();
    }

    @Override
    protected void onCleared() {
        super.onCleared ();
        mText = null;
    }

    public LiveData<Resource<PlayListRoot>> getVipList(){
        return musicRepository.getVipList();
    }

}