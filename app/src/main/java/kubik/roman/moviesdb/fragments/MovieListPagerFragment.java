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
 * Fragment for displaying ViewPager of different types of lists
 */
public class MovieListPagerFragment extends BaseFragment {

    public static MovieListPagerFragment newInstance() {

        return new MovieListPagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_pager, container, false);

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.list_pager);
        PagerAdapter mPagerAdapter = new MovieListPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        return view;
    }

    private class MovieListPagerAdapter extends FragmentStatePagerAdapter {

        public MovieListPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public Fragment getItem(int position) {
            return MainListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
