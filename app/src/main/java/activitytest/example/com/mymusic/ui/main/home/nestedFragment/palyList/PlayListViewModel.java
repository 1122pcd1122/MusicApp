package activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList;

import android.util.Log;

import java.util.List;

import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.network.Resource;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PlayListViewModel extends ViewModel {

    private final PlayListRepository playListRepository;
    private final String TAG = this.getClass ().getName ();

    public PlayListViewModel() {
        playListRepository = PlayListRepository.getPlayListRepository ();
    }

    public LiveData<Resource<List<Info>>> getPlayList(String phone){
        Log.d ( TAG,"准备获取歌单" );
        return playListRepository.getPlayList ( phone );
    }
}
