package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import la.opi.verificacionciudadana.R;


public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {

        DetailFragment detailFragment = new DetailFragment();
        Bundle extraArguments = new Bundle();
        detailFragment.setArguments(extraArguments);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Detalle de Ocurrencia");


        return rootView;
    }


}