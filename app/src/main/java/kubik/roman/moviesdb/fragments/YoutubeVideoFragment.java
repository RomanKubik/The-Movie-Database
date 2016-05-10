package kubik.roman.moviesdb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import kubik.roman.moviesdb.Config;
import kubik.roman.moviesdb.R;

/**
 * Created by roman on 5/10/2016.
 */
public class YoutubeVideoFragment extends DialogFragment {

    private static final String KEY_TAG = "key";

    private String mVideoKey;

    private Context mContex;

    private YouTubePlayer mYoutubePlayer;


    public static YoutubeVideoFragment newInstance(String key) {
        YoutubeVideoFragment fragment = new YoutubeVideoFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TAG, key);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        this.mContex = context;

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.mVideoKey = getArguments().getString(KEY_TAG);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.youtube_video_fragment, container, false);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    mYoutubePlayer = youTubePlayer;
                    mYoutubePlayer.setFullscreen(true);
                    mYoutubePlayer.loadVideo(mVideoKey);
                    mYoutubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("Youtube", youTubeInitializationResult.toString());
            }
        });

        return view;
    }
}
