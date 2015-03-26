package la.opi.verificacionciudadana.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jhordan on 25/03/15.
 */
public class DataConfig {


    public static String getFechaEnvio() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeData = new SimpleDateFormat(Config.DATA_FORMAT_PICTURE);
        return timeData.format(c.getTime());
    }


}
