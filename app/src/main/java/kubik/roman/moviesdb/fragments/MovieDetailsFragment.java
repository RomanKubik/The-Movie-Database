package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.adapters.ImageListAdapter;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.movies_detailes.Image;
import kubik.roman.moviesdb.models.movies_detailes.MovieDetails;
import kubik.roman.moviesdb.models.movies_detailes.MovieImages;
import kubik.roman.moviesdb.models.movies_detailes.MovieReviews;
import kubik.roman.moviesdb.models.movies_detailes.MovieVideos;
import kubik.roman.moviesdb.util.Validator;

/**
 * Fragment for displaying full information about selected movie
 */
public class MovieDetailsFragment extends BaseFragment implements Response.ErrorListener {

    public static final String TAG = MovieDetailsFragment.class.getName();

    public static final String MOVIE_DETAILS_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOVIE_IMAGES = "/images";
    public static final String MOVIE_VIDEOS = "/videos";
    public static final String MOVIE_REVIEWS = "/reviews";
    public static final String MOVIE_SIMILAR = "/similar";
    public static final String API_KEY = "?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public static final String ID = "id";

    private MovieDetails mMovieDetails;
    private MovieImages mMovieImages;
    private MovieVideos mMovieVideos;
    private MovieReviews mMoviesReviews;

    private ImageListAdapter mImageAdapter;

    private int mMovieId;

    private View view;
    private ImageView mImvBackdrop;
    private ImageView mImvPoster;
    private TextView mTvTitle;
    private TextView mTvRating;
    private TextView mTvGenres;
    private TextView mTvOrigTitle;
    private TextView mTvDate;
    private TextView mTvRuntime;
    private TextView mTvDescription;
    private RecyclerView mRvPictures;
    private TextView mTvTagline;
    private TextView mTvBudget;
    private RecyclerView mRvCasts;
    private TextView mTvProdCountries;
    private TextView mTvProdCompanies;
    private RecyclerView mRvSimilar;

    private List<Image> mImageList = new ArrayList<>();

    private RequestQueue queue;

    private Set<Integer> mRequestsQueue = new HashSet<>();

    public static MovieDetailsFragment newInstance(int id) {

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ID, id);
        movieDetailsFragment.setArguments(args);

