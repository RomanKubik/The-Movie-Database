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
import kubik.roman.moviesdb.SlidingTabLayout;

/**
 * Fragment for displaying ViewPager of different types of lists
 */
public class MainListPagerFragment extends BaseFragment {

    private ViewPager mViewPager;
    private SlidingTabLayout mTabLayout;

    public static MainListPagerFragment newInstance() {

        return new MainListPagerFragment();
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

        CharSequence[] titles = {getString(R.string.popular), getString(R.string.top_rated), getString(R.string.upcoming), getString(R.string.now_playing)};

        PagerAdapter mPagerAdapter = new MovieListPagerAdapter(getFragmentManager(), titles, 4 );

        mViewPager = (ViewPager) view.findViewById(R.id.list_pager);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = (SlidingTabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setDistributeEvenly(true);

        mTabLayout.setViewPager(mViewPager);

        return view;
    }

    private class MovieListPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence[] mTitles;
        int mNumOfTabs;

        public MovieListPagerAdapter(FragmentManager fragmentManager, CharSequence[] titles, int numOfTabs) {
            super(fragmentManager);

            this.mTitles = titles;
            this.mNumOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            return MainListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
