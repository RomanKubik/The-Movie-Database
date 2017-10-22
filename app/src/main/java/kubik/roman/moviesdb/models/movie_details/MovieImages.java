package kubik.roman.moviesdb.models.movie_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for movie images
 */
public class MovieImages {
    private int id;
    private List<Image> backdrops = new ArrayList<>();
    private List<Image> posters = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }
}
