package kubik.roman.moviesdb.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.movie_details.Image;

/**
 * Gallery of images
 */

public class GalleryDialog extends DialogFragment {

    public static final String IMAGE_LIST_TAG = "images";
    public static final String POSITION_TAG = "position";

    private List<Image> mImageList = new ArrayList<>();

    private int mCurrentPage;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    public static GalleryDialog newInstance(List<Image> imageList, int currentPosition) {
        GalleryDialog galleryDialog = new GalleryDialog();

        Bundle args = new Bundle();
        args.putParcelableArrayList(IMAGE_LIST_TAG, (ArrayList<? extends Parcelable>) imageList);
        args.putInt(POSITION_TAG, currentPosition);

        galleryDialog.setArguments(args);

        return galleryDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(GalleryDialog.class.getSimpleName(), "onCreate");
        //enables full screen of dialog
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mImageList = getArguments().getParcelableArrayList(IMAGE_LIST_TAG);
        mCurrentPage = getArguments().getInt(POSITION_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(GalleryDialog.class.getSimpleName(), "onCreateView");
        View view = inflater.inflate(R.layout.image_gallery_dialog, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.gallery_pager);
        mPagerAdapter = new GalleryPagerAdapter(getChildFragmentManager(), mCurrentPage);
        mViewPager.setAdapter(mPagerAdapter);

        return view;
    }



    private class GalleryPagerAdapter extends FragmentStatePagerAdapter {

        private int page;

        public GalleryPagerAdapter(FragmentManager fm, int currentPage) {
            super(fm);
            page = currentPage;
            Log.d(GalleryPagerAdapter.class.getSimpleName(), "Constructor");
        }

        @Override
        public Fragment getItem(int position) {

            Log.d(GalleryPagerAdapter.class.getSimpleName(), "GetItem");

            Image image;

            if (position + page < mImageList.size()) {
                image = mImageList.get(position + page);
            } else {
                image = mImageList.get((position + page) % mImageList.size());
            }

            return GalleryItemFragment.newInstance(image.getFilePath());
        }

        @Override
        public int getCount() {
            return mImageList.size();
        }
    }
}
