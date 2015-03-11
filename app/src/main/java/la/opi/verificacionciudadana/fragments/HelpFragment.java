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


public class HelpFragment extends Fragment {

    public HelpFragment() {
    }

    public static HelpFragment newInstance() {

        HelpFragment municipalFragment = new HelpFragment();
        Bundle extraArguments = new Bundle();
        municipalFragment.setArguments(extraArguments);
        return municipalFragment;
    }


    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Ayuda");

        webView = (WebView) rootView.findViewById(R.id.webView);


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(getString(R.string.url_nosmuevelapaz));

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}