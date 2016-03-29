package kubik.roman.moviesdb.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roman on 3/26/2016.
 */
public class Genre {

    public static final String ID = "id";
    public static final String NAME = "name";

    private int mId;
    private String mName;

    public void setGenreFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        mId = jsonObject.getInt(ID);
        mName = jsonObject.getString(NAME);
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
