package kubik.roman.moviesdb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.models.Token;
import kubik.roman.moviesdb.R;

/**
 * Activity for login and creating session or guest_session id
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String REQUESTED_VALIDATE_WITH_LOGIN = "authentication/token/validate_with_login";
    public static final String REQUESTED_SESSION_NEW = "authentication/session/new";
    public static final String REQUESTED_GUEST_ID = "authentication/guest_session/new";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String SUCCESS = "success";

    public static final String SESSION_ID = "session_id";
    public static final String GUEST_SESSION_ID = "guest_session_id";

    public static final String ID = "id";
    public static final String SESSION_TYPE = "session_type";


    private Token mToken;
    private HttpConnectionManager httpManager;

    private EditText mEtLogin;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mToken = (Token) getIntent().getSerializableExtra("Token");

        initializeViews();
        httpManager = new HttpConnectionManager(this);
    }


    private void initializeViews() {
        mEtLogin = (EditText)findViewById(R.id.etLogin);
        mEtPassword = (EditText)findViewById(R.id.etPassword);

        Button mBtnLogin = (Button) findViewById(R.id.btnLogin);
        Button mBtnContinue = (Button) findViewById(R.id.btnContinue);

        mBtnLogin.setOnClickListener(this);
        mBtnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnLogin:
                    login();
                    break;
                case R.id.btnContinue:
                    createGuestId();
                    break;
                default:
                    break;
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            Log.d("LoginActivity", e.toString());
        }

    }


    private void login() throws InterruptedException, ExecutionException, JSONException {
        if (sendLoginRequest()) {
            String mSessionId = createSessionId();
            startMainActivity(mSessionId, SESSION_ID);
        } else {
            Toast.makeText(this, R.string.invalidAuthentication, Toast.LENGTH_LONG).show();
        }
    }

    private void createGuestId() throws ExecutionException, InterruptedException, JSONException {
        String jsonStr = httpManager.getRequest(REQUESTED_GUEST_ID, "");

        if (jsonStr.equals(httpManager.NOT_CONNECTED_MESSAGE) || jsonStr.equals(httpManager.FALSE_URL_MESSAGE) || jsonStr.equals(httpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
            Toast.makeText(this, jsonStr, Toast.LENGTH_LONG).show();
        } else {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Log.d("LoginActivity", String.valueOf(jsonObject.getBoolean(SUCCESS)));
            startMainActivity(jsonObject.getString(GUEST_SESSION_ID), GUEST_SESSION_ID);
        }
    }


    private boolean sendLoginRequest() throws ExecutionException, InterruptedException, JSONException {
        String tokenRequest = mToken.REQUEST_TOKEN + "=" + mToken.getRequestedToken();
        String loginRequest = USERNAME + "=" + mEtLogin.getText().toString();
        String passwordRequest = PASSWORD + "=" + mEtPassword.getText().toString();

        String jsonStr = httpManager.getRequest(REQUESTED_VALIDATE_WITH_LOGIN, tokenRequest, loginRequest, passwordRequest);

        //Checking reply string
        if (jsonStr.equals(httpManager.NOT_CONNECTED_MESSAGE) || jsonStr.equals(httpManager.FALSE_URL_MESSAGE) || jsonStr.equals(httpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
           // Toast.makeText(this, jsonStr, Toast.LENGTH_LONG).show();
            return false;
        } else {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Log.d("LoginActivity", String.valueOf(jsonObject.getBoolean(SUCCESS)));
            return jsonObject.getBoolean(SUCCESS);
        }
    }

    private String createSessionId() throws ExecutionException, InterruptedException, JSONException {
        String tokenRequest = mToken.REQUEST_TOKEN + "=" + mToken.getRequestedToken();

        String jsonStr = httpManager.getRequest(REQUESTED_SESSION_NEW, tokenRequest);

        if (jsonStr.equals(httpManager.NOT_CONNECTED_MESSAGE) || jsonStr.equals(httpManager.FALSE_URL_MESSAGE) || jsonStr.equals(httpManager.FALSE_REQUEST_TYPE_MESSAGE)) {
            //Toast.makeText(this, jsonStr, Toast.LENGTH_LONG).show();
            return null;
        } else {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Log.d("LoginActivity", jsonObject.getString(SUCCESS));
            return jsonObject.getString(SESSION_ID);
        }
    }


    private void startMainActivity(String sessionId, String sessionType) {
        if (sessionId != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(ID, sessionId);
            intent.putExtra(SESSION_TYPE, sessionType);
            startActivity(intent);
            finish();
        }
    }

}
