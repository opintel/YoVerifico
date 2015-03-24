package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.EndPoint;

/**
 * Created by Jhordan on 07/02/15.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {

        AboutFragment aboutFragment = new AboutFragment();
        Bundle extraArguments = new Bundle();
        aboutFragment.setArguments(extraArguments);
        return aboutFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);


        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Acerca de YoVerifico");

        TextView txtOpi = (TextView) rootView.findViewById(R.id.txtOpi);
        txtOpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(EndPoint.OPI_WEB)));

            }
        });

        return rootView;
    }


}
