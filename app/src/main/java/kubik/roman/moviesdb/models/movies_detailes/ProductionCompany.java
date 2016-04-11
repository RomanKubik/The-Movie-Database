package kubik.roman.moviesdb.models.movies_detailes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roman on 3/26/2016.
 */
public class ProductionCompany {

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
