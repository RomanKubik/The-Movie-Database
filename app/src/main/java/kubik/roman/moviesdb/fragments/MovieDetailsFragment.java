package kubik.roman.moviesdb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.MovieDetails;

/**
 * Fragment for displaying for information about selected movie
 */
public class MovieDetailsFragment extends Fragment implements HttpConnectionManager.OnRespondListener {


    public static final String ID = "id";


    private ImageView mImvBackdrop;
    private ImageView mImvPoster;
    private TextView mTvTitle;
    private TextView mTvRating;
    private TextView mTvGenres;
    private TextView mTvOrigTitle;
    private TextView mDate;
    private TextView mDescription;



    private HttpConnectionManager mHttpConnectionManager;
    private MovieDetails mMovieDetails;

    private Context mContext;

    private int mMovieId;

    public static MovieDetailsFragment newInstance(int id) {

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ID, id);
        movieDetailsFragment.setArguments(args);

        return movieDetailsFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt(ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details_fragment, null);
        mContext = container.getContext();
        try {
            mHttpConnectionManager = new HttpConnectionManager(mContext);
            mHttpConnectionManager.setOnRespondListener(this);
            getMovieDetails();
        } catch (InterruptedException | ExecutionException | JSONException e) {
            Log.d("MovieDetailFragment", e.toString());
        }
        setView();
        return view;
    }

    private void setView() {

    }

    private void getMovieDetails() throws InterruptedException, ExecutionException, JSONException {

        mMovieDetails = new MovieDetails();

        mHttpConnectionManager.GET(mMovieDetails.REQUESTED + Integer.toString(mMovieId));

    }


    @Override
    public void onRespond(String respond, String requested) throws JSONException,
            ExecutionException, InterruptedException {

            mMovieDetails.setMovieDetailsFromJson(respond);
            Log.d("Movie title :: ", mMovieDetails.getTitle());

    }



    @Override
    public void onError(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }
}
