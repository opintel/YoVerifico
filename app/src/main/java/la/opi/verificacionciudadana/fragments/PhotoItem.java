package la.opi.verificacionciudadana.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.BitmapTransform;

/**
 * Created by Jhordan on 30/12/14.
 */
public class PhotoItem extends Fragment {

    public PhotoItem() {
    }

    public static String path;
    public static String titleEvidence;
    ImageView evidencePhoto;
    TextView evidencetitle;


    public static PhotoItem newInstance(String pathPhoto, String title) {
        path = pathPhoto;
        titleEvidence = title;
        PhotoItem fragmentDemoApp = new PhotoItem();
        Bundle extraArguments = new Bundle();
        fragmentDemoApp.setArguments(extraArguments);
        return fragmentDemoApp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_item, container, false);

        evidencePhoto = (ImageView) rootView.findViewById(R.id.image_evidence_item);
      //  evidencetitle = (TextView) rootView.findViewById(R.id.text_title_evidence_item);


      //  evidencetitle.setText(titleEvidence);

     // setFullImageFromFilePath(path, evidencePhoto);

       // evidencePhoto.setImageBitmap(bitmap(path));

        picassoPath(path,evidencePhoto);
        return rootView;
    }



    private void picassoPath(String path , ImageView evidencePhoto){
        int MAX_WIDTH = 1024;
        int MAX_HEIGHT = 768;

        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        Picasso.with(getActivity()).load("file://" + path)
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .skipMemoryCache()
                .resize(size, size)
                .centerInside()
                .into(evidencePhoto);
    }

    private Bitmap bitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapAvatar = BitmapFactory.decodeFile(path, options);
        return bitmapAvatar;
    }


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


}
