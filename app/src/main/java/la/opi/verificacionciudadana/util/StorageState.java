package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 11/02/15.
 */
public class StorageState {


    public static boolean validateStorage(Context context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {

            Toast.makeText(context, context.getResources().getString(R.string.only_reading), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.not_avaliable), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
