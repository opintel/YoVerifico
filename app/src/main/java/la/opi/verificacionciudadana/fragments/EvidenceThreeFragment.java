package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;

/**
 * Created by Jhordan on 10/02/15.
 */
public class EvidenceThreeFragment extends FragmentModel implements View.OnClickListener {

    public EvidenceThreeFragment() {
    }
    public static EvidenceThreeFragment newInstance() {

        EvidenceThreeFragment fragmentEventObservations = new EvidenceThreeFragment();
        Bundle extraArguments = new Bundle();
        fragmentEventObservations.setArguments(extraArguments);
        return fragmentEventObservations;
    }
    private TextView txtContinue;
    private EditText editTxtObservations;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_observation, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.evidence_three_fragment));
        txtContinue = (TextView) rootView.findViewById(R.id.continue_observations);
        editTxtObservations = (EditText) rootView.findViewById(R.id.edit_txt_observations);
        txtContinue.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {


        if(!editTxtObservations.getText().toString().equals("")){
            ConfigurationPreferences.setObservationPreference(getActivity(), editTxtObservations.getText().toString());
            fragmentTransactionReplace(EvidenceFourFragment.newInstance(), getResources().getString(R.string.evidence_four_fragment));
        }else{
            Toast.makeText(getActivity(), "Es necesario agregar un comentario por favor.", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }

}
