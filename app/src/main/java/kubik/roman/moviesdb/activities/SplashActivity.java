package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import kubik.roman.moviesdb.GsonGetRequest;
import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for downloading token and demonstrate logo
 */
public class SplashActivity extends Activity implements Response.ErrorListener {


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
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        GsonGetRequest<Token> request = new GsonGetRequest<>(TmdbUrlBuilder.getTokenUrlWithApiKey(),
                Token.class, null, new Response.Listener<Token>() {
            @Override
            public void onResponse(Token response) {
                mToken = response;
            }
        }, this);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    if(json != null) displayMessage(json);
                    break;
            }
        }
    }

    public String trimMessage(String json, String key){
        String trimmedString = "";
        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return trimmedString;
        }

        return trimmedString;
    }

    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

}
