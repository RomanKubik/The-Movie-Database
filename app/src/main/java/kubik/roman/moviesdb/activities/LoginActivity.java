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
public class LoginActivity extends Activity implements View.OnClickListener, HttpConnectionManager.OnRespondListener {

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
    private HttpConnectionManager mHttpManager;

    private EditText mEtLogin;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mToken = (Token) getIntent().getSerializableExtra("Token");

        initializeViews();
        mHttpManager = new HttpConnectionManager(this);
        mHttpManager.setOnRespondListener(this);
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
                    sendLoginRequest();
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

    private void createGuestId() throws ExecutionException, InterruptedException, JSONException {
        mHttpManager.GET(REQUESTED_GUEST_ID);
    }


    private void sendLoginRequest() throws ExecutionException, InterruptedException, JSONException {
        String tokenRequest = mToken.REQUEST_TOKEN + "=" + mToken.getRequestedToken();
        String loginRequest = USERNAME + "=" + mEtLogin.getText().toString();
        String passwordRequest = PASSWORD + "=" + mEtPassword.getText().toString();

        mHttpManager.GET(REQUESTED_VALIDATE_WITH_LOGIN, tokenRequest, loginRequest, passwordRequest);
    }

    private void createSessionId() throws ExecutionException, InterruptedException, JSONException {
        String tokenRequest = mToken.REQUEST_TOKEN + "=" + mToken.getRequestedToken();

        mHttpManager.GET(REQUESTED_SESSION_NEW, tokenRequest);
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
    public void onRespond(String respond, String requested) throws JSONException, ExecutionException, InterruptedException {
        JSONObject jsonObject = new JSONObject(respond);

        switch (requested) {
            case REQUESTED_VALIDATE_WITH_LOGIN:
                isValid(jsonObject);
                break;
            case REQUESTED_SESSION_NEW:
                Log.d("LoginActivity", String.valueOf(jsonObject.getBoolean(SUCCESS)));
                startMainActivity(jsonObject.getString(SESSION_ID), SESSION_ID);
                break;
            case REQUESTED_GUEST_ID:
                Log.d("LoginActivity", String.valueOf(jsonObject.getBoolean(SUCCESS)));
                startMainActivity(jsonObject.getString(GUEST_SESSION_ID), GUEST_SESSION_ID);
                break;
            default:
                break;
        }

    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, R.string.invalidAuthentication, Toast.LENGTH_SHORT).show();
    }

    private void isValid(JSONObject jsonObject) throws JSONException, ExecutionException, InterruptedException {
        Log.d("LoginActivity", String.valueOf(jsonObject.getBoolean(SUCCESS)));
        if (jsonObject.getBoolean(SUCCESS)) {
            createSessionId();
        } else {
            Toast.makeText(this, R.string.invalidAuthentication, Toast.LENGTH_SHORT).show();
        }
    }
}
