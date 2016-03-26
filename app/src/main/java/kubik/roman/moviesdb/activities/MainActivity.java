package kubik.roman.moviesdb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.adapters.MovieListAdapter;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MoviesList;
import kubik.roman.moviesdb.R;

public class MainActivity extends AppCompatActivity implements HttpConnectionManager.OnRespondListener, AdapterView.OnItemClickListener {

    private String mSessionId;
    private String mSessionType;

    private HttpConnectionManager mHttpManager;
    private MoviesList mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSessionId = getIntent().getStringExtra(LoginActivity.ID);
        mSessionType = getIntent().getStringExtra(LoginActivity.SESSION_TYPE);

        try {
            ShowMoviesList();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.d("MainActivity", e.toString());
        }

        showList();

    }

    private void showList() {
        MovieListAdapter adapter = new MovieListAdapter(mMoviesList, this);
        ListView listView = (ListView) findViewById(R.id.lvTitle);
        assert listView != null;
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    private void ShowMoviesList() throws ExecutionException, InterruptedException, JSONException {
        mHttpManager = new HttpConnectionManager(this);
        mHttpManager.setOnRespondListener(this);

        mMoviesList = new MoviesList();

        mHttpManager.GET(mMoviesList.REQUESTED, "");

    }

    @Override
    public void onRespond(String respond, String requested) throws JSONException, ExecutionException, InterruptedException {
        mMoviesList.setMoviesListFromJson(respond);


        for(Movie movie: mMoviesList.getMoviesList()) {
            Log.d("Movie title: ", movie.getTitle());
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) parent.getItemAtPosition(position);
        Log.d("MOVIE :: ",movie.toString());
    }
}
