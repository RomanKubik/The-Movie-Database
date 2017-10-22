package kubik.roman.moviesdb.models.movie_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 4/2/2016.
 */
public class MovieVideos {
    private int id;
    private List<VideoDetails> results = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoDetails> getResults() {
        return results;
    }

    public void setResults(List<VideoDetails> results) {
        this.results = results;
    }
}
