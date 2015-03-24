package la.opi.verificacionciudadana.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.LoginActivity;
import la.opi.verificacionciudadana.activities.SettingsActivity;

public class HomeTabs extends ActionBarActivity {

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

                Intent intent = new Intent(HomeTabs.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.open_next, R.animator.close_main);


                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
