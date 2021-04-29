package activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.music_Info.Data;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.databinding.ContentRcItemBinding;

import activitytest.example.com.mymusic.databinding.ListRcItemBinding;
import activitytest.example.com.mymusic.ui.main.music.adapter.MusicListTitleAdapter;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.InnerViewHolder> {

    private final String TAG = PlayListFragment.class.getName ();
    private Context context;
    private final List<Info> playLists;
    private OnClickListener onClickListener;
    public PlayListAdapter(Context context, List<Info> data) {
        this.context = context;
        this.playLists = data;
    }

    public interface OnClickListener{
        void setOncLickListener(View view,MusicInfo musicInfo);
    }

    public void setOnClick(OnClickListener onClick){
        this.onClickListener = onClick;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListRcItemBinding itemBinding = DataBindingUtil.inflate ( LayoutInflater.from ( context ), R.layout.list_rc_item, parent, false );
        return new InnerViewHolder ( itemBinding );
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Log.d ( TAG,"开始555" );
        ListRcItemBinding contentRcItemBinding = holder.playListRcItemBinding;
        Info playList = playLists.get ( position );
        contentRcItemBinding.contentName.setText ( playList.getName () );
        contentRcItemBinding.contentMinutes.setText ( playList.getTime ());
        contentRcItemBinding.contentUsername.setText ( playList.getArtist () );
        contentRcItemBinding.contentNum.setText ( position+"" );
        if (playList.getIsListenFee () == 1){
            contentRcItemBinding.vipTag.setImageResource ( R.drawable.vip_icon );
        }

        contentRcItemBinding.contentBegin.setImageDrawable ( context.getDrawable ( R.drawable.music_btn_begin ) );
        contentRcItemBinding.contentBegin.setOnClickListener ( v -> {
            MusicInfo musicInfo = new MusicInfo ();
            musicInfo.setMsg ( "歌单列表" );
            Data data = new Data ();
            musicInfo.setData ( data );
            musicInfo.getData ().setRid (Long.parseLong ( playList.getSong () )  );
            musicInfo.getData ().setArtist ( playList.getArtist () );
            musicInfo.getData ().setSongTimeMinutes ( playList.getTime () );
            musicInfo.getData ().setName ( playList.getName () );
            musicInfo.getData ().setListenFee ( playList.getIsListenFee () == 1 );
            onClickListener.setOncLickListener (v, musicInfo );
        } );
    }

    @Override
    public int getItemCount() {
        return playLists.size ();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        private final ListRcItemBinding playListRcItemBinding;
        public InnerViewHolder(@NonNull ListRcItemBinding itemView) {
            super ( itemView.getRoot () );
            this.playListRcItemBinding = itemView;
        }
    }
}
