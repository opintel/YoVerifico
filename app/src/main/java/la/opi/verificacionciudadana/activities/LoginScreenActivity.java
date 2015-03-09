package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.LoginFragment;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

/**
 * Created by Jhordan on 08/03/15.
 */
public class LoginScreenActivity extends BaseActivity implements ActivityChange {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();

        getSupportFragmentManager().beginTransaction().replace(R.id.login_container, LoginFragment.newInstance()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:


                FragmentManager fragmentManager = getSupportFragmentManager();

                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }

                break;
            case R.id.tutorial:
                tutorial();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login_screen;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void tutorial() {
        Intent intent = new Intent(LoginScreenActivity.this, TutorialActivity.class);
        intent = intent.putExtra("tutorial", "showme_tutorial");
        startActivity(intent, activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }


    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationSystemBars();
    }


}
