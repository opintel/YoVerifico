package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;


/**
 * Created by Jhordan on 30/12/14.
 */
public class FragmentPagerDemoItem extends Fragment {
    public FragmentPagerDemoItem() {
    }

    public static String IMG_URL = "img_url";
    public static String ARRAY = "listImages";
    public static String TITLE = "title";
    public static String SUBTITLE = "subtitle";


    public static FragmentPagerDemoItem newInstance(int title, int subtitle) {
        FragmentPagerDemoItem fragmentDemoApp = new FragmentPagerDemoItem();
        Bundle extraArguments = new Bundle();
        extraArguments.putInt(TITLE, title);
        extraArguments.putInt(SUBTITLE, subtitle);
        fragmentDemoApp.setArguments(extraArguments);
        return fragmentDemoApp;
    }

    TextView txtTitle, txtSubTitle;
    int argumentTitle, argumentSubTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_item, container, false);

        txtTitle = (TextView) rootView.findViewById(R.id.text_view_title);
        txtSubTitle = (TextView) rootView.findViewById(R.id.text_view_sub_title);
        argumentTitle = getArguments().getInt(TITLE);
        argumentSubTitle = getArguments().getInt(SUBTITLE);

        txtTitle.setText(getResources().getString(argumentTitle));
        txtSubTitle.setText(getResources().getString(argumentSubTitle));
        txtTitle.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(getActivity()));
        txtSubTitle.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(getActivity()));


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
