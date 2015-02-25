package la.opi.verificacionciudadana.drawer;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.BaseActivity;
import la.opi.verificacionciudadana.activities.DemoActivity;
import la.opi.verificacionciudadana.fragments.AboutFragment;
import la.opi.verificacionciudadana.fragments.EventsFragment;
import la.opi.verificacionciudadana.fragments.PerfilFragment;
import la.opi.verificacionciudadana.fragments.RecycleViewCardView;
import la.opi.verificacionciudadana.fragments.SettingsFragment;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;
import la.opi.verificacionciudadana.interfaces.PressedDetail;


public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ActivityChange, PressedDetail, ActivitySettings {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private boolean onPressedDetail = false;

    boolean map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySettingsToolbar();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), super.getToolbar());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        switch (position) {

            case 0:
                fragmentTransactionReplace(EventsFragment.newInstance());
                break;

            case 1:
                fragmentTransactionReplace(PerfilFragment.newInstance());
                break;

            case 2:
                fragmentTransactionReplace(SettingsFragment.newInstance());
                break;

            case 3:
                fragmentTransactionReplace(AboutFragment.newInstance());
                break;

        }

        onSectionAttached(position);

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.my_events);
                break;
            case 1:
                mTitle = getString(R.string.my_perfil);
                break;
            case 2:
                mTitle = getString(R.string.settings);
                break;
            case 3:
                mTitle = getString(R.string.about);
                break;
        }


    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_demo:

                showToast("soy un demo");
                showDemo();

                break;
            case R.id.action_close_sesion:
                showToast("cerrar sesion");

                break;

        }


        return super.onOptionsItemSelected(item);
    }


    private void fragmentTransactionReplace(Fragment fragmentInstance) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentInstance)
                .commit();

    }


    private void showToast(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * calls activitiesChanged add two arguments
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showDemo() {

        startActivity(new Intent(MainActivity.this, DemoActivity.class), activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        finish();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }

    @Override
    public void onBackPressed() {

        String gg = Boolean.toString(onPressedDetail);

        Log.i("DATO", gg);
        if (onPressedDetail) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_detail, RecycleViewCardView.newInstance()).commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EventsFragment.newInstance()).commit();

            onPressedDetail = false;


            Log.i("DATO", "es verdadero");
        } else {
            finish();
            Log.i("DATO", "es falso");
        }


    }

    @Override
    public void onPressedDetail(Boolean pressed) {
        onPressedDetail = pressed;


    }

    @Override
    public void activitySettingsToolbar() {
        super.getToolbar().setBackgroundColor(getResources().getColor(R.color.toolbar_register));
        super.getToolbar().setTitleTextColor(getResources().getColor(R.color.toolbar_register_text));
    }



}
