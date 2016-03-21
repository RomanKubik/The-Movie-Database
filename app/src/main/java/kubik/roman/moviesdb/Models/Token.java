package kubik.roman.moviesdb.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Class for representation of JSON authentication request
 */
public class Token implements Serializable{

    //Requested URL
    public final String REQUESTED = "authentication/token/new";

    public final String REQUEST_TOKEN = "request_token";
    public static final String EXPIRES_AT = "expires_at";
    public static final String SUCCESS = "success";

    private String mExpiresAt;
    private String mRequestedToken;
    private boolean mSuccess;

    public String getExpiresAt() {
        return mExpiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.mExpiresAt = expiresAt;
    }

    public String getRequestedToken() {
        return mRequestedToken;
    }

    public void setRequestedToken(String requestedToken) {
        this.mRequestedToken = requestedToken;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public void setTokenFromJsonStr(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        mSuccess = jsonObject.getBoolean(SUCCESS);
        mExpiresAt = jsonObject.getString(EXPIRES_AT);
        mRequestedToken = jsonObject.getString(REQUEST_TOKEN);
    }

}
