package kubik.roman.moviesdb.models.movie_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roman on 4/2/2016.
 */
public class MovieReviews {
    private int id;
    private int page;
    private List<Review> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

