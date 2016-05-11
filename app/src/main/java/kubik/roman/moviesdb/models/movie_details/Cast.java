package kubik.roman.moviesdb.models.movie_details;

import com.google.gson.annotations.SerializedName;

/**
 * Cast details
 */
public class Cast {
    @SerializedName("cast_id")
    private int castId;
    private String character;
    @SerializedName("credit_id")
    private String creditId;
    private int id;
    private String name;
    private int order;
    @SerializedName("profile_path")
    private String profilePath;

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
