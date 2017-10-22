package kubik.roman.moviesdb.util;

import android.text.TextUtils;

/**
 * Created by roman on 4/8/2016.
 */
public class Validator {

    private static Validator instance;

    private Validator() {
    }

    private static Validator getInstance() {
        if (instance == null) {
            instance = new Validator();
        }
        return instance;
    }


    public static boolean isStringValid(String str){
        return !TextUtils.isEmpty(str);
    }





}
