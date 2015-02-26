package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jhordan on 25/02/15.
 */
public class TokenPreference {

    private Context context;
    private String token;
    public static final String TOKEN = "Token_Pitagoras";
    public static final String USER_TOKEN = "token_user";
    public static final String TOKEN_EMPITY = "token_user";



    public static void setTokenPreference(Context context , String token) {

        SharedPreferences sharedPref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public static String getTokenPreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        return prefs.getString(USER_TOKEN, TOKEN_EMPITY);

    }


}
