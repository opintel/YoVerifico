package la.opi.verificacionciudadana.tabs;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.EventsFragment;
import la.opi.verificacionciudadana.fragments.OcurrencesFragment;
import la.opi.verificacionciudadana.fragments.PerfilFragment;


public class HomeTabsFragment extends Fragment {

    public HomeTabsFragment() {
    }

    public static HomeTabsFragment newInstance() {

        HomeTabsFragment municipalFragment = new HomeTabsFragment();
        Bundle extraArguments = new Bundle();
        municipalFragment.setArguments(extraArguments);
        return municipalFragment;
    }

    private List<Fragment> listaFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_tabs, container, false);

        View actionBarCustom = View.inflate(getActivity(), R.layout.activity_doss, null);


        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) actionBarCustom.findViewById(R.id.sliding_tabs);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setCustomView(actionBarCustom);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        listaFragments = new ArrayList<>();
        listaFragments.add(OcurrencesFragment.newInstance());
        listaFragments.add(PerfilFragment.newInstance());


        HomePagerAdapter myPagerAdapters = new HomePagerAdapter(getChildFragmentManager(), listaFragments);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(myPagerAdapters);
        mSlidingTabLayout.setViewPager(pager);


        return rootView;
    }


}