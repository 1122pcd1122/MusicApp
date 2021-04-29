package activitytest.example.com.mymusic.ui.main.music.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.music_Info.Data;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.databinding.ListRcItemBinding;
import activitytest.example.com.mymusic.ui.main.home.nestedFragment.palyList.PlayListAdapter;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class VipListAdapter extends RecyclerView.Adapter<VipListAdapter.InnerViewHolder> {


    private Context context;
    private List<Info> infoList;
    private PlayListAdapter.OnClickListener onClickListener;
    public VipListAdapter(Context context , List<Info> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    public interface OnClickListener{
        void setOncLickListener(View view, MusicInfo musicInfo);
    }

    public void setOnClick(PlayListAdapter.OnClickListener onClick){
        this.onClickListener = onClick;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListRcItemBinding listRcItemBinding = DataBindingUtil.inflate ( LayoutInflater.from ( context ), R.layout.list_rc_item, parent, false );
        return new InnerViewHolder ( listRcItemBinding );
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        Info info = infoList.get ( position );
        holder.listRcItemBinding.contentName.setText ( info.getName () );
        holder.listRcItemBinding.contentBegin.setImageDrawable ( context.getDrawable ( R.drawable.music_btn_begin ) );
        holder.listRcItemBinding.contentMinutes.setText ( info.getTime () );
        holder.listRcItemBinding.contentNum.setText ( position+"" );
        holder.listRcItemBinding.contentUsername.setText ( info.getArtist () );
        holder.listRcItemBinding.contentBegin.setOnClickListener ( v -> {
            MusicInfo musicInfo = new MusicInfo ();
            musicInfo.setMsg ( "歌单列表" );
            Data data = new Data ();
            musicInfo.setData ( data );
            musicInfo.getData ().setRid (Long.parseLong ( info.getSong () )  );
            musicInfo.getData ().setArtist ( info.getArtist () );
            musicInfo.getData ().setSongTimeMinutes ( info.getTime () );
            musicInfo.getData ().setName ( info.getName () );
            musicInfo.getData ().setListenFee ( info.getIsListenFee () == 1 );
            onClickListener.setOncLickListener (v, musicInfo );
        } );
    }

    @Override
    public int getItemCount() {
        return infoList.size ();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        ListRcItemBinding listRcItemBinding;
        public InnerViewHolder(@NonNull ListRcItemBinding itemView) {
            super ( itemView.getRoot () );
            listRcItemBinding = itemView;
        }
    }
}
