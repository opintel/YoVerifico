package la.opi.verificacionciudadana.tabs;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.LoginScreenActivity;
import la.opi.verificacionciudadana.activities.SettingsActivity;
import la.opi.verificacionciudadana.fragments.EventsFragment;
import la.opi.verificacionciudadana.fragments.RecycleViewCardView;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;
import la.opi.verificacionciudadana.interfaces.PressedDetail;

public class HomeTabs extends ActionBarActivity implements ActivityAnimate {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_home, HomeTabsFragment.newInstance())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.settings_home:

                settings();

                break;

        }


        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void settings() {
        startActivity(new Intent(HomeTabs.this, SettingsActivity.class), animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle animateActivity(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }


}
