package kubik.roman.moviesdb.models.person_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 5/11/2016.
 */
public class MovieCreditsPerson {

    private List<Cast> cast = new ArrayList<>();
    private int id;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
