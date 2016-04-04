package kubik.roman.moviesdb.models.movies_detailes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roman on 3/26/2016.
 */
public class ProductionCountry {

    public static final String ISO_3166_1 = "iso_3166_1";
    public static final String NAME = "name";

    private String mIso31661;
    private String mName;

    public void setProductionCountryFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        mIso31661 = jsonObject.getString(ISO_3166_1);
        mName = jsonObject.getString(NAME);
    }

    public String getIso31661() {
        return mIso31661;
    }

    public String getName() {
        return mName;
    }
}
