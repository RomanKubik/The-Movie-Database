package kubik.roman.moviesdb.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.adapters.MovieTitleListAdapter;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MoviesList;
import kubik.roman.moviesdb.R;

public class MainActivity extends AppCompatActivity {

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
        MovieTitleListAdapter adapter = new MovieTitleListAdapter(mMoviesList, this);
        ListView listView = (ListView) findViewById(R.id.lvTitle);
        assert listView != null;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ListItem position", String.valueOf(position));
            }
        });
        listView.setAdapter(adapter);
    }

    private void ShowMoviesList() throws ExecutionException, InterruptedException, JSONException {
        mHttpManager = new HttpConnectionManager(this);
        mMoviesList = new MoviesList();

        String jsonStr = mHttpManager.getRequest(mMoviesList.REQUESTED, "");

        mMoviesList.setMoviesListFromJson(jsonStr);

        for(Movie movie: mMoviesList.getMoviesList()) {
            Log.d("Movie title: ", movie.getTitle());
        }
    }
}
