package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.interfaces.MapChangeState;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.OtroComunicator;

/**
 * Created by Jhordan on 07/02/15.
 */
public class EventsFragment extends Fragment implements MapChangeState {

    public EventsFragment() {
    }

    public static EventsFragment newInstance() {

        EventsFragment eventsFragment = new EventsFragment();
        Bundle extraArguments = new Bundle();

        eventsFragment.setArguments(extraArguments);
        return eventsFragment;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_event, container, false);


        Log.i("dato", "me he creado events Fragment");


        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // googleMap = mapView.getMap();
        // googleMap.setMyLocationEnabled(true);
        // googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {


            setUpMapIfNeeded(rootView);


        } catch (Exception e) {

        }

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setUpMapIfNeeded(View rootView) {
        MapsInitializer.initialize(getActivity());

        if (googleMap == null) {

            googleMap = ((MapView) rootView.findViewById(R.id.map)).getMap();
            if (googleMap != null) {




                markerList = new ArrayList<>();

                double lat[] = {19.415334, 19.412084, 19.411084, 19.410054, 19.402014, 19.392084};
                double lon[] = {-99.166063, -99.180576, -99.200576, -99.210576, -99.230576, -99.250576};


                for (int i = 0; i <= lat.length && i <= lon.length; i++) {
                    marker(i, lat[i], lon[i]);

                }


            }
        }


    }

    private void marker(int i, double LATITUD, double LONGITUD) {


        LatLng opiLatLng = new LatLng(LATITUD, LONGITUD);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(opiLatLng, CAMERA_ZOOM);

        marker = googleMap.addMarker(new MarkerOptions().position(opiLatLng).title("Rateritos " + Integer.toString(i)));
        googleMap.animateCamera(update);

        markerList.add(marker);
        Comunicater.setDatos(markerList);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(getActivity(), "HOLA", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public void onMapChangeState(boolean mapStatus) {


        Log.i("dato mapa", Boolean.toString(mapStatus));
        for (Marker marcador : markerList) {
            marcador.remove();
        }

        LatLng LatLng = new LatLng(LATITUD_OPI, LONGITUG_OPI);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LatLng, CAMERA_ZOOM);
        marker = googleMap.addMarker(new MarkerOptions().position(LatLng).title("Raterillo de vecindad"));
        googleMap.animateCamera(update);


    }
}
