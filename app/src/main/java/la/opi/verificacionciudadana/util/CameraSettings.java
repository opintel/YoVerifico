package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 13/03/15.
 */
public class CameraSettings {

    public static Boolean checkCameraExist(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(context, context.getString(R.string.hasnot_camera), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
