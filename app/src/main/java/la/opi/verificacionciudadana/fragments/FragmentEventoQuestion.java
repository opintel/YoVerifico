package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/02/15.
 */
public class FragmentEventoQuestion extends FragmentModel implements View.OnClickListener {

    public FragmentEventoQuestion() {
    }

    public static FragmentEventoQuestion newInstance() {

        FragmentEventoQuestion fragmentEventoQuestion = new FragmentEventoQuestion();
        Bundle extraArguments = new Bundle();
        fragmentEventoQuestion.setArguments(extraArguments);
        return fragmentEventoQuestion;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private Button btnAcepted, btnCancel;
    private int checkBoxValue;
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_question, container, false);

        btnAcepted = (Button) rootView.findViewById(R.id.btn_accepted);
        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnAcepted.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_accepted:


                fragmentTransactionReplace(FragmentPhotoPictures.newInstance(), "photes");


                break;
            case R.id.btn_cancel:

                Toast.makeText(getActivity(), "llegale moreno entonces no!", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }

}
