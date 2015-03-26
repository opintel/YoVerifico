package la.opi.verificacionciudadana.fragments;

/**
 * Created by Jhordan on 09/03/15.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tutorial_menu_two));
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
            progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_help), true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            progressDialog.dismiss();


        }


    }


}