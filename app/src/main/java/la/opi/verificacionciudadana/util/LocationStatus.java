package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by Jhordan on 25/03/15.
 */
public class LocationStatus {

    public static Boolean locationStatus(Context context) {

        try {

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "error inesperado gps", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    

}