        return movieDetailsFragment;
    }

    private void registerRequestInQueue(int requestID) {
        if (!mRequestsQueue.contains(requestID)) {
            mRequestsQueue.add(requestID);
        }
    }

    private void unregisterRequestFromQueue(int requestID) {
        mRequestsQueue.remove(requestID);

        if (mRequestsQueue.isEmpty()) {
            setupViews();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt(ID);
        queue = Volley.newRequestQueue(getActivity());
        Log.d(TAG, "onCreate  " + mMovieId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details_fragment, container, false);
        Log.d(TAG, "onCreateView1");
        initializeViews();
        getAllMovieDetails();
        Log.d(TAG, "onCreateView2");
        return view;
    }

    private void getAllMovieDetails() {
        getMovieDetails();
        getMovieImages();
        getMovieVideos();
        getMovieReviews();
        getMovieSimilar();
    }

    private void getMovieDetails() {
        final int movieRequest = 1;
        registerRequestInQueue(movieRequest);
        String url = MOVIE_DETAILS_URL + String.valueOf(mMovieId) + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Validator.isStringValid(response)) {
                            unregisterRequestFromQueue(movieRequest);
                            Gson gson = new Gson();
                            mMovieDetails = gson.fromJson(response, MovieDetails.class);
                            Log.d(TAG, "Details");

                        }
                    }
                }, this);
        queue.add(stringRequest);
    }


    private void GET(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener);
        queue.add(request);
    }

    private void getMovieImages() {
        final int imagesRequest = 2;
        registerRequestInQueue(imagesRequest);
        String url = MOVIE_DETAILS_URL + String.valueOf(mMovieId) + MOVIE_IMAGES + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        mMovieImages = gson.fromJson(response, MovieImages.class);
                        Log.d(TAG, "Images");

                        unregisterRequestFromQueue(imagesRequest);
                    }
                }, this);
        queue.add(stringRequest);
    }

    private void getMovieVideos() {
        final int videoRequest = 3;
        registerRequestInQueue(videoRequest);
        String url = MOVIE_DETAILS_URL + String.valueOf(mMovieId) + MOVIE_VIDEOS + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        mMovieVideos = gson.fromJson(response, MovieVideos.class);
                        Log.d(TAG, "Videos");
                        unregisterRequestFromQueue(videoRequest);
                    }
                }, this);
        queue.add(stringRequest);
    }

    private void getMovieReviews() {
        final int reviewsRequest = 4;
        registerRequestInQueue(reviewsRequest);
        String url = MOVIE_DETAILS_URL + String.valueOf(mMovieId) + MOVIE_REVIEWS + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        mMoviesReviews = gson.fromJson(response, MovieReviews.class);
                        Log.d(TAG, "Reviews");
                        unregisterRequestFromQueue(reviewsRequest);
                    }
                }, this);
        queue.add(stringRequest);
    }

    private void getMovieSimilar() {
        final int similarRequest = 6;
        registerRequestInQueue(similarRequest);
        String url = MOVIE_DETAILS_URL + String.valueOf(mMovieId) + MOVIE_SIMILAR + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        unregisterRequestFromQueue(similarRequest);
                        Log.d(TAG, "Similar");
                    }
                }, this);
        queue.add(stringRequest);
    }

    private void initializeViews() {
        mImvBackdrop = (ImageView) view.findViewById(R.id.iv_backdrop);
        mImvPoster = (ImageView) view.findViewById(R.id.iv_poster);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvRating = (TextView) view.findViewById(R.id.tv_rating);
        mTvGenres = (TextView) view.findViewById(R.id.tv_genres);
        mTvOrigTitle = (TextView) view.findViewById(R.id.tv_orig_title);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvRuntime = (TextView) view.findViewById(R.id.tv_runtime);
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);

        mRvPictures = (RecyclerView) view.findViewById(R.id.rv_pictures);
       /* final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       */



        mTvTagline = (TextView) view.findViewById(R.id.tv_tagline);
        mTvBudget = (TextView) view.findViewById(R.id.tv_budget);
        mRvCasts = (RecyclerView) view.findViewById(R.id.rv_casts);
        mTvProdCountries = (TextView) view.findViewById(R.id.tv_countries);
        mTvProdCompanies = (TextView) view.findViewById(R.id.tv_companies);
        mRvSimilar = (RecyclerView) view.findViewById(R.id.rv_similar_movies);

        Log.d(TAG, "Initializing");


    }

    private void setupViews() {
        Picasso.with(getBaseActivity()).load(BACKDROP_BASE_URL + mMovieDetails.getBackdropPath()).
                fit().centerCrop().into(this.mImvBackdrop);
        Picasso.with(getBaseActivity()).load(POSTER_BASE_URL + mMovieDetails.getPosterPath()).
                fit().centerCrop().into(this.mImvPoster);
        mTvTitle.setText(mMovieDetails.getTitle());
        mTvRating.setText(String.valueOf(mMovieDetails.getVoteAverage()));
        String genres = getString(R.string.genres) + ": ";
        for (Genre genre : mMovieDetails.getGenres()) {
            genres += genre.getName() + "  ";
        }
        mTvGenres.setText(genres);
        mTvOrigTitle.setText(mMovieDetails.getOriginalTitle());
        mTvDate.setText(mMovieDetails.getReleaseDate());
        String runtime = String.valueOf(mMovieDetails.getRuntime()) + " " + getString(R.string.minutes);
        mTvRuntime.setText(runtime);
        mTvDescription.setText(mMovieDetails.getOverview());

        mImageList.clear();
        mImageList.addAll(mMovieImages.getBackdrops());
        mImageAdapter = new ImageListAdapter(mImageList, getActivity());
        mRvPictures.setAdapter(mImageAdapter);
        mRvPictures.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
       // mImageAdapter.notifyDataSetChanged();

        mTvTagline.setText(mMovieDetails.getTagline());
        mTvBudget.setText(String.valueOf(mMovieDetails.getBudget()));
        Log.d(TAG, "Setting up");

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
                    if (json != null) showToast(json);
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
