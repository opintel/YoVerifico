package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.DetailActivity;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;


/**
 * Created by Jhordan on 30/12/14.
 */
public class FragmentInfoWindowMap extends Fragment {
    public FragmentInfoWindowMap() {
    }

    public static String TITLE = "title";

    public static FragmentInfoWindowMap newInstance(String title) {
        FragmentInfoWindowMap fragmentInfoWindowMap = new FragmentInfoWindowMap();
        Bundle extraArguments = new Bundle();
        extraArguments.putString(TITLE, title);
        fragmentInfoWindowMap.setArguments(extraArguments);
        return fragmentInfoWindowMap;
    }

    TextView txtTitle;

    String argumentTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_window, container, false);


        txtTitle = (TextView) rootView.findViewById(R.id.text_view_title);
        argumentTitle = getArguments().getString(TITLE);
        txtTitle.setText(argumentTitle);

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "detail", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), DetailActivity.class));
            }
        });


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
