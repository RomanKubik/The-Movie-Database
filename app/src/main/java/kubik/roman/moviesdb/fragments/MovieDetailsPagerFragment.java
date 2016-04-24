package kubik.roman.moviesdb.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kubik.roman.moviesdb.R;

/**
 * Fragment for displaying ViewPager
 */
public class MovieDetailsPagerFragment extends BaseFragment {

    public static final String MOVIE_ID_TAG = "movie_id";

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private int mMovieId;

    public static MovieDetailsPagerFragment newInstance(int movieId) {

        MovieDetailsPagerFragment movieDetailsPagerFragment = new MovieDetailsPagerFragment();

        Bundle args = new Bundle();
        args.putInt(MOVIE_ID_TAG, movieId);
        movieDetailsPagerFragment.setArguments(args);

        return movieDetailsPagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieId = getArguments().getInt(MOVIE_ID_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detailes_pager, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.details_pager);
        mPagerAdapter = new DetailsPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        return view;
    }

    public class DetailsPagerAdapter extends FragmentStatePagerAdapter {


        public DetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MovieDetailsFragment.newInstance(mMovieId);
                case 1:
                    return VideoFragment.newInstance(mMovieId);
                default:
                    return MovieDetailsFragment.newInstance(mMovieId);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
