package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PagerAdapterDemo;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.interfaces.MapClick;
import la.opi.verificacionciudadana.interfaces.MarkerClickListener;
import la.opi.verificacionciudadana.interfaces.PressedDetail;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.models.Persona;
import la.opi.verificacionciudadana.util.Comunicater;
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
    private static final double LATITUD_OPI = 19.415334;
    private static final double LONGITUG_OPI = -99.166063;
    // private static final int CAMERA_ZOOM = 16; la ideal
    private static final int CAMERA_ZOOM = 11;

    Boolean mapChanger;
    Marker marker;
    List<Marker> markerList;
    View rootView;
    MarkerClickListener markerClickListener;


    TextView textView;
    ViewPager pager;
    PagerAdapterDemo pagerAdapterImages;
    ViewPager vpViewPager;
    HashMap<Marker, Ocurrence> ocurrenceMarker;
    Ocurrence ocurrencess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("México");


        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapterImages = new PagerAdapterDemo(getFragmentManager());
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // googleMap = mapView.getMap();

        //  googleMap.setMyLocationEnabled(true);

        // googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {


            setUpMapIfNeeded(rootView);


        } catch (Exception e) {

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


                markerList = new ArrayList<>();

                double lat[] = {19.415334, 19.412084, 19.411084, 19.410054, 19.402014, 19.392084};
                double lon[] = {-99.166063, -99.180576, -99.200576, -99.210576, -99.230576, -99.250576};


                ocurrenceMarker = new HashMap<Marker, Ocurrence>();

                for (int i = 0; i <= lat.length && i <= lon.length; i++) {


                    Ocurrence ocurrence = new Ocurrence();

                    ocurrence.setDescription("Ocurrencia: " + Integer.toString(i));
                    ocurrence.setId(Integer.toString(i));
                    ocurrenceMarker.put(marker(i, lat[i], lon[i]), ocurrence);
                    pagerAdapterImages.addFragment(FragmentInfoWindowMap.newInstance("id: "+ocurrence.getId() + ocurrence.getDescription()));


                }


            }
        }


    }

    private Marker marker(final int i, double LATITUD, double LONGITUD) {


        LatLng opiLatLng = new LatLng(LATITUD, LONGITUD);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(opiLatLng, CAMERA_ZOOM);


        marker = googleMap.addMarker(new MarkerOptions().position(opiLatLng)
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarker(getActivity(), Integer.toString(i)))));

        googleMap.animateCamera(update);


        markerList.add(marker);


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                ocurrencess = ocurrenceMarker.get(marker);
            //    Toast.makeText(getActivity(), ocurrencess.getId(), Toast.LENGTH_SHORT).show();
                pager.setCurrentItem(Integer.parseInt(ocurrencess.getId()));


                return false;
            }
        });


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (Map.Entry<Marker, Ocurrence> entry : ocurrenceMarker.entrySet()) {
                    // do something with the entry
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                    // the getters are typed:
                   Marker key = entry.getKey();
                   Ocurrence value = entry.getValue();

                    System.out.println(value.getId());
                    key.setIcon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarker(getActivity(), value.getId())));
                    if(Integer.parseInt(value.getId()) == position){

                        key.setIcon(BitmapDescriptorFactory.fromBitmap(MarkerGoogleMap.customMarkerSelected(getActivity(), value.getId())));
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(key.getPosition(), CAMERA_ZOOM);
                        googleMap.animateCamera(update);

                    }

                }







            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return marker;
    }




}