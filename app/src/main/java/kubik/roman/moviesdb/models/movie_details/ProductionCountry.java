package kubik.roman.moviesdb.models.movie_details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roman on 3/26/2016.
 */
public class ProductionCountry {

    @SerializedName("iso_3166_1")
    private String mIso31661;
    private String name;

    public String getmIso31661() {
        return mIso31661;
    }

    public void setmIso31661(String mIso31661) {
        this.mIso31661 = mIso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
