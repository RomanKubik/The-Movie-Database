package kubik.roman.moviesdb.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class for representation of JSON authentication request
 */
public class Token implements Serializable{

    @SerializedName("expires_at")
    private String expiresAt;
    @SerializedName("request_token")
    private String requestToken;
    private boolean success;

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
