package kubik.roman.moviesdb.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Movie details
 */
public class Movie {

    public static final String ADULT = "adult";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String GENRE_IDS = "genre_ids";
    public static final String ID = "id";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String POPULARITY = "popularity";
    public static final String TITLE = "title";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String VOTE_COUNT = "vote_count";

    private boolean mAdult;
    private String mBackdropPath;
    private ArrayList<Integer> mGenreIds;
    private int mId;
    private String mOriginalLanguage;
    private String mOriginalTitle;
    private String mOverview;
    private String mReleaseDate;
    private String mPosterPath;
    private double mPopularity;
    private String mTitle;
    private boolean mVideo;
    private double mVoteAverage;
    private int mVoteCount;

    public void setMovieFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        mAdult = jsonObject.getBoolean(ADULT);
        mBackdropPath = jsonObject.getString(BACKDROP_PATH);
        JSONArray jsonArray = jsonObject.getJSONArray(GENRE_IDS);
        mGenreIds = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            mGenreIds.add(jsonArray.getInt(i));
        }
        mId = jsonObject.getInt(ID);
        mOriginalLanguage = jsonObject.getString(ORIGINAL_LANGUAGE);
        mOriginalTitle = jsonObject.getString(ORIGINAL_TITLE);
        mOverview = jsonObject.getString(OVERVIEW);
        mReleaseDate = jsonObject.getString(RELEASE_DATE);
        mPosterPath = jsonObject.getString(POSTER_PATH);
        mPopularity = jsonObject.getDouble(POPULARITY);
        mTitle = jsonObject.getString(TITLE);
        mVideo = jsonObject.getBoolean(VIDEO);
        mVoteAverage = jsonObject.getDouble(VOTE_AVERAGE);
        mVoteCount = jsonObject.getInt(VOTE_COUNT);

    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        this.mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    public ArrayList<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.mGenreIds = genreIds;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setmriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        this.mPopularity = popularity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        this.mVideo = video;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        this.mVoteCount = voteCount;
    }


}
