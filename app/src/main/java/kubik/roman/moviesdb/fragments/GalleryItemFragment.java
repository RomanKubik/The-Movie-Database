package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrlBuilder;

/**
 * Item of image gallery. Only one image fullscreen
 */
public class GalleryItemFragment extends Fragment {
    public static final String URL_TAG = "URL";
    private String mImgUrl;

    public static GalleryItemFragment newInstance(String imgUrl) {

        Bundle args = new Bundle();
        args.putString(URL_TAG, imgUrl);

        GalleryItemFragment fragment = new GalleryItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(GalleryItemFragment.class.getSimpleName(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(GalleryItemFragment.class.getSimpleName(), "onCreateView");

        mImgUrl = TmdbUrlBuilder.getGalleryBaseUrl(getArguments().getString(URL_TAG));

        View view = inflater.inflate(R.layout.image_gallery_item, container, false);
        ImageView imgGalleryItem = (ImageView) view.findViewById(R.id.imv_gallery_item);

        Picasso.with(getActivity()).load(mImgUrl).fit().centerInside().into(imgGalleryItem);

        return view;
    }


}
