package la.opi.verificacionciudadana.drawer;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.LoginActivity;
import la.opi.verificacionciudadana.activities.TutorialActivity;
import la.opi.verificacionciudadana.adapters.NavigationDrawerRecycleAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.fragments.AboutFragment;
import la.opi.verificacionciudadana.fragments.EventsFragment;
import la.opi.verificacionciudadana.fragments.RecycleViewCardView;
import la.opi.verificacionciudadana.fragments.SettingsFragment;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;
import la.opi.verificacionciudadana.interfaces.PressedDetail;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class HomeMain extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ActivityAnimate, PressedDetail, ActivitySettings, NavigationDrawerRecycleAdapter.ItemRecycleClickListener {

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
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //estoy prbando a preferencia


        if (!ConfigurationPreferences.getPlacePreference(HomeMain.this)) {
            ConfigurationPreferences.setStatePreference(HomeMain.this, "Localidad del server - ");
            ConfigurationPreferences.setMunicipioPreference(HomeMain.this, "(Guadalajara-jalisco)");
            ConfigurationPreferences.setPlacePreference(HomeMain.this, true);

        }


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activitySettingsToolbar();
        if (mTitle != null) {
            toolbar.setTitle(mTitle);
        } else {
            toolbar.setTitle(getResources().getString(R.string.app_name));
        }
        setSupportActionBar(toolbar);

        mTitle = toolbar.getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout, toolbar, this);


    }


    @Override
    public void itemRecycleClicked(int position) {
        onNavigationDrawerItemSelected(position);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        switch (position) {

            case 0:
                fragmentTransactionReplace(EventsFragment.newInstance());
                break;

            case 1:
                fragmentTransactionReplace(EventsFragment.newInstance());
                break;

            case 2:

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .commit();
                break;

            case 3:
                fragmentTransactionReplace(AboutFragment.newInstance());
                break;

        }

        Log.i("posicion", Integer.toString(position));
        onSectionAttached(position);

    }

    public void onSectionAttached(int number) {

        Log.i("posicion attached", Integer.toString(number));
        switch (number) {
            case 0:
                mTitle = getString(R.string.my_events);
                break;
            case 1:
                mTitle = getString(R.string.my_events);
                break;
            case 2:
                mTitle = getString(R.string.settings);
                break;
            case 3:
                mTitle = getString(R.string.about);
                break;

        }
        Log.i("posicion attached", mTitle.toString());

        if (toolbar != null) {
            toolbar.setTitle(mTitle);
            drawerLayout.closeDrawers();
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

                tutorial();

                break;
            case R.id.action_close_sesion:
                logOut();
                showToast("cerrar sesion");
                ConfigurationPreferences.clearMailPreference(HomeMain.this);
                startActivity(new Intent(HomeMain.this, LoginActivity.class));

                break;

        }


        return super.onOptionsItemSelected(item);
    }


    private void logOut() {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userLogOut().observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

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


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void tutorial() {

        Intent intent = new Intent(HomeMain.this, TutorialActivity.class);
        intent = intent.putExtra("tutorial", "showme_tutorial_login");
        startActivity(intent, animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle animateActivity(int animateEnter, int animateExit) {
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
        // super.getToolbar().setTitleTextColor(getResources().getColor(R.color.font_color_app));

        toolbar.setTitleTextColor(getResources().getColor(R.color.font_color_app));


    }


}
