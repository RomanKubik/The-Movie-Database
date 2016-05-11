package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
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
import kubik.roman.moviesdb.adapters.ReviewListAdapter;
import kubik.roman.moviesdb.models.movie_details.MovieReviews;
import kubik.roman.moviesdb.models.movie_details.Review;

/**
 * Created by roman on 5/11/2016.
 */
public class MovieReviewFragment extends Fragment implements Response.ErrorListener {

    public static final String ID_TAG = "id";

    private int mMovieId;

    private RequestQueue queue;

    private View view;

    private RecyclerView mRvReviews;

    private MovieReviews mMovieReviews;
    private List<Review> mReviewList = new ArrayList<>();

    public static MovieReviewFragment newInstance(int movieId) {
        MovieReviewFragment movieReviewFragment = new MovieReviewFragment();

        Bundle args = new Bundle();
        args.putInt(ID_TAG, movieId);
        movieReviewFragment.setArguments(args);

        return movieReviewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieId = getArguments().getInt(ID_TAG);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reviews_fragment, container, false);

        initViews();
        getReviews();

        return view;
    }

    private void initViews() {
        mRvReviews = (RecyclerView) view.findViewById(R.id.rv_reviews);
    }

    private void getReviews() {
        GsonGetRequest<MovieReviews> requestReviews = new GsonGetRequest<>(TmdbUrlBuilder.getReviewsUrl(mMovieId),
                MovieReviews.class, null, new Response.Listener<MovieReviews>() {

            @Override
            public void onResponse(MovieReviews response) {
                mMovieReviews = response;
                setupReviews();
            }
        }, this);
        queue.add(requestReviews);
    }

    private void setupReviews() {
        mReviewList.clear();
        mReviewList.addAll(mMovieReviews.getResults());

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(mReviewList);
        mRvReviews.setAdapter(reviewListAdapter);
        mRvReviews.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json;

        if(error instanceof NoConnectionError) {
            json = "No internet Access, Check your internet connection.";
            Toast.makeText(getActivity(), json, Toast.LENGTH_LONG).show();
            return;
        }

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    if (json != null)
                        Toast.makeText(getActivity(), json, Toast.LENGTH_LONG).show();
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
