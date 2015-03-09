package la.opi.verificacionciudadana.fragments;

import android.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.EvidencesImagesAdapter;
import la.opi.verificacionciudadana.database.ActionsDataBase;
import la.opi.verificacionciudadana.models.ImageEvidence;

/**
 * Created by Jhordan on 07/02/15.
 */
public class FragmentGallery extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
    private static final int URL_LOADER = 0;
    EvidencesImagesAdapter evidencesImagesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        getActivity().getLoaderManager().initLoader(URL_LOADER, null, this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("gallery", "activity created");







    /*    ArrayList<ImageEvidence> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ImageEvidence imageEvidence = new ImageEvidence();
            imageEvidence.setTitle("evidencia " + Integer.toString(i));
            arrayList.add(imageEvidence);
        }

        EvidencesImagesAdapter evidencesImagesAdapter = new EvidencesImagesAdapter(getActivity(),arrayList);

        gridView.setAdapter(evidencesImagesAdapter);*/


    }

    @Override
    public void onResume() {
        super.onResume();

       System.out.println("SE EJECUTO ON RESUME");
        ActionsDataBase.queryDataBase(getActivity());
        ActionsDataBase.getTitleEvidence();
        ActionsDataBase.getPhotoEvidence();

        for (String ss : ActionsDataBase.getTitleEvidence()) {
            System.out.println("se ejecuto el for");
            System.out.println("database values: " + ss);
        }


        ArrayList<ImageEvidence> imageEvidences = new ArrayList<>();

        for (int i = 0; i < ActionsDataBase.getTitleEvidence().size() && i < ActionsDataBase.getPhotoEvidence().size(); i++) {


            ImageEvidence imageEvidence = new ImageEvidence();

            String[] title = new String[ActionsDataBase.getTitleEvidence().size()];
            title = ActionsDataBase.getTitleEvidence().toArray(title);
            String[] photo = new String[ActionsDataBase.getPhotoEvidence().size()];
            photo = ActionsDataBase.getPhotoEvidence().toArray(photo);
            imageEvidence.setTitle(title[i]);
            imageEvidence.setEvidence(photo[i]);

            imageEvidences.add(imageEvidence);
        }

        EvidencesImagesAdapter evidencesImagesAdapter = new EvidencesImagesAdapter(getActivity(), imageEvidences);
        gridView.setAdapter(evidencesImagesAdapter);


    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //new CursorLoader(getActivity(),,null,null,null,null);
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }


}



