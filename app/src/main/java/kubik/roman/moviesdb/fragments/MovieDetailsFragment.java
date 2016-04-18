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
import java.util.List;

import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.adapters.CastsListAdapter;
import kubik.roman.moviesdb.adapters.ImageListAdapter;
import kubik.roman.moviesdb.adapters.SimilarListAdapter;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.movies_detailes.Cast;
import kubik.roman.moviesdb.models.movies_detailes.Credits;
import kubik.roman.moviesdb.models.movies_detailes.Image;
import kubik.roman.moviesdb.models.movies_detailes.MovieDetails;
import kubik.roman.moviesdb.models.movies_detailes.MovieImages;
import kubik.roman.moviesdb.models.movies_detailes.MovieReviews;
import kubik.roman.moviesdb.models.movies_detailes.MovieVideos;
import kubik.roman.moviesdb.models.movies_detailes.ProductionCompany;
import kubik.roman.moviesdb.models.movies_detailes.ProductionCountry;
import kubik.roman.moviesdb.models.movies_list.Movie;
import kubik.roman.moviesdb.models.movies_list.MoviesList;
import kubik.roman.moviesdb.util.Validator;

/**
 * Fragment for displaying full information about selected movie
 */
public class MovieDetailsFragment extends BaseFragment implements Response.ErrorListener {

    public static final String TAG = MovieDetailsFragment.class.getName();

    public static final String ID = "id";

    private MovieDetails mMovieDetails;
    private MovieImages mMovieImages;
    private MovieVideos mMovieVideos;
    private MovieReviews mMoviesReviews;
    private Credits mMovieCredits;
    private MoviesList mSimilarMovies;

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

    private List<Image> mImagesList = new ArrayList<>();
    private List<Cast> mCastsList = new ArrayList<>();
    private List<Movie> mSimilarMoviesList = new ArrayList<>();

    private RequestQueue queue;
    private Gson gson;

    public static MovieDetailsFragment newInstance(int id) {

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ID, id);
        movieDetailsFragment.setArguments(args);

