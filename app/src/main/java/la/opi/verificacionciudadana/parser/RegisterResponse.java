package la.opi.verificacionciudadana.parser;

import org.json.JSONException;
import org.json.JSONObject;

import la.opi.verificacionciudadana.api.EndPoint;

/**
 * Created by Jhordan on 25/02/15.
 */
public class RegisterResponse {
    private String id;
    private String email;
    private static String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        RegisterResponse.name = name;
    }

    public static void registerResponseError(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String error = jsonObject.getString("errors");
            System.out.println("RESPUESTA" + error);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void registerResponseSucces(String response) {
        String id, email, name;
        try {
            JSONObject jsonObject = new JSONObject(response);
            id = jsonObject.getString(EndPoint.PARSER_REGISTER_ID);
            email = jsonObject.getString(EndPoint.PARSER_REGISTER_MAIL);
            name = jsonObject.getString(EndPoint.PARSER_REGISTER_NAME);
            setName(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
