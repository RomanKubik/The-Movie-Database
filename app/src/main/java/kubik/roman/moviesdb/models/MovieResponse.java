package kubik.roman.moviesdb.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 3/29/2016.
 */
public class MovieResponse {

    int page;

    private List<MovieNew> results = new ArrayList<>();

    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalresults;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalresults() {
        return totalresults;
    }

    public void setTotalresults(int totalresults) {
        this.totalresults = totalresults;
    }

    public List<MovieNew> getResults() {
        return results;
    }

    public void setResults(List<MovieNew> results) {
        this.results = results;
    }
}
