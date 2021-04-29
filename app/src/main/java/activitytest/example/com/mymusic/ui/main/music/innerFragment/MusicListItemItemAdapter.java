package activitytest.example.com.mymusic.ui.main.music.innerFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.music_list.Data;
import activitytest.example.com.mymusic.bean.music_list.Music_item_List;
import activitytest.example.com.mymusic.databinding.MusicListItemRvItemBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

class MusicListItemItemAdapter extends RecyclerView.Adapter<MusicListItemItemAdapter.InnerViewHolder> {

    private final Context context;
    private final Data data;
    public MusicListItemItemAdapter(Context context, Data data) {
        this.context =context;
        this.data = data;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicListItemRvItemBinding musicListItemRvItemBinding = DataBindingUtil.inflate ( LayoutInflater.from ( context ),R.layout.music_list_item_rv_item,parent,false );
        return new InnerViewHolder ( musicListItemRvItemBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        List<Music_item_List> list = data.getList ();
        Music_item_List music_item_list = list.get ( position );
        holder.musicListItemRvItemBinding.musicListItemItemTitle.setText ( music_item_list.getName () );
        Glide.with ( context ).load ( music_item_list.getPic () ).into ( holder.musicListItemRvItemBinding.musicListItemItemImage );
    }

    @Override
    public int getItemCount() {
        return data.getList ().size ();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        MusicListItemRvItemBinding musicListItemRvItemBinding;
        public InnerViewHolder(@NonNull MusicListItemRvItemBinding itemView) {
            super ( itemView.getRoot () );
            this.musicListItemRvItemBinding = itemView;
        }
    }
}
