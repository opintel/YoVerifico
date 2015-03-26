package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.model.Marker;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.DetailActivity;
import la.opi.verificacionciudadana.activities.InformacionActivity;
import la.opi.verificacionciudadana.activities.MapaActivity;
import la.opi.verificacionciudadana.adapters.OcurrencesAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.util.Comunicater;
import la.opi.verificacionciudadana.util.Config;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    LinearLayoutManager linearLayoutManager;
    ArrayList<Ocurrence> ocurrencesArrayListTest;
    View rootView;
    FloatingActionButton mapa;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ocurrences, container, false);

        swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
       // mapa = (FloatingActionButton) rootView.findViewById(R.id.btn_maps);
        swipeRefreshLayout.setColorSchemeResources(R.color.secondary_text, R.color.primary, R.color.accent);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


      /*  mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);
            }
        });*/


        ocurrencesArrayListTest = new ArrayList<>();

      //  testOcurrences();


        ocurrencesRequest(this);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        swipeRefreshLayout.setRefreshing(false);


                    }
                }, 2500);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int topRowVerticalPosition =

                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();

                System.out.println("VALOR ACTUAL" + topRowVerticalPosition);

                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });


    }

    @Override
    public void itemRecycleClicked(int position, Bundle ocurrences) {

        if (position != 0) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(Config.KEY_INTENT, Config.OCURRENCES_FRAGMENT);
            intent.putExtra(Config.BUNDLE_OCURRENCE, ocurrences);
            startActivity(intent);
            getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);


        }

    }

    public void ocurrencesRequest(final ItemListRecycleClickListener itemListRecycleClickListener) {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getOcurrences().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);


                    try {


                        for (Ocurrence p : ocurrenceArrayList(writer.toString())) {
                            System.out.println("datos ==  " + p.getOcurrenceData());
                            System.out.println("datos ==  " + p.getFromHour());
                            System.out.println("datos ==  " + p.getToHour());
                            System.out.println("datos ==  " + p.getAction());
                            System.out.println("datos ==  " + p.getDescription());


                        }


                        //ESTE ES EL QUE FUNCIONA CON EL ENDPOINT

                      ocurrencesArrayListTest = ocurrenceArrayList(writer.toString());

                        Comunicater.setOcurrencesList(ocurrencesArrayListTest);

                        recycleAdapter = new OcurrencesAdapter(ocurrencesArrayListTest, R.layout.item_list_ocurrences, getActivity());

                        //recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(recycleAdapter);

                        recycleAdapter.setItemListRecycleClickListener(itemListRecycleClickListener);


                    } catch (Exception e) {
                        Log.e(Config.ERROR_REGULAR_EXPRESION, e.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(Config.ERROR_RETROFIT, throwable.getMessage());
            }
        });


    }

    private ArrayList<Ocurrence> ocurrenceArrayList(String response) {


        Ocurrence nulo = new Ocurrence();
        nulo.setOcurrenceData("");
        nulo.setFromHour("");
        nulo.setToHour("");
        nulo.setAction("");
        nulo.setDescription("");

        ArrayList<Ocurrence> ocurrenceList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);


            ocurrenceList.add(0, nulo);
            for (int i = 0; i < jsonArray.length(); i++) {

                Ocurrence ocurrence = new Ocurrence();
                JSONObject ocurrenceItem = jsonArray.getJSONObject(i);
                ocurrence.setOcurrenceData(ocurrenceItem.getString(EndPoint.OCURRENCE_DATE));
                ocurrence.setAction(ocurrenceItem.getString(EndPoint.OCURRENCE_ACTION));
                ocurrence.setFromHour(ocurrenceItem.getString(EndPoint.OCURRENCE_STAR_HOUR));
                ocurrence.setToHour(ocurrenceItem.getString(EndPoint.OCURRENCE_FINISH_HOUR));
                ocurrence.setDescription(ocurrenceItem.getString(EndPoint.OCURRENCE_DESCRIPTION));
                ocurrence.setLatitude(ocurrenceItem.getString(EndPoint.OCURRENCE_LATITUDE));
                ocurrence.setLongitude(ocurrenceItem.getString(EndPoint.OCURRENCE_LONGITUDE));
                ocurrence.setCalle(ocurrenceItem.getString(EndPoint.OCURRENCE_CALLE));
                ocurrence.setColonia(ocurrenceItem.getString(EndPoint.OCURRENCE_COLONIA));
                ocurrence.setAdmin2(ocurrenceItem.getString(EndPoint.OCURRENCE_TOWN));
                ocurrence.setId(ocurrenceItem.getString(EndPoint.OCURRENCE_ID));

                ocurrenceList.add(ocurrence);
            }

        } catch (JSONException e) {

            Log.e("error - ocurrence", e.getMessage());


        }


        return ocurrenceList;
    }


    private void testOcurrences() {


        Ocurrence ocu = new Ocurrence();

        ocu.setOcurrenceData("");
        ocu.setFromHour("");
        ocu.setToHour("");
        ocu.setCalle("");
        ocu.setColonia("");
        ocu.setAdmin2("");
        ocu.setAction("");
        ocu.setDescription("Segunda etapa de construcción del faro de artes y oficios, en un predio de 4,000 m2, ubicado en eje 6 esquina L. Gracida, Col. Unidad Habiatacional Vicente Guerrero.");
        ocu.setStrategyDescription("Realizar acciones de prevención situacional que contribuyan a la reducción de oportunidades para la violencia y la delincuencia.");
        ocu.setLatitude("");
        ocu.setLongitude("");

        ocurrencesArrayListTest.add(ocu);


        Ocurrence ocurrence = new Ocurrence();

        ocurrence.setOcurrenceData("2015-03-17");
        ocurrence.setFromHour("01:00");
        ocurrence.setToHour("18:00");
        ocurrence.setCalle("Guadalajara");
        ocurrence.setColonia("Roma Norte");
        ocurrence.setAdmin2("Cuauhtemoc");
        ocurrence.setAction("Construcción de centro cultural");
        ocurrence.setDescription("Segunda etapa de construcción del faro de artes y oficios, en un predio de 4,000 m2, ubicado en eje 6 esquina L. Gracida, Col. Unidad Habiatacional Vicente Guerrero.");
        ocurrence.setStrategyDescription("Realizar acciones de prevención situacional que contribuyan a la reducción de oportunidades para la violencia y la delincuencia.");
        ocurrence.setLatitude("19.3683376");
        ocurrence.setLongitude("-99.1290171");
        ocurrence.setId("1224");

        ocurrencesArrayListTest.add(ocurrence);

        Ocurrence ocurrenced = new Ocurrence();

        ocurrenced.setOcurrenceData("2015-03-17");
        ocurrenced.setFromHour("01:00");
        ocurrenced.setToHour("18:00");
        ocurrenced.setCalle("Guadalajara");
        ocurrenced.setColonia("Roma Norte");
        ocurrenced.setAdmin2("Cuauhtemoc");
        ocurrenced.setAction("Construcción parke skate");
        ocurrenced.setDescription("Segunda etapa de construcción del faro de artes y oficios, en un predio de 4,000 m2, ubicado en eje 6 esquina L. Gracida, Col. Unidad Habiatacional Vicente Guerrero.");
        ocurrenced.setStrategyDescription("Realizar acciones de prevención situacional que contribuyan a la reducción de oportunidades para la violencia y la delincuencia.");
        ocurrenced.setLatitude("19.415334");
        ocurrenced.setLongitude("-99.166063");

        ocurrencesArrayListTest.add(ocurrenced);


        Ocurrence ocur = new Ocurrence();

        ocur.setOcurrenceData("2015-03-17");
        ocur.setFromHour("01:00");
        ocur.setToHour("18:00");
        ocur.setCalle("Guadalajara");
        ocur.setColonia("Roma Norte");
        ocur.setAdmin2("Cuauhtemoc");
        ocur.setAction("Construcción hacker Space");
        ocur.setDescription("Segunda etapa de construcción del faro de artes y oficios, en un predio de 4,000 m2, ubicado en eje 6 esquina L. Gracida, Col. Unidad Habiatacional Vicente Guerrero.");
        ocur.setStrategyDescription("Realizar acciones de prevención situacional que contribuyan a la reducción de oportunidades para la violencia y la delincuencia.");
        ocur.setLatitude("19.515334");
        ocur.setLongitude("-99.186063");

        ocurrencesArrayListTest.add(ocur);


        Comunicater.setOcurrencesList(ocurrencesArrayListTest);

        recycleAdapter = new OcurrencesAdapter(ocurrencesArrayListTest, R.layout.item_list_ocurrences, getActivity());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.setItemListRecycleClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recycleAdapter.setItemListRecycleClickListener(this);

    }


}
