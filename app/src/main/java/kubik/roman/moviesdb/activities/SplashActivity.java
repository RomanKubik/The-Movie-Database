package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.Models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for downloading token and demonstrate logo
 */
public class SplashActivity extends Activity {

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
                startActivity(intent);
            }
        }.start();



    }

    @Override
    protected void onPause() {
        super.onPause();

        this.finish();
    }

    //Send request to get token using HttpConnectionManager
    public void sendTokenRequest() throws ExecutionException, InterruptedException, JSONException {
        HttpConnectionManager httpManager = new HttpConnectionManager(this);

        mToken = new Token();

        String jsonStr = httpManager.getRequest(mToken.REQUESTED, "");
        //Checking reply string
        if (jsonStr.equals(httpManager.NOT_CONNECTED_MESSAGE)) {
            Toast.makeText(this, httpManager.NOT_CONNECTED_MESSAGE,Toast.LENGTH_LONG).show();
        } else if (jsonStr.equals(httpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
            Toast.makeText(this, httpManager.FALSE_REQUEST_TYPE_MESSAGE,Toast.LENGTH_LONG).show();
        } else if (jsonStr.equals(httpManager.FALSE_URL_MESSAGE)){
            Toast.makeText(this, httpManager.FALSE_URL_MESSAGE,Toast.LENGTH_LONG).show();
        } else {
            mToken.setTokenFromJsonStr(jsonStr);
            Log.d("SplashActivity", "Token was gotten");
        }
    }

}
