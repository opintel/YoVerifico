package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Jhordan on 05/02/15.
 */
public class VerificaCiudadFonts {
    public static Typeface typefaceRobotoMedium(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), VerificaCiudadConstants.ROBOTO_MEDIUM);
    }

    public static Typeface typefaceRobotoRegular(Context context) {

        return Typeface.createFromAsset(context.getResources().getAssets(), VerificaCiudadConstants.ROBOTO_REGULAR);
    }

    public static Typeface typefaceRobotoBold(Context context) {

        return Typeface.createFromAsset(context.getResources().getAssets(), VerificaCiudadConstants.ROBOTO_BOLD);
    }
}
