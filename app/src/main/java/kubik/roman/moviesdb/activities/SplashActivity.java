package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.Models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for downloading token and demonstrate logo
 */
public class SplashActivity extends Activity {

    public static final String REQUESTED = "/authentication/token/new";

    private Token mToken;
    private HttpConnectionManager mHttpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHttpManager = new HttpConnectionManager(this);

        mToken = new Token();

        try {
            String jsonStr = mHttpManager.getRequest(REQUESTED,"");
            if (jsonStr.equals(mHttpManager.NOT_CONNECTED_MESSAGE)) {
                Toast.makeText(this,mHttpManager.NOT_CONNECTED_MESSAGE,Toast.LENGTH_SHORT).show();
            } else if (jsonStr.equals(mHttpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
                Toast.makeText(this,mHttpManager.NOT_CONNECTED_MESSAGE,Toast.LENGTH_SHORT).show();
            } else if (jsonStr.equals(mHttpManager.FALSE_URL_MESSAGE)){
                Toast.makeText(this,mHttpManager.NOT_CONNECTED_MESSAGE,Toast.LENGTH_SHORT).show();
            } else {
                mToken.setTokenFromJsonStr(jsonStr);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.d("onResume SplashActivity", e.toString());
        }

    }
}
