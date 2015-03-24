package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.SpinnerCustomAdapter;
import la.opi.verificacionciudadana.adapters.SpinnerCustomTownAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.dialogs.CustomDialog;
import la.opi.verificacionciudadana.models.State;
import la.opi.verificacionciudadana.models.Town;
import la.opi.verificacionciudadana.parser.ParserStatesSpinner;
import la.opi.verificacionciudadana.util.Config;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MunicipalFragment extends Fragment {

    public MunicipalFragment() {
    }

    public static MunicipalFragment newInstance() {

        MunicipalFragment municipalFragment = new MunicipalFragment();
        Bundle extraArguments = new Bundle();
        municipalFragment.setArguments(extraArguments);
        return municipalFragment;
    }

    Spinner spinnerMunicipal, spinnerStates;
    Button btnSavePreferences;
    String estado;
    String municipio;
    String idState;
    String idTwon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestStates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_municipal, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Localidad");
        spinnerMunicipal = (Spinner) rootView.findViewById(R.id.spinner_municipal);
        spinnerStates = (Spinner) rootView.findViewById(R.id.spinner_state);
        btnSavePreferences = (Button) rootView.findViewById(R.id.save);


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // ConfigurationPreferences.setStatePreference(getActivity(), estado);
                // ConfigurationPreferences.setMunicipioPreference(getActivity(), municipio);
                Toast.makeText(getActivity(), idState + "-" + estado + " " + idTwon + "-" + municipio, Toast.LENGTH_LONG).show();
                dialogCustom(R.string.location_change);

                // aqui esta el enn point1  userUpdate("599", "27", "1993");


            }
        });

    }


    private void userUpdate(String idUser, String idState, String idTwon) {

        //final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_session), true);
        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userUpdate(idUser, idState, idTwon)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);


                    System.out.println("RESPONSE UPDATE" + writer.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {


            }
        });

    }


    private void requestStates() {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getMexicoStates().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {

                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, "UTF-8");

                    SpinnerCustomAdapter spinnerCustomAdapter = new
                            SpinnerCustomAdapter(getActivity(), ParserStatesSpinner.paserState(writer.toString()));


                    //


                    for (Map.Entry<Integer, String> entry : ParserStatesSpinner.getStatehashMap().entrySet()) {

                        System.out.println("datos: " + entry.getKey() + " - " + entry.getValue());

                        Integer key = entry.getKey();
                        String value = entry.getValue();

                        if (value.equals("17")) {

                            System.out.println("TU LLAVE ES: " + key.toString());
                        }


                    }


                    ////

                    spinnerStates.setAdapter(spinnerCustomAdapter);
                  //  spinnerStates.setSelection(5);
                    spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            estado = ((State) spinnerStates.getItemAtPosition(position)).getName();
                            idState = ((State) spinnerStates.getItemAtPosition(position)).getId();

                            ArrayList<Town> townArrayList = ((State) spinnerStates.getItemAtPosition(position)).getTownArrayList();

                            HashMap<Integer, String> townHash = new HashMap<>();

                            int positionList = 0;

                            for (Town town : townArrayList) {
                                System.out.println("municipios "+town.getId());
                                townHash.put(positionList, town.getId());
                                positionList++;
                            }

                            ///

                            for (Map.Entry<Integer, String> entry : townHash.entrySet()) {

                                System.out.println("datos municipios: " + entry.getKey() + " - " + entry.getValue());

                                Integer key = entry.getKey();
                                String value = entry.getValue();

                                if (value.equals("1170")) {

                                    System.out.println("TU LLAVE MUNICIPIO ES : " + key.toString());
                                }


                            }


                            ///

                            final SpinnerCustomTownAdapter spinnerCustomTownAdapter = new SpinnerCustomTownAdapter(getActivity(), townArrayList);

                            Collections.sort(townArrayList, new ParserStatesSpinner.CustomComparator());

                            spinnerMunicipal.setAdapter(spinnerCustomTownAdapter);

                            spinnerMunicipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    idTwon = ((Town) spinnerMunicipal.getItemAtPosition(position)).getId();
                                    municipio = ((Town) spinnerMunicipal.getItemAtPosition(position)).getName();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void dialogCustom(int message) {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CustomDialog dialog = CustomDialog.newInstance(message);
        dialog.show(fragmentManager, Config.DIALOG_TEXT);

    }


}