package kubik.roman.moviesdb.models.movie_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for cast list.
 */
public class Credits {

    private int id;
    private List<Cast> cast = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
