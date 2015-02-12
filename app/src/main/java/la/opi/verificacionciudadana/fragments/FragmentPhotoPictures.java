package la.opi.verificacionciudadana.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;

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


    ImageView img;
    final static int REQUEST_IMAGE_CAPTURE = 0;

    String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pictures_photo, container, false);


        ((TextView) rootView.findViewById(R.id.camara)).setOnClickListener(this);
        img = (ImageView) rootView.findViewById(R.id.imageCaptured);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        initializedCamera();
        // fragmentTransactionReplace(FragmentPhotoGallery.newInstance(), "otra");
    }


    private void initializedCamera() {

        if (checkCameraExist()) {

            takePictures();

        }


    }


    private Boolean checkCameraExist() {

        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.i("CAMERA", "ESTE DISPOSITIVO NO TIENE CAMERA");
            return false;
        } else {

            Log.i("CAMERA", "ESTE DISPOSITIVO TIENE CAMERA");
            return true;
        }
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

                setFullImageFromFilePath(mCurrentPhotoPath, img);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StorageFiles.deleteFilesFromDirectory();
                    }
                });
                Log.i("CAMERA", "OK");
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
                Log.i("CAMERA", "CANCEL");
            } else {
                // Image capture failed, advise user
                Log.i("CAMERA", "EXPECTED");
            }
        }


    }


    private void takePictures() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {


            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    // Create an image file name
    private File createImageFile() throws IOException {

        String timeData = new SimpleDateFormat(VerificaCiudadConstants.DATA_FORMAT).format(new Date());
        //LUEGO VEO QUE NOMBRE PONER
        String imageFileName = VerificaCiudadConstants.IMAGE_NAME_DEFOULT + timeData;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + VerificaCiudadConstants.DIRECTORY_EVIDENCE);
        File image = File.createTempFile(imageFileName, VerificaCiudadConstants.JPG_EXTENSION, storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }


    private Bitmap yo(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapAvatar = BitmapFactory.decodeFile(path, options);

        return bitmapAvatar;

    }

    /**
     * Scale the photo down and fit it to our image views.
     * <p/>
     * "Drastically increases performance" to set images using this technique.
     * Read more:http://developer.android.com/training/camera/photobasics.html
     */
    private void setFullImageFromFilePath(String imagePath, ImageView imageView) {


        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        imageView.setImageBitmap(bitmap);
    }

    /**
     * Add the picture to the photo gallery.
     * Must be called on all camera images or they will
     * disappear once taken.
     */


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void fragmentTransactionReplace(Fragment fragmentInstance, String fragmentName) {
        super.fragmentTransactionReplace(fragmentInstance, fragmentName);
    }
}


