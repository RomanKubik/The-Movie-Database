package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for downloading token and demonstrate logo
 */
public class SplashActivity extends Activity implements Response.Listener<String>, Response.ErrorListener {


    public static final String TAG = SplashActivity.class.getSimpleName();

    private Token mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


    }

    @Override
    protected void onResume() {
        super.onResume();

        makeRequest();

        final Intent intent = new Intent(this, LoginActivity.class);

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                intent.putExtra("Token", mToken);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUrlBuilder.getTokenUrlWithApiKey(), this, this);

        queue.add(stringRequest);
    }

    private void createToken(String response) {
        Gson gson = new Gson();
        mToken = new Token();
        mToken = gson.fromJson(response, Token.class);
    }

    @Override
    public void onResponse(String response) {
        createToken(response);
        Log.d(TAG, mToken.getRequestToken());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, error.toString());
    }

}
