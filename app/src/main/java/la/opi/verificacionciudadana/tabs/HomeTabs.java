package la.opi.verificacionciudadana.tabs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.EventsFragment;
import la.opi.verificacionciudadana.fragments.RecycleViewCardView;
import la.opi.verificacionciudadana.interfaces.PressedDetail;

public class HomeTabs extends ActionBarActivity implements PressedDetail{
    private boolean onPressedDetail = false;
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }


    @Override
    public void onBackPressed() {

        if (onPressedDetail) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_detail, RecycleViewCardView.newInstance()).commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_home, EventsFragment.newInstance()).commit();

            onPressedDetail = false;

        } else {
            finish();

        }


    }

    @Override
    public void onPressedDetail(Boolean pressed) {
        onPressedDetail = pressed;


    }



}
