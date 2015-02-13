package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.dialogs.QuestionDialog;

/**
 * Created by Jhordan on 07/02/15.
 */
public class FragmentGallery extends Fragment {

    public FragmentGallery() {
    }

    public static FragmentGallery newInstance() {

        FragmentGallery fragmentGallery = new FragmentGallery();
        Bundle extraArguments = new Bundle();
        fragmentGallery.setArguments(extraArguments);
        return fragmentGallery;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("gallery", "se creo");
    }

    GridView gridView;
    private ArrayAdapter<String> mForestAdapter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      rootView = inflater.inflate(R.layout.fragment_gallery, container, false);




            gridView = (GridView) rootView.findViewById(R.id.gridview_gallery);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("gallery", "activity created");


        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add("hola");
        }


        List<String> weekForecast = new ArrayList<String>(arrayList);
        mForestAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.item_gallery_fragment,
                R.id.textView_photo_evidence,
                weekForecast);

        gridView.setAdapter(mForestAdapter);
    }


}
