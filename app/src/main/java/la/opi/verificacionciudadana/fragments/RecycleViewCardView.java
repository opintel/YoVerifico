package la.opi.verificacionciudadana.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.RecycleAdapter;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.interfaces.MapChangeState;
import la.opi.verificacionciudadana.interfaces.PressedDetail;
import la.opi.verificacionciudadana.models.Persona;
import la.opi.verificacionciudadana.util.Comunicater;

/**
 * Created by Jhordan on 07/11/14.
 */
public class RecycleViewCardView extends Fragment implements ItemListRecycleClickListener {

    public RecycleViewCardView() {
    }

    public static RecycleViewCardView newInstance() {

        RecycleViewCardView recycleViewCardView = new RecycleViewCardView();
        Bundle extraArguments = new Bundle();
        recycleViewCardView.setArguments(extraArguments);
        return recycleViewCardView;
    }

    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    ArrayList<Persona> personaArrayList;
    View rootView;
    PressedDetail onPressedDetail;

    MapChangeState mapChangeState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ocurrences, container, false);


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Marker> markers = Comunicater.getDatos();

        try {
            for (Marker m : markers) {
                Log.i("RECYCLE VIEW!!", m.getTitle());


            }
        } catch (Exception e) {
        }


        personaArrayList = new ArrayList<>();


        for (int i = 0; i <= 10; i++) {
            Persona persona = new Persona();
            persona.setId("Lorem ipsum dolor sit amet, consectetuer adipiscing elit.");
            persona.setComunity(" Colonia Iztapalapa Delicuencia elevada");
            persona.setName("Colonia Juárez 24 julio 16:00 pm");

            personaArrayList.add(persona);


        }

        for (Persona p : personaArrayList) {

            System.out.println(p);
        }


        recycleAdapter = new RecycleAdapter(personaArrayList, R.layout.item_list_ocurrences);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.setItemListRecycleClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // onPressedDetail = (PressedDetail) activity;
        // mapChangeState = (MapChangeState) getActivity().getSupportFragmentManager().findFragmentById(R.id.container_home);
    }


    @Override
    public void itemRecycleClicked(int position, String name) {

        String n = Integer.toString(position);
        Toast.makeText(getActivity(), n + " " + name, Toast.LENGTH_SHORT).show();




        //fragmentTransactionReplace(FragmentAccionDetail.newInstance());


    }

    private void fragmentTransactionReplace(Fragment fragmentInstance) {

        if (rootView != null) {
            ViewGroup group = (ViewGroup) rootView.getParent();

            if (group != null) {
                group.removeView(rootView);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_detail, fragmentInstance).addToBackStack("hola")
                        .commit();

                //  onPressedDetail.onPressedDetail(true);
                //    mapChangeState.onMapChangeState(true);


            }


        }


    }


}
