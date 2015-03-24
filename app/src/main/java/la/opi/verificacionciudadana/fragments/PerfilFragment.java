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

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.parser.PerfilParser;
import la.opi.verificacionciudadana.util.Config;
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

        //  profileRequest();

        answers();
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

                        System.out.println("PERFIL RESPONSE: " + writer.toString());


                        PerfilParser.perfil(writer.toString());


                        txtName.setText(PerfilParser.getProfileName());
                        txtEmail.setText(PerfilParser.getProfileMail());
                        txtState.setText(PerfilParser.getProfileStateName() + "," + PerfilParser.getProfileTownName());


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


    public void answers() {

        JSONArray jsonArray = new JSONArray();

        JSONArray verificaciones = new JSONArray();
        JSONArray verificaciones_edit = new JSONArray();
        JSONObject answer = new JSONObject();
        JSONObject actividad = new JSONObject();
        JSONObject locationAtCompletion = new JSONObject();
        JSONObject ocurrencia = new JSONObject();


        try {

            for (int i = 0; i < 3; i++) {

                ocurrencia.put("content", "esta bueno el evento");
                ocurrencia.put("id", "1");
                ocurrencia.put("updated_at", "17 Sep 2014 18:05:24 GMT");
                ocurrencia.put("task_id", "1");
                ocurrencia.put("descripcion", "");
                ocurrencia.put("evidence_type", "la.opi.verificacionciudadana.Evidence");
                ocurrencia.put("evidence_id", "1");
                ocurrencia.put("valid_flag", "1");
                ocurrencia.put("edit_count", "1");

                verificaciones.put(ocurrencia);

            }


            locationAtCompletion.put("longitude", "-99.16582529");
            locationAtCompletion.put("provider", "gps");
            locationAtCompletion.put("latitude", "19.41554712");
            locationAtCompletion.put("accuracy", "16");
            locationAtCompletion.put("altitude", "2221.39990234375");

            actividad.put("location_at_completion", locationAtCompletion);
            actividad.put("completed_at", "17 Sep 2014 18:05:25 GMT");


            answer.put("verificaciones", verificaciones);
            answer.put("verificaciones_editadas", verificaciones_edit);
            answer.put("actividad", actividad);


            jsonArray.put(answer);


            System.out.println(jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
