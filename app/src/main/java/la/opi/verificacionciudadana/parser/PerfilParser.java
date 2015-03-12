package la.opi.verificacionciudadana.parser;

import org.json.JSONException;
import org.json.JSONObject;

import la.opi.verificacionciudadana.api.EndPoint;

/**
 * Created by Jhordan on 06/03/15.
 */
public class PerfilParser {

    private static String profileId;
    private static String profileName;
    private static String profileMail;
    private static String profileStateId;
    private static String profileTownId;
    private static String profileStateName;
    private static String profileTownName;

    public static String getProfileStateName() {
        return profileStateName;
    }

    public static void setProfileStateName(String profileStateName) {
        PerfilParser.profileStateName = profileStateName;
    }

    public static String getProfileTownName() {
        return profileTownName;
    }

    public static void setProfileTownName(String profileTownName) {
        PerfilParser.profileTownName = profileTownName;
    }


    public static String getProfileId() {
        return profileId;
    }

    public static void setProfileId(String profileId) {
        PerfilParser.profileId = profileId;
    }

    public static String getProfileName() {
        return profileName;
    }

    public static void setProfileName(String profileName) {
        PerfilParser.profileName = profileName;
    }

    public static String getProfileMail() {
        return profileMail;
    }

    public static void setProfileMail(String profileMail) {
        PerfilParser.profileMail = profileMail;
    }

    public static String getProfileStateId() {
        return profileStateId;
    }

    public static void setProfileStateId(String profileStateId) {
        PerfilParser.profileStateId = profileStateId;
    }

    public static String getProfileTownId() {
        return profileTownId;
    }

    public static void setProfileTownId(String profileTownId) {
        PerfilParser.profileTownId = profileTownId;
    }


    public static void perfil(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            setProfileId(jsonObject.getString(EndPoint.PROFILE_ID));
            setProfileName(jsonObject.getString(EndPoint.PROFILE_NAME));
            setProfileMail(jsonObject.getString(EndPoint.PROFILE_EMAIL));
            setProfileStateId(jsonObject.getString(EndPoint.PROFILE_STATE_ID));
            setProfileTownId(jsonObject.getString(EndPoint.PROFILE_TWON_ID));
            setProfileStateName(jsonObject.getString(EndPoint.PROFILE_STATE_NAME));
            setProfileTownName(jsonObject.getString(EndPoint.PROFILE_TWON_NAME));


            System.out.println(getProfileId());
            System.out.println(getProfileName());
            System.out.println(getProfileMail());

            System.out.println(getProfileStateId());
            System.out.println(getProfileTownId());
            System.out.println(getProfileStateName());
            System.out.println(getProfileName());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
