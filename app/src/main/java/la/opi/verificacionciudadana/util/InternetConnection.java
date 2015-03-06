package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jhordan on 25/02/15.
 */
public class InternetConnection {


    public static Boolean connectionState(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] information = connectivity.getAllNetworkInfo();
            if (information != null) {

                for (int i = 0; i < information.length; i++) {
                    if (information[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }


        return false;
    }


    public static Boolean mobileConnection(Context context) {


        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED) {

            return false;
        }

        return true;

    }


    public static Boolean isNetworkMobile(Context context){

        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {
            Log.i("CAMERA", "ESTE DISPOSITIVO NO TIENE CAMERA");
            return false;
        }

        return true;
    }

}
