package la.opi.verificacionciudadana.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import la.opi.verificacionciudadana.R;


/**
 * Created by Jhordan on 4/02/15.
 */
public class SystemConfigurationBars {
    Activity activity;

    public SystemConfigurationBars(Activity activity) {
        this.activity = activity;
    }

    public void configurationSystemBars() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        // use System Bar Tin library for change color navigation bar
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.color.transparent);
        setStatusBarColor(activity.findViewById(R.id.statusBarBackground), activity.getResources().getColor(android.R.color.transparent));

    }


    public void configurationNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(false);
        }

        // use System Bar Tin library for change color navigation bar
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.color.transparent);
        setStatusBarColor(activity.findViewById(R.id.statusBarBackground), activity.getResources().getColor(android.R.color.transparent));
    }

    //api 19 only change navigation bar

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    //colocate status bar view and color
    private void setStatusBarColor(View statusBar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int actionBarHeight = getActionBarHeight();
            int statusBarHeight = getStatusBarHeight();
            //action bar height
            statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



}
