package kubik.roman.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrls;
import kubik.roman.moviesdb.models.movies_detailes.Cast;
import kubik.roman.moviesdb.models.movies_list.Movie;

/**
 * Created by roman on 4/12/2016.
 */
public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    private OnItemClickListener mItemClickListener;

    public SimilarListAdapter(List<Movie> movies, Context context) {
        this.mMovies = movies;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_cast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.with(mContext).load(TmdbUrls.getPosterBaseUrl() + movie.getPosterPath()).fit().into(holder.imageView);
        holder.textView.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mMovies.size() > 10) return 10;
        else return mMovies.size();

    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imv_cast);
            textView = (TextView) itemView.findViewById(R.id.tv_cast);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}
