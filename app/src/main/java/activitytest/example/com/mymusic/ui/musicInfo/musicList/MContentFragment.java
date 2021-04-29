package activitytest.example.com.mymusic.ui.musicInfo.musicList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import activitytest.example.com.mymusic.bean.content.Content;
import activitytest.example.com.mymusic.bean.content.ContentInfo;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.databinding.FragmentContentBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.ui.musicInfo.musicList.adapter.MContentAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import activitytest.example.com.mymusic.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


public class MContentFragment extends Fragment {

    private FragmentContentBinding fragmentContentBinding;
    private MContentViewModel mContentViewModel;
    private final String TAG = this.getClass ().getName ();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentContentBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_content, container, false );
        mContentViewModel = new ViewModelProvider ( this ).get ( MContentViewModel.class );

        return fragmentContentBinding.getRoot ();
    }

    @Override
    public void onStart() {
        super.onStart ();

        Bundle bundle = getArguments ();
        String content_title = null;
        if (bundle != null) {
            content_title = bundle.getString ( "content_title" );
        }

        LiveData<Resource<Content>> resourceLiveData = mContentViewModel.listContent ( content_title );
        fragmentContentBinding.contentTitle.setText ( content_title );
        resourceLiveData.observe ( getViewLifecycleOwner (), contentResource -> {
            List<ContentInfo> list = Optional.ofNullable ( contentResource.getData () ).orElse ( new Content () ).getList ();
            MContentAdapter mContentAdapter = new MContentAdapter ( getContext (),  list );
            fragmentContentBinding.recycleView.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
            fragmentContentBinding.recycleView.setAdapter ( mContentAdapter );
            reOnclick ( list, mContentAdapter );
            resourceLiveData.removeObservers ( this );
        } );


    }

    /**
     * RecycleView的事件点击
     * @param contentResource 列表内容
     * @param mContentAdapter 适配器
     */
    private void reOnclick(List<ContentInfo> contentResource, MContentAdapter mContentAdapter) {

        mContentAdapter.setOnItemClickListener ( (view, position) -> {
            ContentInfo contentInfo = contentResource.get ( position );
            Log.d ( TAG,contentResource.get ( position ).getRid ()+"" );
            LiveData<Resource<MusicInfo>> musicInfo = mContentViewModel.getMusicInfo ( contentInfo.getRid () );
            musicInfo.observe ( getViewLifecycleOwner (), musicInfoResource -> {
                Bundle bundle = new Bundle ();
                bundle.putSerializable ( "MusicInfo", musicInfoResource.getData ());
                Log.d ( TAG,musicInfoResource.getData ().getMsg () );

                @SuppressLint("UseRequireInsteadOfGet") SharedPreferences
                sharedPreferences = Objects.requireNonNull ( getActivity () ).getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
                String isVip = sharedPreferences.getString ( "isVip", "" );
                if (musicInfoResource.getData ().getData ().isListenFee () && !isVip.equals ( "1" ) ){
                    Toast.makeText ( getContext (), "请充值会员......", Toast.LENGTH_SHORT ).show ();
                    return;
                }

                @SuppressLint("UseRequireInsteadOfGet")
                NavController navController = Navigation.findNavController ( Objects.requireNonNull ( getView () ) );
                navController.navigate ( R.id.action_MContentFragment_to_listenFragment,bundle );
            } );
        } );
    }



}