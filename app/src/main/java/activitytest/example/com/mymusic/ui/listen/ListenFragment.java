package activitytest.example.com.mymusic.ui.listen;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.lyrics.Lyrisc;
import activitytest.example.com.mymusic.bean.musicLocation.MusicLocation;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.databinding.FragmentListenBinding;
import activitytest.example.com.mymusic.network.Resource;
import activitytest.example.com.mymusic.network.Status;
import activitytest.example.com.mymusic.service.ListenService;
import activitytest.example.com.mymusic.ui.listen.repository.LyrBean;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.hw.lrcviewlib.ILrcViewSeekListener;
import com.hw.lrcviewlib.IRowsParser;
import com.hw.lrcviewlib.LrcDataBuilder;
import com.hw.lrcviewlib.LrcRow;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenFragment extends Fragment {


    private final String TAG = this.getClass ().getName ();
    private FragmentListenBinding fragmentListenBinding;
    private ListenService.MyBinder myBinder;
    private boolean isPlaying = true;
    private ListenViewModel listenViewModel;
    private SeekBar seekBar ;
    private final Handler mHandler = new Handler ();
    //进度条下面的当前进度文字，将毫秒化为m:ss格式
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private ServiceConnection serviceConnection;


    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );


    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListenBinding = DataBindingUtil.inflate ( inflater, R.layout.fragment_listen, container, false );
        listenViewModel = new ViewModelProvider ( this ).get ( ListenViewModel.class );
        return fragmentListenBinding.getRoot ();
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onStart() {
        super.onStart ();
        seekBar = fragmentListenBinding.seekBar;
        /*
            获取bundle
         */
        Bundle bundle = getArguments ();
        MusicInfo musicInfo = null;
        if (bundle != null) {
            musicInfo = (MusicInfo) bundle.getSerializable ( "MusicInfo" );
            Log.d (TAG, musicInfo.getMsg ());
        }

        /*
            初始化view
         */
        if (musicInfo != null) {
//            Glide.with ( Objects.requireNonNull ( getView () ) ).load ( musicInfo.getData ().getPic () ).into ( fragmentListenBinding.musicImage );
            fragmentListenBinding.musicName.setText ( musicInfo.getData ().getName () );
            fragmentListenBinding.musicTime.setText ( musicInfo.getData ().getSongTimeMinutes () );

        }


        LiveData<Resource<Lyrisc>> musicLyric = listenViewModel.getMusicLyric ( musicInfo.getData ().getRid () );
        musicLyric.observe ( getViewLifecycleOwner (), new Observer<Resource<Lyrisc>> () {
            @Override
            public void onChanged(Resource<Lyrisc> lyriscResource) {
                String lyric_str = lyriscResource.getData ().getLyric_str ();
                fragmentListenBinding.textLyric.setText ( lyric_str );
            }
        } );

        /*
            service与fragment连接
         */
        MusicInfo finalMusicInfo = musicInfo;
         /*
            开启service
         */
        Intent intent = new Intent (getActivity (), ListenService.class );

        serviceConnection = new ServiceConnection () {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (ListenService.MyBinder) iBinder;
                LiveData<Resource<MusicLocation>> musicLocation = listenViewModel.getMusicLocation ( finalMusicInfo.getData ().getRid () );
                musicLocation.observe ( getViewLifecycleOwner (), musicLocationResource -> {
                    if (musicLocationResource.getStatus ().getCode () == Status.SUCCESS.getCode ()){
                        Log.d ( TAG,musicLocationResource.getData ().getUrl ()+"" );
                        if (myBinder != null){
                            myBinder.iniMediaPlayerFile ( musicLocationResource.getData ().getUrl () );
                            seekBar.setMax ( myBinder.getProgress () );
                            clickMusicBtn ();
                        }
                    }else {
                        Toast.makeText ( getContext (), "网络出现问题...", Toast.LENGTH_SHORT ).show ();
                    }
                } );

                mHandler.post ( mRunnable );
                Log.d ( TAG, "service与fragment连接成功" );
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }

        };
        Objects.requireNonNull ( getActivity () ).bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);




    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void clickMusicBtn() {
    /*
        开始或暂停button
     */
        ImageButton musicBtnBegin = fragmentListenBinding.musicBtnBegin;
        musicBtnBegin.setOnClickListener ( view -> {
            if (isPlaying){
                myBinder.playMusic ();
                fragmentListenBinding.musicBtnBegin.setImageDrawable ( getResources().getDrawable ( R.drawable.music_btn_pause  ) );
                isPlaying = false;
            }else {
                myBinder.pauseMusic ();
                fragmentListenBinding.musicBtnBegin.setImageDrawable ( getResources ().getDrawable ( R.drawable.music_btn_begin ) );
                isPlaying = true;
            }
        } );

        fragmentListenBinding.downLoad.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( getContext (), "下载中......", Toast.LENGTH_SHORT ).show ();
            }
        } );

        fragmentListenBinding.love.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( getContext (), "下载歌词中......", Toast.LENGTH_SHORT ).show ();
            }
        } );

        fragmentListenBinding.seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    myBinder.seekToPositon(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );
    }

    /**
     * 更新ui的runnable
     */
    private final Runnable mRunnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            fragmentListenBinding.seekBar.setProgress(myBinder.getPlayPosition());
            fragmentListenBinding.musicNowTime.setText(time.format(myBinder.getPlayPosition()) + "s");
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onStop() {
        super.onStop ();

        mHandler.removeCallbacks(mRunnable);

        myBinder.closeMedia();
        Objects.requireNonNull ( getActivity () ).unbindService ( serviceConnection);
    }
}