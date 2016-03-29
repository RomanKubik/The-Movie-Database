package kubik.roman.moviesdb.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class for data of details of movie
 */
public class MovieDetails extends Movie {

    public final String REQUESTED = "movie/";

    public static final String BELONGS_TO_COLLECTION = "belongs_to_collections";
    public static final String BUDGET = "budget";
    public static final String GENRES = "genres";
    public static final String HOMEPAGE = "homepage";
    public static final String IMDB_ID = "imdb_id";
    public static final String PRODUCTION_COMPANIES = "production_companies";
    public static final String PRODUCTION_COUNTRIES = "production_countries";
    public static final String REVENUE = "revenue";
    public static final String RUNTIME = "runtime";
    public static final String SPOKEN_LANGUAGES = "spoken_languages";
    public static final String STATUS = "status";
    public static final String TAGLINE = "tagline";

    private int mBelongsToCollection;
    private int mBudget;
    private ArrayList<Genre> mGenres;
    private String mHomepage;
    private String mImdbId;
    private ArrayList<ProductionCompany> mProductionCompanies;
    private ArrayList<ProductionCountry> mProductionCountries;
    private String mRevenue;
    private String mRuntime;
    private ArrayList<SpokenLanguage> mSpokenLanguages;
    private String mStatus;
    private String mTagline;

    public void setMovieDetailsFromJson(String jsonStr) throws JSONException {

        super.setMovieFromJson(jsonStr);

        JSONObject jsonObject = new JSONObject(jsonStr);

        try {
            mBelongsToCollection = jsonObject.getInt(BELONGS_TO_COLLECTION);
        } catch (JSONException e) {
            mBelongsToCollection = 0;
        }
        mBudget = jsonObject.getInt(BUDGET);
        JSONArray jsonArray = jsonObject.getJSONArray(GENRES);
        mGenres = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Genre genre = new Genre();
            genre.setGenreFromJson(jsonArray.getString(i));
            mGenres.add(genre);
        }
        mHomepage = jsonObject.getString(HOMEPAGE);
        mImdbId = jsonObject.getString(IMDB_ID);
        jsonArray = jsonObject.getJSONArray(PRODUCTION_COMPANIES);
        mProductionCompanies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ProductionCompany productionCompany = new ProductionCompany();
            productionCompany.setProductionCompanyFromJson(jsonArray.getString(i));
            mProductionCompanies.add(productionCompany);
        }
        jsonArray = jsonObject.getJSONArray(PRODUCTION_COUNTRIES);
        mProductionCountries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ProductionCountry productionCountry = new ProductionCountry();
            productionCountry.setProductionCountryFromJson(jsonArray.getString(i));
            mProductionCountries.add(productionCountry);
        }
        mRevenue = jsonObject.getString(REVENUE);
        mRuntime = jsonObject.getString(RUNTIME);
        jsonArray = jsonObject.getJSONArray(SPOKEN_LANGUAGES);
        mSpokenLanguages = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            SpokenLanguage spokenLanguage = new SpokenLanguage();
            spokenLanguage.setSpokenLanguageFromJson(jsonArray.getString(i));
            mSpokenLanguages.add(spokenLanguage);
        }
        mStatus = jsonObject.getString(STATUS);
        mTagline = jsonObject.getString(TAGLINE);
    }

    public int getBelongsToCollection() {
        return mBelongsToCollection;
    }

    public int getBudget() {
        return mBudget;
    }

    public ArrayList<Genre> getGenres() {
        return mGenres;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return mProductionCompanies;
    }

    public ArrayList<ProductionCountry> getProductionCountries() {
        return mProductionCountries;
    }

    public String getRevenue() {
        return mRevenue;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public ArrayList<SpokenLanguage> getSpokenLanguages() {
        return mSpokenLanguages;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getTagline() {
        return mTagline;
    }
}

