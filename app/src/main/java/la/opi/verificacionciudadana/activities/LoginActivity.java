package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.LoginFragment;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.InternetConnection;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

/**
 * Created by Jhordan on 08/03/15.
 */
public class LoginActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_container, LoginFragment.newInstance()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (InternetConnection.isNetworkMobile(this)) {
            if (!InternetConnection.connectionState(this) && !InternetConnection.mobileConnection(this)) {
                showToast(R.string.not_internet_conection);
            }
        } else if (!InternetConnection.connectionState(this)) {
            showToast(R.string.not_internet_conection);
        }

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

                Intent intento = new Intent(LoginActivity.this, TutorialActivity.class);
                intento.putExtra(Config.TUTORIAL, Config.SHOWME_TUTORIAL);
                startActivity(intento);
                overridePendingTransition(R.animator.open_next, R.animator.close_main);

                break;

            case R.id.help:

                Intent intent = new Intent(LoginActivity.this, HelpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.open_next, R.animator.close_main);

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



    private void showToast(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationSystemBars();
    }


}
