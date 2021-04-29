package activitytest.example.com.mymusic.ui.listen;

import activitytest.example.com.mymusic.bean.lyrics.Lyrisc;
import activitytest.example.com.mymusic.bean.musicLocation.MusicLocation;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.listen.repository.ListenRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListenViewModel extends ViewModel {

    private final ListenRepository listenRepository;

    public ListenViewModel() {
        listenRepository = ListenRepository.getListenRepository ();
    }

    public LiveData<Resource<MusicLocation>> getMusicLocation(long rid){
        return listenRepository.getMusicLocation ( rid );
    }

    public LiveData<Resource<Lyrisc>> getMusicLyric(long rid){
        return listenRepository.getMusicLyric ( rid );
    }
}
