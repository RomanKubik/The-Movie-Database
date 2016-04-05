package kubik.roman.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.movies_detailes.Image;

/**
 * Created by roman on 4/4/2016.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w300";

    private List<Image> mImageList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public ImageListAdapter(List<Image> mImageList, Context context) {
        this.mImageList = mImageList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.movie_img_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = mImageList.get(position);
        Picasso.with(mContext).load(IMAGE_BASE_URL + image.getFilePath()).
                fit().centerCrop().into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

}
