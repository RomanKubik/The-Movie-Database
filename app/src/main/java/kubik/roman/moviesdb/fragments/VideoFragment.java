package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.movies_detailes.MovieVideos;

/**
 * Fragment for videos of movies
 */
public class VideoFragment extends BaseFragment {

    public static final String MOVIE_ID_TAG = "movie_id";

    private MovieVideos mMovieVideos;

    private int mMovieId;

    public static Fragment newInstance(int movieId) {

        VideoFragment videoFragment = new VideoFragment();

        Bundle args = new Bundle();
        args.putInt(MOVIE_ID_TAG, movieId);
        videoFragment.setArguments(args);

        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieId = getArguments().getInt(MOVIE_ID_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragmetn, container, false);

        return view;
    }
}
