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
                finish();
            }
        }.start();

    }

    //Send request to get token using HttpConnectionManager
    public boolean sendTokenRequest() throws ExecutionException, InterruptedException, JSONException {
        HttpConnectionManager httpManager = new HttpConnectionManager(this);

        mToken = new Token();

        String jsonStr = httpManager.getRequest(mToken.REQUESTED, "");
        //Checking reply string
        if (jsonStr.equals(httpManager.NOT_CONNECTED_MESSAGE) || jsonStr.equals(httpManager.FALSE_URL_MESSAGE) || jsonStr.equals(httpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
            Toast.makeText(this, jsonStr,Toast.LENGTH_LONG).show();
            return false;
        } else {
            mToken.setTokenFromJsonStr(jsonStr);
            Log.d("SplashActivity", "Token was gotten");
            return true;
        }
    }

}
