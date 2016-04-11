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

    private List<Image> mImage;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public ImageListAdapter(List<Image> image, Context context) {
        mImage = image;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_img_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = mImage.get(position);
        Picasso.with(mContext).load(IMAGE_BASE_URL + image.getFilePath()).fit().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

}
