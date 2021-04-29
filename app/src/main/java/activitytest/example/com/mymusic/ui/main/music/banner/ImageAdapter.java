package activitytest.example.com.mymusic.ui.main.music.banner;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends BannerAdapter<DataBean,ImageAdapter.BannerViewHolder> {
    private List<DataBean> dataBeans;
    private Context context;

    public ImageAdapter(List<DataBean> datas, Context context) {
        super ( datas );
        this.dataBeans = datas;
        this.context = context;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView ( parent.getContext () );
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder ( imageView );
    }

    @Override
    public void onBindView(BannerViewHolder holder, DataBean data, int position, int size) {

        Glide.with ( context ).load ( data.getImageUrl () ).into ( holder.imageView );
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public BannerViewHolder(@NonNull ImageView itemView) {
            super ( itemView );
            this.imageView = itemView;
        }
    }
}
