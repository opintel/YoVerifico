package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.TownActivity;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.parser.PerfilParser;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import retrofit.RestAdapter;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 07/02/15.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {

    public LocationFragment() {
    }

    public static LocationFragment newInstance() {

        LocationFragment locationFragment = new LocationFragment();
        Bundle extraArguments = new Bundle();
        locationFragment.setArguments(extraArguments);
        return locationFragment;
    }
    Button btnLocalidad;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        btnLocalidad = (Button) rootView.findViewById(R.id.btn_localidad);
        btnLocalidad.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(getActivity(), TownActivity.class));

    }
}
