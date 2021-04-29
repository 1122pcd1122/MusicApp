package activitytest.example.com.mymusic.ui.main.music.innerFragment;

import android.os.Bundle;

import activitytest.example.com.mymusic.bean.music_list.Data;
import activitytest.example.com.mymusic.bean.music_list.MusicListRoot;
import activitytest.example.com.mymusic.databinding.FragmentMusicListItemItemBinding;
import activitytest.example.com.mymusic.databinding.FragmentPlayListBindingImpl;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.main.music.MusicViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class MusicListItemItemFragment extends Fragment {

    private MusicViewModel musicViewModel;
    private FragmentMusicListItemItemBinding fragmentMusicListItemItemBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMusicListItemItemBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_music_list_item_item, container, false );
        musicViewModel = new ViewModelProvider ( this ).get ( MusicViewModel.class );
        return fragmentMusicListItemItemBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        Bundle arguments = getArguments ();
        int location = arguments.getInt ( "location" );
        musicViewModel.getMusicList ().observe ( getViewLifecycleOwner (), musicListRootResource -> {
            Data data = musicListRootResource.getData ().getData ().get ( location );
            fragmentMusicListItemItemBinding.textView12.setText ( data.getName () );
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            fragmentMusicListItemItemBinding.musicListItemItemInnerRv.setLayoutManager(layoutManager);
            MusicListItemItemAdapter musicListItemItemAdapter = new MusicListItemItemAdapter ( getContext (), data );
            fragmentMusicListItemItemBinding.musicListItemItemInnerRv.setAdapter ( musicListItemItemAdapter );
        } );
    }
}