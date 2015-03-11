package la.opi.verificacionciudadana.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.maps.android.ui.IconGenerator;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/03/15.
 */
public class MarkerGoogleMap {

    public static Bitmap customMarker(Context context, String titleMarker) {

        IconGenerator iconGenerator = new IconGenerator(context);
        TextView textMarker = new TextView(context);
        textMarker.setGravity(Gravity.CENTER);
        int mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        textMarker.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
        textMarker.setPadding(padding, padding, padding, padding);
        textMarker.setText(titleMarker);
        textMarker.setTextColor(context.getResources().getColor(R.color.white));
        iconGenerator.setContentView(textMarker);
        iconGenerator.setColor(context.getResources().getColor(R.color.button_cristal));

        return iconGenerator.makeIcon();
    }


    public static Bitmap customMarkerSelected(Context context, String titleMarker) {

        IconGenerator iconGenerator = new IconGenerator(context);
        TextView textMarker = new TextView(context);
        textMarker.setGravity(Gravity.CENTER);
        int mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        textMarker.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
        textMarker.setPadding(padding, padding, padding, padding);
        textMarker.setText(titleMarker);
        textMarker.setTextColor(context.getResources().getColor(R.color.white));
        iconGenerator.setContentView(textMarker);
        iconGenerator.setColor(context.getResources().getColor(R.color.orange));

        return iconGenerator.makeIcon();
    }
}
