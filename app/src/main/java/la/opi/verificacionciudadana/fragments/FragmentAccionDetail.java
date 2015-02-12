package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.EvidenceActivity;
import la.opi.verificacionciudadana.dialogs.QuestionDialog;

/**
 * Created by Jhordan on 07/02/15.
 */
public class FragmentAccionDetail extends Fragment {

    public FragmentAccionDetail() {
    }

    public static FragmentAccionDetail newInstance() {

        FragmentAccionDetail fragmentAccionDetail = new FragmentAccionDetail();
        Bundle extraArguments = new Bundle();
        fragmentAccionDetail.setArguments(extraArguments);
        return fragmentAccionDetail;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    Button btnEvidence;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View    rootView = inflater.inflate(R.layout.fragment_accion_detail, container, false);

        btnEvidence = (Button) rootView.findViewById(R.id.btn_evidence);


        btnEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo();
            }
        });
        return rootView;
    }



    private void dialogo() {

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();

        QuestionDialog dialogo = new QuestionDialog();
        dialogo.show(fragmentManager, "Cerrar Sesión");
    }


}
