package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.sql.SQLOutput;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.models.Perfil;
import la.opi.verificacionciudadana.parser.PerfilParser;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 07/02/15.
 */
public class PerfilFragment extends Fragment {

    public PerfilFragment() {
    }

    public static PerfilFragment newInstance() {

        PerfilFragment perfilFragment = new PerfilFragment();
        Bundle extraArguments = new Bundle();
        perfilFragment.setArguments(extraArguments);
        return perfilFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;

    TextView txtName, txtEmail, txtState, txtTwon;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        swipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorSchemeResources(R.color.secondary_text, R.color.primary, R.color.accent);
        swipeRefreshLayout.setRefreshing(true);


        txtName = (TextView) rootView.findViewById(R.id.name_profile);
        txtEmail = (TextView) rootView.findViewById(R.id.mail_profile);
        // txtTwon = (TextView) rootView.findViewById(R.id.twon_profile);
        txtState = (TextView) rootView.findViewById(R.id.state_profile);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profileRequest();


    }


    @Override
    public void onResume() {
        super.onResume();
        txtState.setText(ConfigurationPreferences.getStatePreference(getActivity()) +
                "," + ConfigurationPreferences.getMunicipioPreference(getActivity()));

    }

    public void profileRequest() {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getProfile().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);
                    try {

                        PerfilParser.perfil(writer.toString());
                        txtName.setText(PerfilParser.getProfileName());
                        txtEmail.setText(PerfilParser.getProfileMail());
                        ConfigurationPreferences.setIdMunicipioPreference(getActivity(), PerfilParser.getProfileTownId());
                        ConfigurationPreferences.setIdStatePreference(getActivity(), PerfilParser.getProfileStateId());
                        setSettingStateFirstTime();


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


    private void setSettingStateFirstTime() {

        if (!ConfigurationPreferences.getPlacePreference(getActivity())) {
            ConfigurationPreferences.setStatePreference(getActivity(), PerfilParser.getProfileStateName());
            ConfigurationPreferences.setMunicipioPreference(getActivity(), PerfilParser.getProfileTownName());
            ConfigurationPreferences.setPlacePreference(getActivity(), true);

        }
    }


}
