package kubik.roman.moviesdb;

/**
 * Class for creating movie urls
 */
public class TmdbUrls {

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";
    private static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";

    //For creating token
    private static final String TOKEN_URL_WITH_API_KEY = "http://api.themoviedb.org/3/authentication/token/new?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";

    //For logging in
    private static final String VALIDATE_WITH_LOGIN_URL = "http://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";
    private static final String NEW_SESSION_URL = "http://api.themoviedb.org/3/authentication/session/new?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";
    private static final String GUEST_SESSION_URL = "http://api.themoviedb.org/3/authentication/guest_session/new?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";
    private static final String REQUEST_TOKEN = "&request_token=";
    private static final String USERNAME = "&username=";
    private static final String PASSWORD = "&password=";


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

    public static String getPosterBaseUrl(String path) {
        return POSTER_BASE_URL + path;
    }

    public static String getBackdropBaseUrl(String path) {
        return BACKDROP_BASE_URL + path;
    }

    public static String getTokenUrlWithApiKey() {
        return TOKEN_URL_WITH_API_KEY;
    }

    public static String getValidateWithLoginUrl(String requestToken, String username,
                                                 String password) {
        return VALIDATE_WITH_LOGIN_URL + REQUEST_TOKEN + requestToken + USERNAME + username +
                PASSWORD + password;
    }

    public static String getNewSessionUrl(String requestToken) {
        return NEW_SESSION_URL + REQUEST_TOKEN + requestToken;
    }

    public static String getGuestSessionUrl() {
        return GUEST_SESSION_URL;
    }
}
