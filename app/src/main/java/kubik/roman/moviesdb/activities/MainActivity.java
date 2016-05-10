package kubik.roman.moviesdb.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import kubik.roman.moviesdb.fragments.MainListFragment;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.fragments.MainListPagerFragment;


/*********
 * CHANGES
 ***********/

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
        loadFragment(MainListPagerFragment.newInstance());
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


    private void getIntents() {
        mSessionId = getIntent().getStringExtra(LoginActivity.ID);
        mSessionType = getIntent().getStringExtra(LoginActivity.SESSION_TYPE);
    }

    public void loadFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = this.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
        supportInvalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {

        switch (getCurrentFragment().getClass().getSimpleName()) {
            case "MovieDetailsPagerFragment":
                getSupportFragmentManager().popBackStack();
                break;
            default:
                loadLogInActivity();
        }

    }

    /**
     * Loads LoginActivity on back press action
     */
    private void loadLogInActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Returns instance of a Fragment user's currently on
     *
     * @return - Fragment
     */
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }


}
