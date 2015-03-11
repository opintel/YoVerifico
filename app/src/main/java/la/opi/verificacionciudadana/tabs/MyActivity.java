package la.opi.verificacionciudadana.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PageAdapter;
import la.opi.verificacionciudadana.fragments.AboutFragment;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myyy);

        setToolbar();
        setTabsandPager();

    }


    public void setToolbar() {

        //Configurando que el Toolbar como ActionBar
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //En este ejemplo, ocultamos el titulo de la aplicación, esto es opcional
       // toolbar.setTitle("I am Pusheen");
       // setSupportActionBar(toolbar);

    }

    public void setTabsandPager() {


        List<Fragment> listaFragments;
        HomePagerAdapter myPagerAdapter;
        listaFragments = new ArrayList<>();
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        listaFragments.add(AboutFragment.newInstance());
        myPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),listaFragments);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(myPagerAdapter);
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        mSlidingTabLayout.setViewPager(mViewPager);

    }


}
