package kubik.roman.moviesdb.models.movie_details;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.movies_list.Movie;

/**
 * Class for data of details of movie
 */
public class MovieDetails extends Movie {

    @SerializedName("belongs_to_collection")
    private Object belongsToCollection;
    private int budget;
    private List<Genre> genres = new ArrayList<>();
    private String homepage;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies = new ArrayList<>();
    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries = new ArrayList<>();
    private String revenue;
    private int runtime;
    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = new ArrayList<>();
    private String status;
    private String tagline;

    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}

