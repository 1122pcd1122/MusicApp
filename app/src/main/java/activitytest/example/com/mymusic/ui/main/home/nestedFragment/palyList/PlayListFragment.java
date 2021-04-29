package activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.databinding.FragmentHomeBinding;
import activitytest.example.com.mymusic.databinding.FragmentPlayListBinding;
import activitytest.example.com.mymusic.network.Resource;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlayListFragment extends Fragment {


    private final String TAG = PlayListFragment.class.getName ();
    private FragmentPlayListBinding playListBinding;
    private PlayListViewModel playListViewModel;
    private SharedPreferences sharedPreferences;
    private PlayListFragment playListFragment;

    public PlayListFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        playListBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_play_list, container, false );
        playListViewModel = new ViewModelProvider ( this ).get ( PlayListViewModel.class );
        sharedPreferences = getContext ().getSharedPreferences ( "userInfo",Context.MODE_PRIVATE );
        playListFragment = this;

        return playListBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        if (!sharedPreferences.getString ( "name","" ).isEmpty ()){
            Log.d ( TAG,"执行" );
            String phone = sharedPreferences.getString ( "phone", "" );
            LiveData<Resource<List<Info>>> playList = playListViewModel.getPlayList ( phone );
            RecyclerView recyclerView = playListBinding.recyclerView;
            playList.observe ( getViewLifecycleOwner (), new Observer<Resource<List<Info>>> () {
                @Override
                public void onChanged(Resource<List<Info>> listResource) {

                    if (listResource.getStatus () == null){
                        return;
                    }

                    if (listResource.getData () != null){
                        Log.d ( TAG,"开始" );
                        Log.d ( TAG,"aaa"+listResource.getStatus () );

                        PlayListAdapter playListAdapter = new PlayListAdapter ( getContext (),listResource.getData ());
                        recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                        playListAdapter.setOnClick ( (view, musicInfo) -> {
                            SharedPreferences sharedPreferences = getActivity ().getSharedPreferences ( "userInfo",Context.MODE_PRIVATE );
                            String isVip = sharedPreferences.getString ( "isVip", "" );

                            if (musicInfo.getData ().isListenFee () && !isVip.equals ( "1" )){
                                Toast.makeText ( getContext (), "请充值会员...", Toast.LENGTH_SHORT ).show ();
                                return;
                            }

                            NavController navController =Navigation.findNavController ( view );
                            Bundle bundle = new Bundle ();
                            bundle.putSerializable ( "MusicInfo",musicInfo );
                            navController.navigate ( R.id.action_navigation_home_to_listenFragment2,bundle );
                            @SuppressLint("UseRequireInsteadOfGet")
                            BottomNavigationView viewById = Objects.requireNonNull ( getActivity () ).findViewById ( R.id.nav_view );
                            viewById.setVisibility ( View.INVISIBLE );
                        } );
                        recyclerView.setAdapter ( playListAdapter );
                    }
                }
            } );

        }

    }
}