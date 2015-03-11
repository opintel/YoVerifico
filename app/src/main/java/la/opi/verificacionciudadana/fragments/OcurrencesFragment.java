package la.opi.verificacionciudadana.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.DetailActivity;
import la.opi.verificacionciudadana.adapters.OcurrencesAdapter;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.interfaces.MapChangeState;
import la.opi.verificacionciudadana.interfaces.PressedDetail;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.util.Comunicater;

/**
 * Created by Jhordan on 07/11/14.
 */
public class OcurrencesFragment extends Fragment implements ItemListRecycleClickListener {

    public OcurrencesFragment() {
    }

    public static OcurrencesFragment newInstance() {

        OcurrencesFragment recycleViewCardView = new OcurrencesFragment();
        Bundle extraArguments = new Bundle();
        recycleViewCardView.setArguments(extraArguments);
        return recycleViewCardView;
    }

    RecyclerView recyclerView;
    OcurrencesAdapter recycleAdapter;
    ArrayList<Ocurrence> ocurrencesArrayListTest;
    View rootView;
    PressedDetail onPressedDetail;

    MapChangeState mapChangeState;

    Button explorer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ocurrences, container, false);

      /*  explorer = (Button) rootView.findViewById(R.id.btn_explorer);

        final ViewPager vpViewPager = (ViewPager) container;


        explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpViewPager.setCurrentItem(1);
            }
        });*/


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


        ocurrencesArrayListTest = new ArrayList<>();


        for (int i = 0; i <= 10; i++) {
            Ocurrence persona = new Ocurrence();
            persona.setId("Lorem ipsum dolor sit amet, consectetuer adipiscing elit.");
            persona.setComments(" Colonia Iztapalapa Delicuencia elevada");
            persona.setDescription("Colonia Juárez 24 julio 16:00 pm");

            ocurrencesArrayListTest.add(persona);


        }

        for (Ocurrence p : ocurrencesArrayListTest) {

            System.out.println(p);
        }


        recycleAdapter = new OcurrencesAdapter(ocurrencesArrayListTest, R.layout.item_list_ocurrences, getActivity());
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

        if (position != 0) {
            String n = Integer.toString(position);
            Toast.makeText(getActivity(), n + " " + name, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getActivity(), DetailActivity.class));
        }


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
