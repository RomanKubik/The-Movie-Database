package kubik.roman.moviesdb.models.person_details;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.models.movie_details.Image;

/**
 * Created by roman on 5/11/2016.
 */
public class PhotosPerson {

    private int id;
    private List<Image> profiles = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Image> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Image> profiles) {
        this.profiles = profiles;
    }
}
