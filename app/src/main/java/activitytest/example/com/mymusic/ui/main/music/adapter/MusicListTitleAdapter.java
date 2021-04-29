package activitytest.example.com.mymusic.ui.main.music.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.music_list.Data;
import activitytest.example.com.mymusic.databinding.MusicListItemBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class MusicListTitleAdapter extends RecyclerView.Adapter<MusicListTitleAdapter.InnerViewHolder>{

    private final Context context;
    private final List<Data> musicList;
    private OnclickListener onclickListener;
    public MusicListTitleAdapter(Context context, List<Data> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public interface OnclickListener{
        void setOnClickListener(View view,int position);
    }

    public void setItemOnclickListener(OnclickListener onclickListener){
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicListItemBinding musicListItemBinding = DataBindingUtil.inflate ( LayoutInflater.from ( context ), R.layout.music_list_item, parent, false );
        return new InnerViewHolder ( musicListItemBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
       holder.musicListItemBinding.textView7.setText ( musicList.get ( position ).getName () );
       holder.musicListItemBinding.imageButton.setOnClickListener ( v -> onclickListener.setOnClickListener ( v,position ) );
    }

    @Override
    public int getItemCount() {
        if (musicList == null){
            return 0;
        }
        return musicList.size ();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        MusicListItemBinding musicListItemBinding;
        public InnerViewHolder(@NonNull MusicListItemBinding itemView) {
            super ( itemView.getRoot () );
            this.musicListItemBinding = itemView;
        }
    }
}
