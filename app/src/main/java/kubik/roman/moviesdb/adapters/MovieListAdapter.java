package kubik.roman.moviesdb.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MoviesList;

/**
 * Created by roman on 3/21/2016.
 */
public class MovieListAdapter extends BaseAdapter {

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private MoviesList mMoviesList;
    private Context mContext;

    public MovieListAdapter(MoviesList moviesList, Context context) {
        this.mMoviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mMoviesList.getMoviesList().size();
    }

    @Override
    public Movie getItem(int position) {
        return mMoviesList.getMoviesList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieViewHolder mtvHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_list_item, parent, false);

            mtvHolder = new MovieViewHolder( (TextView) convertView.findViewById(R.id.tvTitle),
                    (TextView) convertView.findViewById(R.id.tvRating),
                    (TextView) convertView.findViewById(R.id.tvDescription),
                    (ImageView) convertView.findViewById(R.id.ivTitle),
                    (ImageView) convertView.findViewById(R.id.ivPoster) );

            convertView.setTag(mtvHolder);
        } else {
            mtvHolder = (MovieViewHolder) convertView.getTag();
        }

        Movie movie = mMoviesList.getMoviesList().get(position);

        if (movie != null) {
           mtvHolder.setMovieItem(movie, mContext);
        }
        return convertView;
    }

    static class MovieViewHolder {
        TextView mTvTitle;
        TextView mTvRating;
        TextView mTvDescription;
        ImageView mIvTitle;
        ImageView mIvPoster;

        public MovieViewHolder(TextView tvTitle, TextView tvRating, TextView tvDescription,
                               ImageView ivTitle, ImageView ivPoster) {
            this.mTvTitle = tvTitle;
            this.mTvRating = tvRating;
            this.mTvDescription = tvDescription;
            this.mIvTitle = ivTitle;
            this.mIvPoster = ivPoster;
        }

        public void setMovieItem (Movie movie, Context context) {
            this.mTvTitle.setText(movie.getTitle());
            this.mTvTitle.setTag("Title");
            this.mTvRating.setText(Double.toString(movie.getVoteAverage()));
            this.mTvRating.setTag("Rating");
            this.mTvDescription.setText(movie.getOverview());
            this.mTvDescription.setTag("Description");
            Picasso.with(context).load(IMAGE_BASE_URL + movie.getBackdropPath()).resize(360, 135).centerCrop().into(this.mIvTitle);
            this.mIvTitle.setTag("imgTitle");
            Picasso.with(context).load(IMAGE_BASE_URL + movie.getPosterPath()).resize(100, 150).centerCrop().into(this.mIvPoster);
            this.mIvPoster.setTag("imgPoster");
        }
    }
}
