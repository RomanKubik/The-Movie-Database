package kubik.roman.moviesdb.models.movies_detailes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roman on 3/26/2016.
 */
public class SpokenLanguage {

    public static final String ISO_639_1 = "iso_639_1";
    public static final String NAME = "name";

    private String mIso6391;
    private String mName;

    public void setSpokenLanguageFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        mIso6391 = jsonObject.getString(ISO_639_1);
        mName = jsonObject.getString(NAME);
    }

    public String getIso6391() {
        return mIso6391;
    }

    public String getName() {
        return mName;
    }
}
