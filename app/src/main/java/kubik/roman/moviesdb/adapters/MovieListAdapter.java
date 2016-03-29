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

import java.util.ArrayList;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MoviesList;

/**
 * Adapter for displaying smoothly ListView using ViewHolder pattern
 */
public class MovieListAdapter extends BaseAdapter {

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";


    private MoviesList mMoviesList;
    private Context mContext;

    public static ArrayList<Genre> mGenresList;

    public MovieListAdapter(MoviesList moviesList, ArrayList<Genre> genresList, Context context) {
        this.mMoviesList = moviesList;
        mGenresList = genresList;
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

            mtvHolder = new MovieViewHolder(convertView);

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
        TextView mTvGenres;
        ImageView mIvTitle;
        ImageView mIvPoster;
        String genresString;


        public MovieViewHolder(View convertView) {
            this.mTvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            this.mTvRating = (TextView) convertView.findViewById(R.id.tvRating);
            this.mTvGenres = (TextView) convertView.findViewById(R.id.tvGenres);
            this.mIvTitle = (ImageView) convertView.findViewById(R.id.iv_title);
            this.mIvPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
        }

        public void setMovieItem(Movie movie, Context context) {
            this.mTvTitle.setText(movie.getTitle());
            this.mTvTitle.setTag("Title");
            this.mTvRating.setText(String.valueOf(movie.getVoteAverage()));
            this.mTvRating.setTag("Rating");
            genresString = R.string.genres + " : ";

            for (int i = 0; i < movie.getGenreIds().size(); i++) {
                for (int j = 0; j < mGenresList.size(); j++) {
                    if (mGenresList.get(j).getId() == movie.getGenreIds().get(i)) {
                        genresString += mGenresList.get(j).getName() + "  ";
                        break;
                    }
                }
            }
            this.mTvGenres.setText(genresString);
            this.mTvGenres.setTag("Genres");
            Picasso.with(context).load(BACKDROP_BASE_URL + movie.getBackdropPath()).fit().centerCrop().into(this.mIvTitle);
            this.mIvTitle.setTag("imgTitle");
            Picasso.with(context).load(POSTER_BASE_URL + movie.getPosterPath()).fit().centerCrop().into(this.mIvPoster);
            this.mIvPoster.setTag("imgPoster");
        }
    }
}
