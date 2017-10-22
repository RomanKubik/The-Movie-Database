package kubik.roman.moviesdb.models.logging;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by roman on 4/1/2016.
 */
public class AuthSessionId implements Serializable {
    private boolean success;
    @SerializedName("session_id")
    private String sessionId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
