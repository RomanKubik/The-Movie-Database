package kubik.roman.moviesdb.models.movies_list;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.models.Genre;

/**
 * Created by roman on 4/2/2016.
 */
public class GenresList {
    private List<Genre> genres = new ArrayList<>();

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
