package kubik.roman.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.movies_list.GenresList;
import kubik.roman.moviesdb.models.movies_list.Movie;
import kubik.roman.moviesdb.models.movies_list.MoviesList;
import kubik.roman.moviesdb.util.Validator;

/**
 * Adapter for displaying smoothly ListView using ViewHolder pattern and RecyclerView
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private int lastPosition = -1;

    private MoviesList mMoviesList;
    private Context mContext;

    private OnItemClickListener mItemClickListener;

    public static GenresList mGenresList;


    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public MoviesListAdapter(MoviesList moviesList, GenresList genresList, Context context) {
        this.mMoviesList = moviesList;
        mGenresList = genresList;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMoviesList.getResults().get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRating.setText(String.valueOf(movie.getVoteAverage()));
        holder.genresString = mContext.getString(R.string.genres) + ": ";

        for (int i = 0; i < movie.getGenreIds().size(); i++) {
            for (int j = 0; j < mGenresList.getGenres().size(); j++) {
                if (mGenresList.getGenres().get(j).getId() == movie.getGenreIds().get(i)) {
                    holder.genresString += mGenresList.getGenres().get(j).getName() + ", ";
                    break;
                }
            }
        }
        if (Validator.isStringValid(holder.genresString)) {
            holder.genresString = holder.genresString.substring(0, holder.genresString.length() - 2);
        } else {
            holder.genresString = "Unknown";
        }
        holder.tvGenres.setText(holder.genresString);
        Picasso.with(mContext).load(BACKDROP_BASE_URL + movie.getBackdropPath()).fit().centerCrop().into(holder.imvTitle);
        Picasso.with(mContext).load(POSTER_BASE_URL + movie.getPosterPath()).fit().centerCrop().into(holder.imvPoster);

        setAnimation(holder.container, position);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.getResults().size();
    }

    private void setAnimation(LinearLayout container, int position) {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            container.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvRating;
        TextView tvGenres;
        ImageView imvTitle;
        ImageView imvPoster;
        String genresString;

        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            container = (LinearLayout) itemView.findViewById(R.id.list_item);

            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvRating = (TextView) itemView.findViewById(R.id.tv_rating);
            this.tvGenres = (TextView) itemView.findViewById(R.id.tv_genres);
            this.imvTitle = (ImageView) itemView.findViewById(R.id.iv_title);
            this.imvPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
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
