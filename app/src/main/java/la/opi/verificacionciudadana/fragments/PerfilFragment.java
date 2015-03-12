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

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.HttpHelper;
import la.opi.verificacionciudadana.parser.PerfilParser;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;
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

        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.primary, R.color.accent);
        swipeRefreshLayout.setRefreshing(false);


        txtName = (TextView) rootView.findViewById(R.id.name_profile);
        txtEmail = (TextView) rootView.findViewById(R.id.mail_profile);
        txtTwon = (TextView) rootView.findViewById(R.id.twon_profile);
        txtState = (TextView) rootView.findViewById(R.id.state_profile);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profileRequest();
    }

    public void profileRequest() {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getProfile().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, VerificaCiudadConstants.UTF_8);


                    try {

                        System.out.println("PERFIL RESPONSE: " + writer.toString());


                        PerfilParser.perfil(writer.toString());



                        txtName.setText(PerfilParser.getProfileName());
                        txtEmail.setText(PerfilParser.getProfileMail());
                        txtState.setText(PerfilParser.getProfileStateName());
                        txtTwon.setText(PerfilParser.getProfileTownName());


                    } catch (Exception e) {
                        Log.e(VerificaCiudadConstants.ERROR_REGULAR_EXPRESION, e.toString());
                    }

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

}
