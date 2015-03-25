package la.opi.verificacionciudadana.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.EvidencesImagesAdapter;
import la.opi.verificacionciudadana.database.ActionsDataBase;
import la.opi.verificacionciudadana.models.ImageEvidence;
import la.opi.verificacionciudadana.util.CameraSettings;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;

/**
 * Created by Jhordan on 10/02/15.
 */
public class EvidenceOneFragment extends FragmentModel implements View.OnClickListener {

    public EvidenceOneFragment() {
    }

    public static EvidenceOneFragment newInstance() {

        EvidenceOneFragment fragmentPictureEvidences = new EvidenceOneFragment();
        Bundle extraArguments = new Bundle();
        fragmentPictureEvidences.setArguments(extraArguments);
        return fragmentPictureEvidences;
    }

    // ImageView img;
    final static int REQUEST_IMAGE_CAPTURE = 0;
    String mCurrentPhotoPath;
    View rootView;
    String imageFileName;
    String datePicture;
    GridView gridView;
    FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_informacion, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.evidence_one_fragment));
        ((FloatingActionButton) rootView.findViewById(R.id.btn_camera)).setOnClickListener(this);
        ((FloatingActionButton) rootView.findViewById(R.id.btn_continue)).setOnClickListener(this);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.container_informacion);
        frameLayout.setBackgroundColor(getResources().getColor(R.color.recycle_events_background));




        return rootView;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_camera:
                initializedCamera();
                break;
            case R.id.btn_continue:
                fragmentTransactionReplace(EvidenceTwoFragment.newInstance(), getResources().getString(R.string.evidence_two_fragment));
                break;

        }


    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("SE EJECUTO ON RESUME");
        ActionsDataBase.queryDataBase(getActivity());
        ActionsDataBase.getTitleEvidence();
        ActionsDataBase.getPhotoEvidence();
       ConfigurationPreferences.setPhotosSizePreference(getActivity(),Integer.toString(ActionsDataBase.getTitleEvidence().size()));

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("CAMERA", "ACTIVITY RESULT");
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                //   Bundle b = data.getExtras();
                // Bitmap bit = (Bitmap) b.get("data");
                //  img.setImageBitmap(bit);

                //  img.setImageBitmap(yo(mCurrentPhotoPath));

                // setFullImageFromFilePath(mCurrentPhotoPath, img);

               /* img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StorageFiles.deleteFilesFromDirectory();
                    }
                });*/
                Log.i("CAMERA", "OK");
                ActionsDataBase.insertDataBase(getActivity(), imageFileName, mCurrentPhotoPath);


                /// GUARDO LOS ARCHIVOS HASTA QUE ES OK !
                Toast.makeText(getActivity(), "Evidencia ha sido guardada", Toast.LENGTH_SHORT).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {

                Log.i("CAMERA", "CANCEL");
            } else {
                // Image capture failed, advise user
                Log.i("CAMERA", "EXPECTED");
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }

    private void initializedCamera() {
        if (CameraSettings.checkCameraExist(getActivity())) {
            takePictures();

        }
    }

    private void takePictures() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    }

    private File createImageFile() throws IOException {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeData = new SimpleDateFormat(Config.DATA_FORMAT_PICTURE);
        datePicture = timeData.format(c.getTime());
        imageFileName = Config.IMAGE_NAME_DEFOULT + datePicture;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + Config.DIRECTORY_EVIDENCE);
        File image = File.createTempFile(imageFileName, Config.PNG_EXTENSION, storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();


        return image;
    }

}


