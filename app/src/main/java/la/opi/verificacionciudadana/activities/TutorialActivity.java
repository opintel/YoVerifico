package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.drawer.HomeMain;
import la.opi.verificacionciudadana.fragments.TutorialFragment;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

public class TutorialActivity extends BaseActivity implements ActivityAnimate {
    String g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();
        super.getToolbar().setTitleTextColor(getResources().getColor(R.color.transparent));
        createDirectory();


        g = getIntent().getStringExtra("tutorial");

        if (ConfigurationPreferences.getTutorialPreference(this)) {

            if (g != null && g.equals("showme_tutorial") || g != null && g.equals("showme_tutorial_login")) {
                Log.e("tutorial", "showmetutorial");

                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, TutorialFragment.newInstance())
                            .commit();

                }


            } else {
                startActivity(new Intent(TutorialActivity.this, LoginScreenActivity.class));
                finish();
            }

        } else {

            Log.e("tutorial", "mostrar el tutorial");
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, TutorialFragment.newInstance())
                        .commit();


            }

        }


        ConfigurationPreferences.setTutorialPreference(this, true);


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_demo;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                if (g != null && g.equals("showme_tutorial")) {
                    login();
                } else if (g != null && g.equals("showme_tutorial_login")) {

                    home();

                } else {
                    super.onBackPressed();
                }

                break;

            case R.id.action_omited:
                if (g != null && g.equals("showme_tutorial")) {
                    login();
                } else if (g != null && g.equals("showme_tutorial_login")) {

                    home();

                }else{
                    login();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {


        if (g != null && g.equals("showme_tutorial")) {
            login();
        } else if (g != null && g.equals("showme_tutorial_login")) {

            home();

        } else {
            super.onBackPressed();
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void home() {
        startActivity(new Intent(TutorialActivity.this, HomeMain.class), animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void login() {
        startActivity(new Intent(TutorialActivity.this, LoginScreenActivity.class), animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle animateActivity(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }

    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationNavigationBar();
    }


    private void createDirectory() {

        // FALTA VALIDAR CUANDO ESTA LLENA LA MEMORIA
        if (StorageState.validateStorage(this)) {
            Log.i("DIRECTORY", "se puede crear ");
            StorageFiles.createDirectory(this);

        } else {
            Log.i("DIRECTORY", "no se puede crear");
        }
    }


}
