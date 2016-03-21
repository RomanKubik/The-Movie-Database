package kubik.roman.moviesdb.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MoviesList;

/**
 * Created by roman on 3/21/2016.
 */
public class MovieTitleListAdapter extends BaseAdapter {

    private MoviesList mMoviesList;
    private Context mContext;

    public MovieTitleListAdapter(MoviesList moviesList, Context context) {
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

        MovieTitleViewHolder mtvHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.movie_title, parent, false);

            mtvHolder = new MovieTitleViewHolder();
            mtvHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(mtvHolder);
        } else {
            mtvHolder = (MovieTitleViewHolder)convertView.getTag();
        }

        Movie movie = mMoviesList.getMoviesList().get(position);

        if (movie != null) {
            mtvHolder.mTvTitle.setText(movie.getTitle());
            mtvHolder.mTvTitle.setTag(movie.getId());
        }
        return convertView;
    }

    static class MovieTitleViewHolder {
        TextView mTvTitle;
    }
}
