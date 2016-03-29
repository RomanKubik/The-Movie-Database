package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for downloading token and demonstrate logo
 */
public class SplashActivity extends Activity implements HttpConnectionManager.OnRespondListener {

    private Token mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("SplashActivity", "onResume");


        final Intent intent = new Intent(this, LoginActivity.class);

        new CountDownTimer(4000, 2000) {

            public void onTick(long millisUntilFinished) {
                Log.d("SplashActivity", "onTick");
                try {
                    sendTokenRequest();
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    Log.d("onResume SplashActivity", e.toString());
                }
            }

            public void onFinish() {
                intent.putExtra("Token", mToken);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }.start();

    }

    //Send request to get token using HttpConnectionManager
    public void sendTokenRequest() throws ExecutionException, InterruptedException, JSONException {
        HttpConnectionManager httpManager = new HttpConnectionManager(this);

        httpManager.setOnRespondListener(this);

        mToken = new Token();

        httpManager.GET(mToken.REQUESTED);
    }


    @Override
    public void onRespond(String respond, String requested) throws JSONException, ExecutionException, InterruptedException {
        mToken.setTokenFromJsonStr(respond);
        Log.d("SplashActivity", "Token was gotten");
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
