package kubik.roman.moviesdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.Models.Token;

/**
 * Class for working with HttpUrlConnection
 */
public class HttpConnectionManager {

    public static final String API_KEY = "api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";
    public static final String REQUESTED_URL = "http://api.themoviedb.org/3/";

    public static final String GET ="GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";

    public final String NOT_CONNECTED_MESSAGE = "No internet access";
    public final String FALSE_URL_MESSAGE = "False URL request";
    public final String FALSE_REQUEST_TYPE_MESSAGE = "False request type";

    private Context mContext;

    public HttpConnectionManager(Context context) {

        mContext = context;
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private class DownloadDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            if (!isConnected()) {
                return NOT_CONNECTED_MESSAGE;
            }
            try {
                switch (params[1]) {
                    case GET:
                        return downloadDataGet(params[0]);
                    case POST:
                        return downloadDataPost(params[0]);
                    case DELETE:
                        return downloadDataDelete(params[0]);
                    default:
                        return FALSE_REQUEST_TYPE_MESSAGE;
                }
            } catch (IOException e) {
                return FALSE_URL_MESSAGE;
            }
        }

        private String downloadDataDelete(String param) {
            return null;
        }

        private String downloadDataPost(String param) {
            return null;
        }

        private String downloadDataGet(String path) throws IOException {
            InputStream iStream = null;
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(GET);
                connection.setDoInput(true);
                connection.connect();
                iStream = connection.getInputStream();
                return convertIStreamToString(iStream);
            } finally {
                if (iStream != null) {
                    iStream.close();
                }
            }
        }

        private String convertIStreamToString(InputStream iStream) throws IOException {

            Reader reader = null;
            String thisLine = "";
            reader = new InputStreamReader(iStream, "UTF-8");
            BufferedReader buff = new BufferedReader(reader);
            String output = "";
            while ( (thisLine = buff.readLine()) != null) {
                output += thisLine;
            }
            return output;
        }

    }



    public String getRequest(String requested, String... params) throws ExecutionException, InterruptedException {
        String param = REQUESTED_URL + "\\" + requested + "?" + API_KEY + Arrays.toString(params);
        return new DownloadDataAsyncTask().execute(param, GET).get();
    }


}
