package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;

/**
 * Created by Jhordan on 10/02/15.
 */
public class EvidenceTwoFragment extends FragmentModel implements View.OnClickListener {

    public EvidenceTwoFragment() {
    }

    public static EvidenceTwoFragment newInstance() {

        EvidenceTwoFragment evidenceTwoFragment = new EvidenceTwoFragment();
        Bundle extraArguments = new Bundle();
        evidenceTwoFragment.setArguments(extraArguments);
        return evidenceTwoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private FloatingActionButton btnContinue;
    private TextView txtEvent;
    private RatingBar ratingBarEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_calification, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.evidence_two_fragment));

        txtEvent = (TextView) rootView.findViewById(R.id.calification_txt);
        txtEvent.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
        btnContinue = (FloatingActionButton) rootView.findViewById(R.id.btn_continue_calification);
        ratingBarEvent = (RatingBar) rootView.findViewById(R.id.ratingBar);
        btnContinue.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        ConfigurationPreferences.setRatingPreference(getActivity(), String.valueOf(Math.round(ratingBarEvent.getRating())));
        fragmentTransactionReplace(EvidenceThreeFragment.newInstance(), getResources().getString(R.string.evidence_three_fragment));
    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }


}
