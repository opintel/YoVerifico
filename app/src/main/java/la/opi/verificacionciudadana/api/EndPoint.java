package la.opi.verificacionciudadana.api;

/**
 * Created by Jhordan on 24/02/15.
 */
public class EndPoint {

    public final static String PITAGORAS_API_PRODUCCTION = "http://staging.pitagoras.opi.la";
    public static final String AWS_S3_PITAGORAS = "http://uploads.api.opi.la";
    public final static String EVIDENCE_MULTIMEDIA_S3 = "/levantamientos/multimedia/nite_owl";
    public final static String PART_FILE = "file";

    public final static String PITAGORAS_API_DEV = "http://10.230.20.79:3000";
    public final static String MEXICO_STATES = "/api/admin1/index_with_admin2";
    public final static String USER_SIGN_UP = "/users/create.json";
    public final static String USER_SIGN_IN = "/users/sign_in";
    public final static String HOME = "/home";
    public final static String OCURRENCES = "/occurrences.json";
    public final static String USER_UPDATE = "/users/{id}/update.json";
    public final static String OPI_WEB = "http://opi.la";
    public final static String USER_LOG_OUT = "/users/sign_out";
    public final static String USER_PROFILE_TEST = "/users/599.json";
    public final static String USER_PROFILE = "/users/get_info.json";
    public final static String USER_ANSWERS = "/answers";

    public final static String FIELD_AUTHENTICITY_TOKEN = "authenticity_token";
    public final static String FIELD_FORM_AUTHENTICITY_TOKEN = "form_authenticity_token";
    public final static String FIELD_UTF_8 = "utf8";
    public final static String FIELD_USER_NAME = "user[name]";
    public final static String FIELD_USER_MAIL = "user[email]";
    public final static String FIELD_USER_ROLE = "user[role_id]";
    public final static String FIELD_USER_STATE = "user[ciudadano_admin1_id]";
    public final static String FIELD_USER_TOWN = "user[ciudadano_admin2_id]";
    public final static String FIELD_USER_PASSWORD = "user[password]";
    public final static String FIELD_USER_CONFIRMATION = "user[password_confirmation]";
    public final static String FIELD_COMMIT = "commit";
    public final static String FIELD_USER_REMEMBER_ME = "user[remember_me]";
    public final static String FIELD_JSON = "json";

    public final static String PATH_ID = "id";

    public final static String HEADER_USER_AGENT = "User-Agent";
    public final static String HEADER_AGENT_NAME = "YoVerifico-Android-OPI";

    public final static String PARAMETER_REMEMBERME = "0";
    public final static String PARAMETER_COMMIT_SIGN_IN = "Acceder";
    public final static String PARAMETER_COMMIT_SIGN_UP = "Save";
    public final static String PARAMETER_UTF8 = "&#x2713";
    public final static String PARAMETER_USER_ROLE = "9";
    public final static String PARAMETER_TOKEN = "";


    // PARSER

    public final static String PARSER_ID_STATE = "id";
    public final static String PARSER_NAME_STATE = "name";
    public final static String PARSER_LIST_NAME_TOWN = "admin2";
    public final static String PARSER_NAME_TOWN = "name";
    public final static String PARSER_ID_TOWN = "id";
    public final static String PARSER_REGISTER_ID = "id";
    public final static String PARSER_REGISTER_NAME = "name";
    public final static String PARSER_REGISTER_MAIL = "email";

    public final static String PROFILE_ID = "id";
    public final static String PROFILE_NAME = "name";
    public final static String PROFILE_EMAIL = "email";
    public final static String PROFILE_STATE_ID = "ciudadano_admin1_id";
    public final static String PROFILE_TWON_ID = "ciudadano_admin2_id";
    public final static String PROFILE_STATE_NAME = "ciudadano_admin1_name";
    public final static String PROFILE_TWON_NAME = "ciudadano_admin2_name";


    public final static String OCURRENCE_ID = "id";
    public final static String OCURRENCE_DATE = "occurrence_date";
    public final static String OCURRENCE_CALLE = "calle";
    public final static String OCURRENCE_NO_EXTERIOR = "nro_exterior";
    public final static String OCURRENCE_NO_INTERIOR = "nro_interior";
    public final static String OCURRENCE_COLONIA = "colonia";
    public final static String OCURRENCE_STREETS = "entre_calles";
    public final static String OCURRENCE_CP = "cp";
    public final static String OCURRENCE_COMMENTS = "comments";
    public final static String OCURRENCE_CONTACT_INFO = "contact_info";
    public final static String OCURRENCE_LATITUDE = "latitude";
    public final static String OCURRENCE_LONGITUDE = "longitude";
    public final static String OCURRENCE_ACTION = "action";
    public final static String OCURRENCE_STAR_HOUR = "from_hour";
    public final static String OCURRENCE_FINISH_HOUR = "to_hour";
    public final static String OCURRENCE_STATE = "admin1";
    public final static String OCURRENCE_TOWN = "admin2";
    public final static String OCURRENCE_POLIGON = "polygon";
    public final static String OCURRENCE_USERNAME = "username";
    public final static String OCURRENCE_STAR_HOUR_DATE = "from_hour_datetime";
    public final static String OCURRENCE_FINISH_HOUR_DATE = "to_hour_datetime";
    public final static String OCURRENCE_DESCRIPTION = "description";
    public final static String OCURRENCE_GOAL_NAME = "goal_name";
    public final static String OCURRENCE_GOAL_DESCRIPTION = "goal_description";
    public final static String OCURRENCE_STRATEGY_NAME = "strategy_name";
    public final static String OCURRENCE_STRATEGY_DESCRIPTION = "strategy_description";
    public final static String OCURRENCE_COURSE_ACTION_NAME = "course_of_action_name";
    public final static String OCURRENCE_COURSE_ACTION_DESCRIPTION = "course_of_action_description";
    public final static String OCURRENCE_COURSE_ACTION = "action_description";
    public final static String OCURRENCE_ENTITY = "overseeing_entity";
    public final static String OCURRENCE_PRODUCT = "product";


}
