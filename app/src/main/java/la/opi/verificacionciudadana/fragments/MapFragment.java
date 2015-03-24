package la.opi.verificacionciudadana.fragments;

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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PagerAdapterDemo;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.views.MarkerGoogleMap;


public class MapFragment extends Fragment {

    public MapFragment() {
    }

    public static MapFragment newInstance() {

        MapFragment mapFragment = new MapFragment();
        Bundle extraArguments = new Bundle();
        mapFragment.setArguments(extraArguments);
        return mapFragment;
    }

    private GoogleMap googleMap;
    private MapView mapView;
    // private static final int CAMERA_ZOOM = 16; la ideal
    private static final int CAMERA_ZOOM = 11;
    Marker marker;
    View rootView;
    ViewPager pager;
    PagerAdapterDemo pagerAdapterImages;
    HashMap<Marker, Ocurrence> markerOcurrenceHashMap;
    Ocurrence ocurrencess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.mexico));


        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapterImages = new PagerAdapterDemo(getFragmentManager());
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        try {


            setUpMapIfNeeded(rootView);


        } catch (Exception e) {

            e.printStackTrace();

        }

        pager.setAdapter(pagerAdapterImages);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();


    }


    private void setUpMapIfNeeded(View rootView) {
        MapsInitializer.initialize(getActivity());

        if (googleMap == null) {

            googleMap = ((MapView) rootView.findViewById(R.id.map)).getMap();
            if (googleMap != null) {


                markerOcurrenceHashMap = new HashMap<>();
                int i = 0;



                for (Ocurrence ocurrence : Comunicater.getOcurrencesList()) {

                    if (i != 0) {

                        ocurrence.setPositionPager(i);
                        markerOcurrenceHashMap.put(marker(i, ocurrence.getLatitude(), ocurrence.getLongitude()), ocurrence);
                        pagerAdapterImages.addFragment(FragmentInfoWindowMap.newInstance(ocurrence.getPositionPager()+". "+ ocurrence.getAction() ,
                                bundleOcurrences((Ocurrence) Comunicater.getOcurrencesList().get(i) )));
                    }
                    i++;
                }


            }

        }


    }

    private Marker marker(final int i, String latitude, String longitude) {


        LatLng positionOcurrence = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(positionOcurrence, CAMERA_ZOOM);
        marker = googleMap.addMarker(new MarkerOptions().position(positionOcurrence)
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarker(getActivity(), Integer.toString(i)))));

        googleMap.animateCamera(update);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ocurrencess = markerOcurrenceHashMap.get(marker);
                pager.setCurrentItem(ocurrencess.getPositionPager() - 1);
                return false;
            }
        });

        pagerListener();

        return marker;
    }

    private void pagerListener() {


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (Map.Entry<Marker, Ocurrence> entry : markerOcurrenceHashMap.entrySet()) {

                    Marker key = entry.getKey();
                    Ocurrence value = entry.getValue();

                    key.setIcon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarker(getActivity(), Integer.toString(value.getPositionPager()))));
                    if ((value.getPositionPager() - 1) == position) {

                        key.setIcon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarkerSelected(getActivity(), Integer.toString(value.getPositionPager()))));
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(key.getPosition(), CAMERA_ZOOM);
                        googleMap.animateCamera(update);

                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private Bundle bundleOcurrences(Ocurrence ocurrence) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.TITLE_OCURRENCE, ocurrence.getAction());
        bundle.putString(Config.DATA_OCURRENCE, ocurrence.getOcurrenceData());
        bundle.putString(Config.TIME_OCURRENCE, ocurrence.getToHour() + " - " + ocurrence.getFromHour());
        bundle.putString(Config.DESCRIPTION_OCURRENCE, ocurrence.getDescription() + "\n \n" + ocurrence.getStrategyDescription());
        bundle.putString(Config.PLACE_OCURRENCE, ocurrence.getCalle() + " ," + ocurrence.getColonia() + " " + ocurrence.getAdmin2());
        bundle.putString(Config.CONTACT_OCURRENCE, ocurrence.getContactInfo());
        return bundle;

    }


}