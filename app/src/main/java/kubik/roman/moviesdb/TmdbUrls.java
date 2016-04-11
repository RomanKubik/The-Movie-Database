package kubik.roman.moviesdb;

/**
 * Class for creating movie urls
 */
public class TmdbUrls {

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";
    private static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private static final String MOVIE_DETAILS_URL = "http://api.themoviedb.org/3/movie/";
    private static final String MOVIE_IMAGES = "/images";
    private static final String MOVIE_CREDITS = "/credits";
    private static final String MOVIE_VIDEOS = "/videos";
    private static final String MOVIE_REVIEWS = "/reviews";
    private static final String MOVIE_SIMILAR = "/similar";
    private static final String API_KEY = "?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";


    private static TmdbUrls instance;

    private TmdbUrls() {
    }

    public static TmdbUrls getInstance() {
        if (instance == null) {
            instance = new TmdbUrls();
        }
        return instance;
    }

    public static String getDetailsUrl(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + API_KEY;
    }

    public static String getImagesUrl(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + MOVIE_IMAGES + API_KEY;
    }

    public static String getMovieCredits(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + MOVIE_CREDITS + API_KEY;
    }

    public static String getVideosUrl(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + MOVIE_VIDEOS + API_KEY;
    }

    public static String getReviewsUrl(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + MOVIE_REVIEWS + API_KEY;
    }

    public static String getSimilarListUrl(int movieId) {
        return MOVIE_DETAILS_URL + String.valueOf(movieId) + MOVIE_SIMILAR + API_KEY;
    }

    public static String getPosterBaseUrl() {
        return POSTER_BASE_URL;
    }

    public static String getBackdropBaseUrl() {
        return BACKDROP_BASE_URL;
    }
}
