package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.GsonGetRequest;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.activities.MovieDetailsActivity;
import kubik.roman.moviesdb.adapters.VideoListAdapter;
import kubik.roman.moviesdb.models.movies_detailes.MovieVideos;
import kubik.roman.moviesdb.models.movies_detailes.VideoDetails;

/**
 * Created by roman on 5/10/2016.
 */
public class VideoListFragment extends Fragment implements Response.ErrorListener {

    public static final String MOVIE_ID = "movie_id";

    private int mMovieId;

    private RecyclerView mRvVideoList;

    private RequestQueue queue;

    private View view;

    private MovieVideos mMovieVideos;

    private List<VideoDetails> mVideosList = new ArrayList<>();

    private MovieDetailsActivity mActivity;

    public static Fragment newInstance(int id) {
        VideoListFragment fragment = new VideoListFragment();

        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt(MOVIE_ID);

        queue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (MovieDetailsActivity) getActivity();

        view = inflater.inflate(R.layout.video_list_fragment, container, false);
        mRvVideoList = (RecyclerView) view.findViewById(R.id.rv_video_list);
        getVideoList();
        return view;
    }

    private void getVideoList() {
        GsonGetRequest<MovieVideos> requestVideos = new GsonGetRequest<>(TmdbUrlBuilder.getVideosUrl(mMovieId),
                MovieVideos.class, null, new Response.Listener<MovieVideos>() {
            @Override
            public void onResponse(MovieVideos response) {
                mMovieVideos = response;
                setupVideos();
            }
        }, this);
        queue.add(requestVideos);
    }

    private void setupVideos() {
        mVideosList.clear();
        mVideosList.addAll(mMovieVideos.getResults());
        VideoListAdapter adapter = new VideoListAdapter(mVideosList, getActivity());
        mRvVideoList.setAdapter(adapter);
        mRvVideoList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        adapter.SetOnItemClickListener(new VideoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("VideoList", "onClick");
                String key = mVideosList.get(position).getKey();
                YoutubeVideoFragment youtubeVideoFragment = YoutubeVideoFragment.newInstance(key);
                youtubeVideoFragment.show(mActivity.getSupportFragmentManager(),
                        YoutubeVideoFragment.class.getSimpleName());
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    //if (json != null) showToast(json);
                    break;
            }
        }
    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
