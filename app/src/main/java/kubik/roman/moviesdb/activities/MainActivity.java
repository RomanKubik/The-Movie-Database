package kubik.roman.moviesdb.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kubik.roman.moviesdb.fragments.MainListFragment;
import kubik.roman.moviesdb.R;

public class MainActivity extends AppCompatActivity {

    private String mSessionId;
    private String mSessionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntents();

        loadFragment(new MainListFragment());
    }


    private void getIntents() {
        mSessionId = getIntent().getStringExtra(LoginActivity.ID);
        mSessionType = getIntent().getStringExtra(LoginActivity.SESSION_TYPE);
    }

    public void loadFragment(Fragment fragment) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commit();
        } catch (Exception e) {
            Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
        }
        supportInvalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof MainListFragment) {
            MainActivity.this.finish();
        } else {
            getSupportFragmentManager().popBackStack();

        }

    }


}
