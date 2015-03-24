package la.opi.verificacionciudadana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.TutorialFragment;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

public class TutorialActivity extends BaseActivity {
    String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();
        super.getToolbar().setTitleTextColor(getResources().getColor(R.color.transparent));
        createDirectory();

        if (getIntent().getStringExtra(Config.FRAGMENT_TUTORIAL) != null) {
            activity = getIntent().getStringExtra(Config.FRAGMENT_TUTORIAL);
        } else {
            activity = "null";
        }


        if (ConfigurationPreferences.getTutorialPreference(this)) {
            if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)) {
                showTutorial(savedInstanceState);
            } else {
                startActivity(new Intent(TutorialActivity.this, LoginActivity.class));
                finish();
            }

        } else {
            showTutorial(savedInstanceState);
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

                if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)){
                    super.onBackPressed();
                    overridePendingTransition(R.animator.open_main, R.animator.close_next);
                }

                break;

            case R.id.action_omited:

                if (activity.equals("null")) {

                    Intent intent = new Intent(TutorialActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.animator.open_next, R.animator.close_main);

                }else if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)){
                    super.onBackPressed();
                    overridePendingTransition(R.animator.open_main, R.animator.close_next);
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)){
            super.onBackPressed();
            overridePendingTransition(R.animator.open_main, R.animator.close_next);
        }
    }

    private void signIn() {
        super.onBackPressed();
        overridePendingTransition(R.animator.open_main, R.animator.close_next);
    }

    private void showTutorial(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TutorialFragment.newInstance())
                    .commit();

        }
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
