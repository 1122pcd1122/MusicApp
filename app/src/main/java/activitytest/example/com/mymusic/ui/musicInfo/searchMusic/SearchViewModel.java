package activitytest.example.com.mymusic.ui.musicInfo.searchMusic;

import activitytest.example.com.mymusic.bean.advice.Advice;
import activitytest.example.com.mymusic.network.Resource;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    SearchRepository searchRepository;
    public SearchViewModel() {
        searchRepository = SearchRepository.getSearchRepository ();
    }

    public LiveData<Resource<Advice>> listAdvice(String key){
        return searchRepository.listAdvice ( key );

    }
}
