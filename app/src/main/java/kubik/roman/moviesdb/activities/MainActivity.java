package kubik.roman.moviesdb.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import kubik.roman.moviesdb.fragments.MainListFragment;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.fragments.MainListPagerFragment;

public class MainActivity extends AppCompatActivity {

    public Toolbar mToolbar;

    private String mSessionId;
    private String mSessionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        getIntents();
        Fragment fragment = MainListPagerFragment.newInstance();
        loadFragment(fragment, true);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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

        if (currentFragment instanceof MainListFragment) {
            MainActivity.this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


}
