package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jhordan on 25/02/15.
 */
public class ConfigurationPreferences {

    private Context context;
    private String token;
    public static final String TOKEN = "Token_Pitagoras";
    public static final String USER_TOKEN = "token_user";
    public static final String TOKEN_EMPITY = "token_user";
    public static final String MAIL_EMPITY = "Ciudadano Movil";
    public static final String MAIL_PREFERENCE = "Mail_Pitagoras";
    public static final String USER_MAIL = "user_mail";
    public static final String MUNICIPAL_PREFERENCE = "Municipio_Pitagoras";
    public static final String USER_MUNICIPAL = "user_municipio";
    public static final String MUNICIPAL_EMPITY = "municipio";
    public static final String PLACE_PREFERENCE = "Place_Pitagoras";
    public static final String USER_PLACE = "user_place";
    public static final Boolean PLACE_EMPITY = false;
    public static final String STATE_PREFERENCE = "State_Pitagoras";
    public static final String USER_STATE = "user_state";
    public static final String STATE_EMPITY = "estado";


    public static void setTokenPreference(Context context, String token) {

        SharedPreferences sharedPref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public static String getTokenPreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        return prefs.getString(USER_TOKEN, TOKEN_EMPITY);

    }

    public static void setMailPreference(Context context, String email) {

        SharedPreferences sharedPref = context.getSharedPreferences(MAIL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_MAIL, email);
        editor.commit();
    }

    public static String getMailPreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(MAIL_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_MAIL, MAIL_EMPITY);

    }

    public static void clearMailPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MAIL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static void setMunicipioPreference(Context context, String email) {

        SharedPreferences sharedPref = context.getSharedPreferences(MUNICIPAL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_MUNICIPAL, email);
        editor.commit();
    }

    public static String getMunicipioPreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(MUNICIPAL_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_MUNICIPAL, MUNICIPAL_EMPITY);

    }


    public static void setStatePreference(Context context, String state) {

        SharedPreferences sharedPref = context.getSharedPreferences(STATE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_STATE, state);
        editor.commit();
    }

    public static String getStatePreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(STATE_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_STATE, STATE_EMPITY);

    }

    public static void setPlacePreference(Context context, Boolean valor) {
        SharedPreferences sharedPref = context.getSharedPreferences(PLACE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(USER_PLACE, valor);
        editor.commit();
    }

    public static Boolean getPlacePreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PLACE_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getBoolean(USER_PLACE, PLACE_EMPITY);
    }


}
