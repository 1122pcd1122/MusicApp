package activitytest.example.com.mymusic.ui.musicInfo.musicList.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import activitytest.example.com.mymusic.R;
import activitytest.example.com.mymusic.bean.content.ContentInfo;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.databinding.ContentRcItemBinding;
import activitytest.example.com.mymusic.ui.musicInfo.musicList.MContentRepository;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MContentAdapter extends RecyclerView.Adapter<MContentAdapter.ViewHolder> {

    private final Context context;
    private OnItemClickListener onItemClickListener;
    private final List<ContentInfo> contentInfoList;

    public MContentAdapter(Context context, List<ContentInfo> content) {
        this.context = context;
        contentInfoList = content;

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentRcItemBinding contentRcItemBinding = DataBindingUtil.inflate ( LayoutInflater.from ( context ), R.layout.content_rc_item, parent, false );
        return new ViewHolder ( contentRcItemBinding );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentRcItemBinding contentRcItemBinding = holder.contentRcItemBinding;
        ContentInfo contentInfo = contentInfoList.get ( position );
        contentRcItemBinding.contentNum.setText ( position+"" );
        contentRcItemBinding.contentName.setText ( contentInfo.getName () );
        contentRcItemBinding.contentUsername.setText ( contentInfo.getArtist () );
        contentRcItemBinding.contentMinutes.setText ( contentInfo.getSongTimeMinutes () );
        if (contentInfo.getIsListenFee ()){
            contentRcItemBinding.vipTag.setImageResource ( R.drawable.vip_icon );
        }

        if (onItemClickListener != null){
           holder.contentRcItemBinding.contentBegin.setOnClickListener ( view -> onItemClickListener.onItemClick ( view,position ) );
        }

        contentRcItemBinding.contentMenu.setOnClickListener ( v -> showPopup ( v,position ) );
    }

    private void showPopup(View view,int position) {
        PopupMenu popupMenu = new PopupMenu ( view.getContext (),view );
        MenuInflater inflater = popupMenu.getMenuInflater ();
        popupMenu.setOnMenuItemClickListener ( menuItem -> {
            SharedPreferences preferences = context.getSharedPreferences ( "userInfo", Context.MODE_PRIVATE );
            if (preferences.getString ( "name","" ).isEmpty ()){
                Toast.makeText ( context, "请先登录...", Toast.LENGTH_SHORT ).show ();
                return false;
            }

            MContentRepository mContentRepository = MContentRepository.getmContentRepository ();
            ContentInfo contentInfo = contentInfoList.get ( position );
            int status = 0;
            if (contentInfo.getIsListenFee ()){
                status = 1;
            }

            String phone = preferences.getString ( "phone", "" );
            PlayList playList = new PlayList (phone,contentInfo.getArtist (),contentInfo.getRid (),contentInfo.getSongTimeMinutes (),status,contentInfo.getName ());
            mContentRepository.postPlayList ( playList );


            return false;
        } );
        inflater.inflate ( R.menu.re_item_menu,popupMenu.getMenu () );
        popupMenu.show ();
    }

    @Override
    public int getItemCount() {
       if (contentInfoList == null){
           return 0;
       }
        return contentInfoList.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ContentRcItemBinding contentRcItemBinding;
        public ViewHolder(@NonNull ContentRcItemBinding rcItemBinding) {
            super ( rcItemBinding.getRoot () );
            this.contentRcItemBinding = rcItemBinding;
        }
    }
}
