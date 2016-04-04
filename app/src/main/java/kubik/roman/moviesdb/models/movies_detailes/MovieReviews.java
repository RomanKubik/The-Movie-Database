package kubik.roman.moviesdb.models.movies_detailes;

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
}

