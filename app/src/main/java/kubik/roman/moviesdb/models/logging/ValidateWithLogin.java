package kubik.roman.moviesdb.models.logging;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roman on 4/1/2016.
 */
public class ValidateWithLogin {
    @SerializedName("request_token")
    private String requestToken;
    private boolean success;

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
