package activitytest.example.com.mymusic.ui.musicInfo.musicList;

import android.view.View;

import activitytest.example.com.mymusic.ConfigurationActivity;
import activitytest.example.com.mymusic.bean.content.Content;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.network.Resource;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MContentViewModel extends ViewModel {
    private final MContentRepository repository;

    public MContentViewModel() {
        repository = MContentRepository.getmContentRepository ();
    }

    public LiveData<Resource<Content>> listContent(String key){
        return repository.listContent ( key );
    }

    public LiveData<Resource<MusicInfo>> getMusicInfo(long rid){
        return repository.getMusicInfo(rid);
    }

}
