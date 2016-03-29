package kubik.roman.moviesdb.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roman on 3/26/2016.
 */
public class ProductionCompany {

    public static final String NAME = "name";
    public static final String ID = "id";

    private String mName;
    private int mId;

    public void setProductionCompanyFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        mName = jsonObject.getString(NAME);
        mId = jsonObject.getInt(ID);
    }

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }
}
