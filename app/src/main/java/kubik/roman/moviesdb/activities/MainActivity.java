package kubik.roman.moviesdb.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        supportInvalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {

        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof MainListFragment) {
            MainActivity.this.finish();
        } else {
            getFragmentManager().popBackStackImmediate();

        }

    }


}
