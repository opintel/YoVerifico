package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.DetailFragment;
import la.opi.verificacionciudadana.fragments.SettingsFragment;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;
import la.opi.verificacionciudadana.tabs.HomeTabs;

public class SettingsActivity extends BaseActivity implements ActivityAnimate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.container_settings, SettingsFragment.newInstance())
                    .commit();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_settings;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                login();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        login();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void login() {
        startActivity(new Intent(SettingsActivity.this, HomeTabs.class), animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle animateActivity(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }


}
