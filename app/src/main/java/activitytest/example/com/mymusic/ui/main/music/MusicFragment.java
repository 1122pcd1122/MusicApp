package activitytest.example.com.mymusic.ui.main.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.indicator.CircleIndicator;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import activitytest.example.com.mymusic.SearchActivity;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.music_list.Data;
import activitytest.example.com.mymusic.bean.music_list.MusicListRoot;
import activitytest.example.com.mymusic.bean.playList.PlayListRoot;
import activitytest.example.com.mymusic.databinding.FragmentMusicBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList.PlayListAdapter;
import activitytest.example.com.mymusic.ui.main.music.adapter.MusicListTitleAdapter;
import activitytest.example.com.mymusic.ui.main.music.adapter.VipListAdapter;
import activitytest.example.com.mymusic.ui.main.music.banner.DataBean;
import activitytest.example.com.mymusic.ui.main.music.banner.ImageAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import activitytest.example.com.mymusic.R;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MusicFragment extends Fragment {


    private FragmentMusicBinding fragmentMusicBinding;
    private MusicViewModel musicViewModel;
    private MusicFragment musicFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView = getActivity ().findViewById ( R.id.nav_view );
        bottomNavigationView.setVisibility ( View.VISIBLE );
        fragmentMusicBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_music, container, false );

        musicViewModel = new ViewModelProvider ( this ).get ( MusicViewModel.class );
        musicViewModel.getText ().observe ( getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                fragmentMusicBinding.titleMusic.setText ( s );
            }
        } );

        musicFragment = this;
        return fragmentMusicBinding.getRoot ();
    }



    @Override
    public void onStart() {
        super.onStart ();
        svShow ();
        showBanner ();

        showList ();


        RecyclerView recyclerView = fragmentMusicBinding.includeVip.findViewById ( R.id.vipList );

        musicViewModel.getVipList ().observe ( getViewLifecycleOwner (), new Observer<Resource<PlayListRoot>> () {
            @Override
            public void onChanged(Resource<PlayListRoot> playListRootResource) {
                if (playListRootResource.getData () == null){
                    return;
                }
                VipListAdapter vipListAdapter = new VipListAdapter ( getContext (),playListRootResource.getData ().getInfo () );
                recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                recyclerView.setAdapter ( vipListAdapter );
                vipListAdapter.setOnClick ( (view, musicInfo) -> {
                    @SuppressLint("UseRequireInsteadOfGet")
                    SharedPreferences sharedPreferences = Objects.requireNonNull ( getActivity () ).getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
                    String isVip = sharedPreferences.getString ( "isVip", "" );
                    if (!isVip.equals ( "1" )){
                        Toast.makeText ( getContext (), "请充值会员...", Toast.LENGTH_SHORT ).show ();
                        return;
                    }
                    Bundle bundle = new Bundle ();
                    bundle.putSerializable ( "MusicInfo",musicInfo );
                    NavController navController = NavHostFragment.findNavController ( musicFragment );
                    navController.navigate ( R.id.action_navigation_music_to_listenFragment2,bundle );
                    @SuppressLint("UseRequireInsteadOfGet")
                    BottomNavigationView bottomNavigationView = Objects.requireNonNull ( getActivity () ).findViewById ( R.id.nav_view );
                    bottomNavigationView.setVisibility ( View.INVISIBLE );
                } );
            }
        } );

    }

    private void showList() {
        musicViewModel.getMusicList ().observe ( getViewLifecycleOwner (), (Observer<Resource<MusicListRoot>>) musicListRootResource -> {
            MusicListRoot data = musicListRootResource.getData ();

            RecyclerView recyclerView = fragmentMusicBinding.include.findViewById ( R.id.musicListRV );
            recyclerView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
            if (data == null){
                return;
            }
            MusicListTitleAdapter musicListTitleAdapter = new MusicListTitleAdapter ( getContext (), data.getData () );
            musicListTitleAdapter.setItemOnclickListener ( (view, position) -> {
                Bundle bundle = new Bundle ();
                bundle.putInt ( "location",position );
                NavController navController = NavHostFragment.findNavController ( this );
                navController.navigate ( R.id.action_navigation_music_to_musicListItemItemFragment,bundle );
            } );
            recyclerView.setAdapter ( musicListTitleAdapter );


        } );
    }

    private void showBanner() {
        List<DataBean> list = new ArrayList<> ();
        list.add ( new DataBean ( "http://192.168.43.47:5000/static/101.jpg" ) );
        list.add ( new DataBean ( "http://192.168.43.47:5000/static/102.jpg" ) );
        list.add ( new DataBean ( "http://192.168.43.47:5000/static/103.jpg" ) );

        fragmentMusicBinding.banner.setAdapter ( new ImageAdapter (list,getContext ()  ) )
        .addBannerLifecycleObserver ( this )
        .setIndicator ( new CircleIndicator ( getContext () ) );
        fragmentMusicBinding.banner.start ();
    }

    @Override
    public void onStop() {
        super.onStop ();
        fragmentMusicBinding.banner.stop ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        fragmentMusicBinding.banner.destroy ();
    }

    /**
     * 搜索框出现和移除对toolbar中标题的影响
     */
    private void svShow() {
        ImageButton searchView = fragmentMusicBinding.searchView;
        searchView.setOnClickListener ( view -> {
            //进入搜素页
            Intent intent = new Intent (getActivity (), SearchActivity.class );
            getActivity ().startActivity ( intent );
        } );

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu ( menu, inflater );

        inflater.inflate ( R.menu.bottom_nav_menu,menu );
        MenuItem music = menu.findItem ( R.id.navigation_music );
        MenuItem home = menu.findItem ( R.id.navigation_home );

        Log.d ( "MyTitle",music.getTitle ().toString ()+" adssad" );
        Log.d ( "MyTitle",music.getTitle ().toString ()+"asdf" );

        musicViewModel.getText ().postValue ( music.getTitle ().toString () );
    }
}