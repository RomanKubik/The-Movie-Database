package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.os.Bundle;

import kubik.roman.moviesdb.Models.Token;
import kubik.roman.moviesdb.R;

/**
 * Created by roman on 3/15/2016.
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Token token = (Token) getIntent().getSerializableExtra("Token");
    }

}
