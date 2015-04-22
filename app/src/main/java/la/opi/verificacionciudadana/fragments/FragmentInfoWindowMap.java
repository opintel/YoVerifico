package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.DetailActivity;
import la.opi.verificacionciudadana.util.Config;


/**
 * Created by Jhordan on 30/12/14.
 */
public class FragmentInfoWindowMap extends Fragment {
    public FragmentInfoWindowMap() {
    }

    public static String TITLE = "title";
    public static String BUNDLE = "bundle";

    public static FragmentInfoWindowMap newInstance(String title, Bundle bundle) {
        FragmentInfoWindowMap fragmentInfoWindowMap = new FragmentInfoWindowMap();
        Bundle extraArguments = new Bundle();
        extraArguments.putString(TITLE, title);
        extraArguments.putBundle(BUNDLE, bundle);
        fragmentInfoWindowMap.setArguments(extraArguments);
        return fragmentInfoWindowMap;
    }

    TextView txtTitle ,txtMore;
    String argumentTitle;
    Bundle bundle;
    String activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_window, container, false);


        if (getActivity().getIntent().getStringExtra(Config.KEY_INTENT) != null) {
            activity = getActivity().getIntent().getStringExtra(Config.KEY_INTENT);
            System.out.println(activity);
        } else {
            activity = "null";
            System.out.println(activity);
        }


        bundle = getArguments().getBundle(BUNDLE);
        txtTitle = (TextView) rootView.findViewById(R.id.txt_title_detail);
        txtMore = (TextView)rootView.findViewById(R.id.txt_more);
        argumentTitle = getArguments().getString(TITLE);
        txtTitle.setText(argumentTitle);

       txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (activity.equals(Config.DETAIL_FRAGMENT)) {
                    getActivity().onBackPressed();

                } else {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(Config.BUNDLE_OCURRENCE, bundle);
                    intent.putExtra(Config.KEY_INTENT, Config.WINDOINFO_FRAGMENT);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);
                }


            }
        });


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
