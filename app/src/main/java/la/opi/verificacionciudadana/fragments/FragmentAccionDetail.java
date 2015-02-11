package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.EvidenceActivity;

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
        View rootView = inflater.inflate(R.layout.fragment_accion_detail, container, false);

        btnEvidence = (Button) rootView.findViewById(R.id.btn_evidence);


        btnEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "dssfgdfh", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EvidenceActivity.class);
                intent.putExtra("dato", "hola");
                startActivity(intent);

            }
        });
        return rootView;
    }


}
