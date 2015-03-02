package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance


    }

    TextView txtOpi;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        txtOpi = (TextView) rootView.findViewById(R.id.txt_opi);
        txtOpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(EndPoint.OPI_WEB)));

            }
        });

        return rootView;
    }


}
