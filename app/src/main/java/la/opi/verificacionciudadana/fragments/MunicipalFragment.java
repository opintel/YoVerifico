package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.SpinnerCustomAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.dialogs.CustomDialog;
import la.opi.verificacionciudadana.models.State;
import la.opi.verificacionciudadana.parser.ParserStatesSpinner;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;
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

                String municipio = String.valueOf(spinnerMunicipal.getSelectedItem());
                ConfigurationPreferences.setStatePreference(getActivity(), estado);
                ConfigurationPreferences.setMunicipioPreference(getActivity(), municipio);
                Toast.makeText(getActivity(), estado + " - " + municipio, Toast.LENGTH_LONG).show();
                dialogCustom(R.string.location_change);


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

                    spinnerStates.setAdapter(spinnerCustomAdapter);
                    spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            estado = getState((State) spinnerStates.getItemAtPosition(position));

                            ArrayList<String> town = getNameTown((State) spinnerStates.getItemAtPosition(position));
                            Collections.sort(town);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.item_spinner_mexico,
                                    R.id.item_spinner,
                                    town);

                            spinnerMunicipal.setAdapter(adapter);

                            spinnerMunicipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    //  userMunicipio = adapter.getItem(position);
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
                Log.e(VerificaCiudadConstants.ERROR_RETROFIT, throwable.getMessage());
            }
        });


    }


    private ArrayList<String> getNameTown(State state) {
        return state.getTown();
    }

    private String getState(State state) {
        return state.getName();
    }


    private void dialogCustom(int message) {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CustomDialog dialog = CustomDialog.newInstance(message);
        dialog.show(fragmentManager, VerificaCiudadConstants.DIALOG_TEXT);

    }



}