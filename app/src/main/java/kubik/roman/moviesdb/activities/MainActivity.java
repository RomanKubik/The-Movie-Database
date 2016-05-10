package kubik.roman.moviesdb.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.fragments.MovieListPagerFragment;

public class MainActivity extends AppCompatActivity {

    private String mSessionId;
    private String mSessionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntents();
        Fragment fragment = MovieListPagerFragment.newInstance();
        loadFragment(fragment, true);
    }


    private void getIntents() {
        mSessionId = getIntent().getStringExtra(LoginActivity.ID);
        mSessionType = getIntent().getStringExtra(LoginActivity.SESSION_TYPE);
    }

    public void loadFragment(Fragment fragment, boolean isAddToBackStack) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            transaction.commit();
        } catch (Exception e) {
            Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
        }
        supportInvalidateOptionsMenu();
    }



    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof MovieListPagerFragment) {
            MainActivity.this.finish();
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }


}
