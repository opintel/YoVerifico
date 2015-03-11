package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PagerAdapterDemo;
import la.opi.verificacionciudadana.interfaces.MarkerClickListener;
import la.opi.verificacionciudadana.interfaces.PressedDetail;
import la.opi.verificacionciudadana.util.Comunicater;


public class MapDetailFragment extends Fragment implements MarkerClickListener {

    public MapDetailFragment() {
    }

    public static MapDetailFragment newInstance() {

        MapDetailFragment mapDetailFragment = new MapDetailFragment();
        Bundle extraArguments = new Bundle();
        mapDetailFragment.setArguments(extraArguments);
        return mapDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_detail, container, false);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        PagerAdapterDemo pagerAdapterImages;
        pagerAdapterImages = new PagerAdapterDemo(getFragmentManager());
        pagerAdapterImages.addFragment(FragmentInfoWindowMap.newInstance("hola"));
        pagerAdapterImages.addFragment(FragmentInfoWindowMap.newInstance("hola 2"));
        pagerAdapterImages.addFragment(FragmentInfoWindowMap.newInstance("hola 3"));


        pager.setAdapter(pagerAdapterImages);




        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();




    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void markerClicked(String name) {
        Log.i("MAPA DETAIL", name);
    }



}