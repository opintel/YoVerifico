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
    public static final String USER_MUNICIPAL_ID = "user_municipio_id";
    public static final String MUNICIPAL_EMPITY = "municipio";
    public static final String PLACE_PREFERENCE = "Place_Pitagoras";
    public static final String USER_PLACE = "user_place";
    public static final Boolean PLACE_EMPITY = false;
    public static final String STATE_PREFERENCE = "State_Pitagoras";
    public static final String USER_STATE = "user_state";
    public static final String USER_STATE_ID = "user_state_id";
    public static final String STATE_EMPITY = "estado";
    public static final String EVIDENCE_PREFERENCE = "Evidence_Pitagoras";
    public static final String EVIDENCE_PREFERENCE_TWO = "Evidence_Pitagoras_two";
    public static final String EVIDENCE_PREFERENCE_PICTURES = "evidence_pitagoras_photos";
    public static final String USER_RATING = "user_rating";
    public static final String USER_PHOTOS_SIZE = "user_photos_size";
    public static final String USER_OBSERVATION = "user_observation";
    public static final String EVIDENCE_EMPITY = "evidence_empity";
    public static final String TUTORIAL_PREFERENCE = "tutorial_preference";
    public static final String TUTORIAL = "tutorial_state";
    public static final String HOUR_COMMENT = "comment_evidence_hour";
    public static final String HOUR_CALIFICATION = "calification_evidence_hour";
    public static final String HOUR_TAKE_PHOTOS = "photos_hour";

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

    public static void setRatingPreference(Context context, String rating) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_RATING, rating);
        editor.commit();

    }

    public static String getRatingPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_RATING, EVIDENCE_EMPITY);
    }

    public static void setPhotosSizePreference(Context context, String size) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE_PICTURES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHOTOS_SIZE, size);
        editor.commit();

    }

    public static String getPhotosSizePreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE_PICTURES, Context.MODE_PRIVATE);
        return prefs.getString(USER_PHOTOS_SIZE, EVIDENCE_EMPITY);
    }


    public static void setObservationPreference(Context context, String observation) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_OBSERVATION, observation);
        editor.commit();

    }

    public static String getObservationPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, Context.MODE_PRIVATE);
        return prefs.getString(USER_OBSERVATION, EVIDENCE_EMPITY);
    }



    public static void setHourCommentPreference(Context context, String hour) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HOUR_COMMENT, hour);
        editor.commit();

    }

    public static String getHourCommentPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, Context.MODE_PRIVATE);
        return prefs.getString(HOUR_COMMENT, EVIDENCE_EMPITY);
    }

    public static void setHourCalificationPreference(Context context, String hour) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HOUR_CALIFICATION, hour);
        editor.commit();

    }

    public static String getHourCalificationPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, Context.MODE_PRIVATE);
        return prefs.getString(HOUR_CALIFICATION, EVIDENCE_EMPITY);
    }

    public static void setPhotosHourPreference(Context context, String hour) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HOUR_TAKE_PHOTOS, hour);
        editor.commit();

    }

    public static String getPhotosHourPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EVIDENCE_PREFERENCE_TWO, Context.MODE_PRIVATE);
        return prefs.getString(HOUR_TAKE_PHOTOS, EVIDENCE_EMPITY);
    }

    public static void setTutorialPreference(Context context, Boolean observation) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TUTORIAL_PREFERENCE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TUTORIAL, observation);
        editor.commit();

    }

    public static Boolean getTutorialPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(TUTORIAL_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getBoolean(TUTORIAL, PLACE_EMPITY);
    }



    public static void setIdMunicipioPreference(Context context, String email) {

        SharedPreferences sharedPref = context.getSharedPreferences(MUNICIPAL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_MUNICIPAL_ID, email);
        editor.commit();
    }

    public static String getIdMunicipioPreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(MUNICIPAL_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_MUNICIPAL_ID, MUNICIPAL_EMPITY);

    }


    public static void setIdStatePreference(Context context, String state) {

        SharedPreferences sharedPref = context.getSharedPreferences(STATE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_STATE_ID, state);
        editor.commit();
    }

    public static String getIdStatePreference(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(STATE_PREFERENCE, Context.MODE_PRIVATE);
        return prefs.getString(USER_STATE_ID, STATE_EMPITY);

    }



}
