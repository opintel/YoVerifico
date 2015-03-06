package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PagerAdapterDemo;
import la.opi.verificacionciudadana.drawer.MainActivity;
import la.opi.verificacionciudadana.fragments.FragmentPagerDemoItem;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

public class DemoActivity extends BaseActivity implements ActivityChange, ActivitySettings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();
        activitySettingsToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DemoFragment())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_demo;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.menu_demo, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case android.R.id.home:
                demoActivity();
                break;

            case R.id.action_omited:
                Login();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Login() {
        startActivity(new Intent(DemoActivity.this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        demoActivity();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void demoActivity() {
        startActivity(new Intent(DemoActivity.this, MainActivity.class), activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }

    @Override
    public void activitySettingsToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.transparent));
    }

    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationNavigationBar();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DemoFragment extends Fragment {

        public DemoFragment() {
        }

        RelativeLayout relativeLayout;
        int vieneDelUltimo = 0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_demo_activity, container, false);


            ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
            PagerAdapterDemo pagerAdapterImages;
            pagerAdapterImages = new PagerAdapterDemo(getFragmentManager());
            pagerAdapterImages.addFragment(FragmentPagerDemoItem.newInstance(R.string.text_demo_1, R.string.lorem));
            pagerAdapterImages.addFragment(FragmentPagerDemoItem.newInstance(R.string.text_demo_2, R.string.lorem));
            pagerAdapterImages.addFragment(FragmentPagerDemoItem.newInstance(R.string.text_demo_3, R.string.lorem));

            relativeLayout = (RelativeLayout) rootView.findViewById(R.id.background_pager_layout);

            pager.setAdapter(pagerAdapterImages);


            CirclePageIndicator circlePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);

            circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onPageSelected(int position) {


                    Toast.makeText(getActivity(), Integer.toString(vieneDelUltimo), Toast.LENGTH_SHORT).show();


                    switch (position) {

                        case 0:


                            relativeLayout.setBackgroundResource(R.drawable.tres);
                            TransitionDrawable transitions = (TransitionDrawable) relativeLayout.getBackground();
                            transitions.startTransition(1000);

                            break;
                        case 1:

                            if (vieneDelUltimo == 3) {
                                relativeLayout.setBackgroundResource(R.drawable.cua);
                                TransitionDrawable transi = (TransitionDrawable) relativeLayout.getBackground();
                                transi.startTransition(1000);
                                vieneDelUltimo = 0;

                            } else {
                                relativeLayout.setBackgroundResource(R.drawable.dos);
                                TransitionDrawable transitionTwo = (TransitionDrawable) relativeLayout.getBackground();
                                transitionTwo.startTransition(1000);

                            }


                            break;
                        case 2:
                            relativeLayout.setBackgroundResource(R.drawable.uno);
                            TransitionDrawable transitionThree = (TransitionDrawable) relativeLayout.getBackground();
                            transitionThree.startTransition(1000);
                            vieneDelUltimo = 3;
                            break;
                    }
                }


                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            circlePageIndicator.setViewPager(pager);


            return rootView;
        }

    }


}
