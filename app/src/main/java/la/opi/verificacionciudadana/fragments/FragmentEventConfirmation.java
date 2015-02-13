package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/02/15.
 */
public class FragmentEventConfirmation extends Fragment implements View.OnClickListener {

    public FragmentEventConfirmation() {
    }

    public static FragmentEventConfirmation newInstance() {

        FragmentEventConfirmation fragmentEventConfirmation = new FragmentEventConfirmation();
        Bundle extraArguments = new Bundle();
        fragmentEventConfirmation.setArguments(extraArguments);
        return fragmentEventConfirmation;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_confirmation, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Reporte Final");


        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onClick(View v) {


    }


}
