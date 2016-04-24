package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import kubik.roman.moviesdb.models.logging.AuthSessionId;
import kubik.roman.moviesdb.models.logging.GuestSessionId;
import kubik.roman.moviesdb.models.logging.ValidateWithLogin;
import kubik.roman.moviesdb.models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for login and creating session or guest_session id
 */
public class LoginActivity extends Activity implements View.OnClickListener, Response.ErrorListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";

    public static final String SESSION_ID = "session_id";
    public static final String GUEST_SESSION_ID = "guest_session_id";

    public static final String ID = "id";
    public static final String SESSION_TYPE = "session_type";

    private Token mToken;
    private RequestQueue queue;

    private SharedPreferences mPreferences;

    private EditText mEtLogin;
    private EditText mEtPassword;
    private CheckBox mChbRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mToken = (Token) getIntent().getSerializableExtra("Token");

        initializeViews();

        loadAuthentication();
        queue = Volley.newRequestQueue(this);
    }

    private void initializeViews() {
        mEtLogin = (EditText)findViewById(R.id.et_login);
        mEtPassword = (EditText)findViewById(R.id.et_password);

        Button mBtnLogin = (Button) findViewById(R.id.btn_login);
        Button mBtnContinue = (Button) findViewById(R.id.btn_continue);

        mChbRemember = (CheckBox) findViewById(R.id.chb_remember);

        mBtnLogin.setOnClickListener(this);
        mBtnContinue.setOnClickListener(this);
        mEtLogin.setOnClickListener(this);
        mEtPassword.setOnClickListener(this);
    }

    private void loadAuthentication() {
        mPreferences = getPreferences(MODE_PRIVATE);
        mEtLogin.setText(mPreferences.getString(USERNAME, ""));
        mEtPassword.setText(mPreferences.getString(PASSWORD, ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                makeAuthSession();
                break;
            case R.id.btn_continue:
                makeGuestSession();
                break;
            case R.id.et_login:
                mEtLogin.setText("");
                break;
            case R.id.et_password:
                mEtPassword.setText("");
            default:
                break;
        }
    }

    private void makeAuthSession() {
        String url = TmdbUrlBuilder.getValidateWithLoginUrl(mToken.getRequestToken(),
                mEtLogin.getText().toString(), mEtPassword.getText().toString());
        GsonGetRequest<ValidateWithLogin> request = new GsonGetRequest<>(url, ValidateWithLogin.class,
                null, new Response.Listener<ValidateWithLogin>() {
            @Override
            public void onResponse(ValidateWithLogin response) {
                if (response.isSuccess()) {
                    if (mChbRemember.isChecked()) {
                        saveAuthentication();
                    }
                    getAuthId();
                } else {
                    Toast.makeText(getApplicationContext(),R.string.invalid_authentication,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, this);

        queue.add(request);
    }

    private void saveAuthentication() {
        mPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USERNAME, mEtLogin.getText().toString());
        editor.putString(PASSWORD, mEtPassword.getText().toString());
        editor.apply();
    }

    private void getAuthId() {
        String url = TmdbUrlBuilder.getNewSessionUrl(mToken.getRequestToken());
        GsonGetRequest<AuthSessionId> request = new GsonGetRequest<>(url, AuthSessionId.class, null,
                new Response.Listener<AuthSessionId>() {
                    @Override
                    public void onResponse(AuthSessionId response) {
                        String id = response.getSessionId();
                        startMainActivity(id, SESSION_ID);
                    }
                }, this);
        queue.add(request);
    }


    private void makeGuestSession() {
        GsonGetRequest<GuestSessionId> request = new GsonGetRequest<>(TmdbUrlBuilder.getGuestSessionUrl(),
                GuestSessionId.class, null, new Response.Listener<GuestSessionId>() {
            @Override
            public void onResponse(GuestSessionId response) {
                String id = response.getGuestSessionId();
                startMainActivity(id, GUEST_SESSION_ID);
            }
        }, this);

        queue.add(request);
    }

    private void startMainActivity(String sessionId, String sessionType) {
        if (sessionId != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(ID, sessionId);
            intent.putExtra(SESSION_TYPE, sessionType);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json = null;

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