        return movieDetailsFragment;
    }

    private void GET(String url, Response.Listener<String> listener,
                     Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener);
        queue.add(request);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_details_fragment, container, false);
        Log.d(TAG, "onCreateView1");
        initializeViews();
        getAllMovieDetails();
        Log.d(TAG, "onCreateView2");
        return view;
    }

    private void initializeViews() {
        //Backdrop and poster
        mImvBackdrop = (ImageView) view.findViewById(R.id.iv_title);
        mImvPoster = (ImageView) view.findViewById(R.id.iv_poster);
        //Title, rating, genres
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvRating = (TextView) view.findViewById(R.id.tv_rating);
        mTvGenres = (TextView) view.findViewById(R.id.tv_genres);
        //Original title
        mTvOrigTitle = (TextView) view.findViewById(R.id.tv_orig_title);
        //Fate, runtime
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvRuntime = (TextView) view.findViewById(R.id.tv_runtime);
        //Description
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);
        //Images
        mRvPictures = (RecyclerView) view.findViewById(R.id.rv_pictures);
        //Tagline and budget
        mTvTagline = (TextView) view.findViewById(R.id.tv_tagline);
        mTvBudget = (TextView) view.findViewById(R.id.tv_budget);
        //Casts
        mRvCasts = (RecyclerView) view.findViewById(R.id.rv_casts);
        //Companies and countries
        mTvProdCountries = (TextView) view.findViewById(R.id.tv_countries);
        mTvProdCompanies = (TextView) view.findViewById(R.id.tv_companies);
        //Similar movies
        mRvSimilar = (RecyclerView) view.findViewById(R.id.rv_similar_movies);

        Log.d(TAG, "Initialized");
    }

    private void getAllMovieDetails() {
        gson = new Gson();
        //Get Base details
        GET(TmdbUrlBuilder.getDetailsUrl(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    mMovieDetails = gson.fromJson(response, MovieDetails.class);
                    Log.d(TAG, "Details");
                    setupBaseInfo();
                }
            }
        }, this);
        //Get Images
        GET(TmdbUrlBuilder.getImagesUrl(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    mMovieImages = gson.fromJson(response, MovieImages.class);
                    Log.d(TAG, "Images");
                    setupImages();
                }
            }
        }, this);
        //Get Videos
        GET(TmdbUrlBuilder.getVideosUrl(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    mMovieVideos = gson.fromJson(response, MovieVideos.class);
                    Log.d(TAG, "Videos");
                }
            }
        }, this);
        //Get Reviews
        GET(TmdbUrlBuilder.getReviewsUrl(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    Gson gson = new Gson();
                    mMoviesReviews = gson.fromJson(response, MovieReviews.class);
                    Log.d(TAG, "Reviews");
                }
            }
        }, this);
        //Get Casts
        GET(TmdbUrlBuilder.getMovieCredits(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    mMovieCredits = gson.fromJson(response, Credits.class);
                    Log.d(TAG, "Similar");
                    setupCasts();
                }
            }
        }, this);
        //Get Similar movies
        GET(TmdbUrlBuilder.getSimilarListUrl(mMovieId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Validator.isStringValid(response)) {
                    mSimilarMovies = gson.fromJson(response, MoviesList.class);
                    Log.d(TAG, "Similar");
                    setupSimilarMovies();
                }
            }
        }, this);

    }

    private void setupBaseInfo() {
        Picasso.with(getBaseActivity()).load(TmdbUrlBuilder.getBackdropBaseUrl(mMovieDetails.getBackdropPath())).
                fit().centerCrop().into(this.mImvBackdrop);
        Picasso.with(getBaseActivity()).load(TmdbUrlBuilder.getPosterBaseUrl(mMovieDetails.getPosterPath()))
                .fit().centerCrop().into(this.mImvPoster);
        mTvTitle.setText(mMovieDetails.getTitle());
        mTvRating.setText(String.valueOf(mMovieDetails.getVoteAverage()));

        String genres = getString(R.string.genres) + ": ";
        for (Genre genre : mMovieDetails.getGenres()) {
            genres += genre.getName() + ", ";
        }
        if (Validator.isStringValid(genres)) {
            genres = genres.substring(0, genres.length() - 2);
        } else {
            genres = "Unknown";
        }
        mTvGenres.setText(genres);

        mTvOrigTitle.setText(mMovieDetails.getOriginalTitle());
        mTvDate.setText(mMovieDetails.getReleaseDate());
        String runtime = String.valueOf(mMovieDetails.getRuntime()) + " " +
                getString(R.string.minutes);
        mTvRuntime.setText(runtime);
        mTvDescription.setText(mMovieDetails.getOverview());

        if (Validator.isStringValid(mMovieDetails.getTagline()))
            mTvTagline.setText(mMovieDetails.getTagline());

        String budget = String.valueOf(mMovieDetails.getBudget()) + getString(R.string.dollars);
        mTvBudget.setText(budget);

        String str = "";
        for (ProductionCountry tmp: mMovieDetails.getProductionCountries()) {
            str += tmp.getName() + ", ";
        }
        if (Validator.isStringValid(str)) {
            str = str.substring(0, str.length() - 2);
        } else {
            str = "Unknown";
        }
        mTvProdCountries.setText(str);

        str = "";
        for (ProductionCompany tmp: mMovieDetails.getProductionCompanies()) {
            str += tmp.getName() + ", ";
        }
        if (Validator.isStringValid(str)) {
            str = str.substring(0, str.length() - 2);
        } else {
            str = "Unknown";
        }
        mTvProdCompanies.setText(str);
    }

    private void setupImages() {
        mImagesList.clear();
        mImagesList.addAll(mMovieImages.getBackdrops());
        ImageListAdapter imageAdapter = new ImageListAdapter(mImagesList, getActivity());
        mRvPictures.setAdapter(imageAdapter);
        mRvPictures.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        imageAdapter.SetOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private void setupCasts() {
        mCastsList.clear();
        mCastsList.addAll(mMovieCredits.getCast());
        CastsListAdapter castsListAdapter = new CastsListAdapter(mCastsList, getActivity());
        mRvCasts.setAdapter(castsListAdapter);
        mRvCasts.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        castsListAdapter.SetOnItemClickListener(new CastsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, mCastsList.get(position).getName());
            }
        });
    }

    private void setupSimilarMovies() {
        mSimilarMoviesList.clear();
        mSimilarMoviesList.addAll(mSimilarMovies.getResults());
        SimilarListAdapter similarListAdapter = new SimilarListAdapter(mSimilarMoviesList, getActivity());
        mRvSimilar.setAdapter(similarListAdapter);
        mRvSimilar.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        similarListAdapter.SetOnItemClickListener(new SimilarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = mSimilarMovies.getResults().get(position).getId();
                MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(id);
                navigateTo(movieDetailsFragment);
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
