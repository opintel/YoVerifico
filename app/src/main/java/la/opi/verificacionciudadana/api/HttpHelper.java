package la.opi.verificacionciudadana.api;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jhordan on 25/02/15.
 */
public class HttpHelper {

    private static String token ;
    public static String regexToken(String html){

        String regex = "<meta content=\"([^\\s]+)\" name=\"csrf-token\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(html);
        String token = "";
        if(match.find()){
            StringTokenizer st = new StringTokenizer(match.group(0),"\"");
            st.nextToken();
            token = st.nextToken();
        }

        return token;
    }

    public static boolean regexLoginSuccess(String html){
        boolean success;
        String regex = "id=\"flash_alert\"([^\\s]+)Correo o contra([^\\s]+)a in([^\\s]+)dos";
        Pattern pat = Pattern.compile(regex);
        Matcher match = pat.matcher(html);
        success = !match.find();
        return success;
    }

}
