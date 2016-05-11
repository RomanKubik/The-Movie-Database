package kubik.roman.moviesdb.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.fragments.GalleryDialog;
import kubik.roman.moviesdb.fragments.MovieDetailsFragment;
import kubik.roman.moviesdb.fragments.MovieDetailsPagerFragment;
import kubik.roman.moviesdb.fragments.MovieReviewFragment;
import kubik.roman.moviesdb.fragments.VideoListFragment;
import kubik.roman.moviesdb.models.movies_detailes.Review;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        MovieDetailsPagerFragment fragment = MovieDetailsPagerFragment.
                newInstance(getIntent().getExtras().getInt("movie_id"));
        loadFragment(fragment);
    }

    public void loadFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = this.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_movie_details, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
        supportInvalidateOptionsMenu();
    }

    public void forceLoadFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content_movie_details, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commit();
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        handleBackPress();
        //finish();
    }

    /**
     * Handles back-press events
     */
    private void handleBackPress() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_movie_details);
        if (fragment != null) {
            if (fragment instanceof MovieDetailsPagerFragment) {
                finish();
            } else if (fragment instanceof MovieDetailsFragment) {
                getSupportFragmentManager().popBackStackImmediate();
            } else if (fragment instanceof MovieReviewFragment) {
                getSupportFragmentManager().popBackStackImmediate();
            }

            Log.d("MovieDetailsActivity", fragment.getClass().getSimpleName());
        }

    }
}
