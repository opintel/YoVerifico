package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;

/**
 * Created by Jhordan on 10/02/15.
 */
public class FragmentEventCalification extends FragmentModel implements View.OnClickListener {

    public FragmentEventCalification() {
    }

    public static FragmentEventCalification newInstance() {

        FragmentEventCalification fragmentEventCalification = new FragmentEventCalification();
        Bundle extraArguments = new Bundle();
        fragmentEventCalification.setArguments(extraArguments);
        return fragmentEventCalification;
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
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Calificar Evento");


        txtEvent = (TextView) rootView.findViewById(R.id.calification_txt);
        txtEvent.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
        btnContinue = (FloatingActionButton) rootView.findViewById(R.id.btn_continue_calification);
        ratingBarEvent = (RatingBar) rootView.findViewById(R.id.ratingBar);



        btnContinue.setOnClickListener(this);


        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onClick(View v) {

        float rating=ratingBarEvent.getRating();
        Toast.makeText(getActivity(),"Ratings: " + String.valueOf(rating),Toast.LENGTH_SHORT).show();
        fragmentTransactionReplace(FragmentEventObservations.newInstance(), "observations");
    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }

}
