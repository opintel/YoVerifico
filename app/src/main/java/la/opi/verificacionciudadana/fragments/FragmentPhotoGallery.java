package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.opi.verificacionciudadana.R;

/**
 * Created by Jhordan on 10/02/15.
 */
public class FragmentPhotoGallery extends FragmentModel implements View.OnClickListener {

    public FragmentPhotoGallery() {
    }

    public static FragmentPhotoGallery newInstance() {

        FragmentPhotoGallery fragmentPhotoGallery = new FragmentPhotoGallery();
        Bundle extraArguments = new Bundle();
        fragmentPhotoGallery.setArguments(extraArguments);
        return fragmentPhotoGallery;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    TextView bt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures_gallery, container, false);

        bt = (TextView) rootView.findViewById(R.id.buto);

        bt.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        fragmentTransactionReplace(FragmentEventCalification.newInstance(), "calificaion");

    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }
}
