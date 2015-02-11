package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/02/15.
 */
public class FragmentPhotoPictures extends FragmentModel implements View.OnClickListener {

    public FragmentPhotoPictures() {
    }

    public static FragmentPhotoPictures newInstance() {

        FragmentPhotoPictures fragmentPhotoPictures = new FragmentPhotoPictures();
        Bundle extraArguments = new Bundle();
        fragmentPhotoPictures.setArguments(extraArguments);
        return fragmentPhotoPictures;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures_photo, container, false);


        ((TextView) rootView.findViewById(R.id.camara)).setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        fragmentTransactionReplace(FragmentPhotoGallery.newInstance(), "otra");
    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }
}
