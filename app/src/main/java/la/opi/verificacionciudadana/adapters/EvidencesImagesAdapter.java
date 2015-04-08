package la.opi.verificacionciudadana.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.models.ImageEvidence;
import la.opi.verificacionciudadana.util.BitmapTransform;

/**
 * Created by Jhordan on 01/03/15.
 */
public class EvidencesImagesAdapter extends ArrayAdapter<ImageEvidence> {

    ArrayList<ImageEvidence> evidencesArrayList;
    LayoutInflater layoutInflater;

    public EvidencesImagesAdapter(Context context, ArrayList<ImageEvidence> evidencesArrayList) {

        super(context, -1, evidencesArrayList);
        this.evidencesArrayList = evidencesArrayList;
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final ImageEvidence evidence = getItem(position);
        int layout = R.layout.grid_item;

        if (convertView == null) {

            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imageEvidencePath = (ImageView) convertView.findViewById(R.id.image_evidence);
            holder.txtTitlePath = (TextView) convertView.findViewById(R.id.text_title);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitlePath.setText(evidence.getTitle());

       int MAX_WIDTH = 1024;
       int MAX_HEIGHT = 768;

        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));


       if(evidence.getEvidence().equals("vacio")){

            holder.imageEvidencePath.setImageResource(R.drawable.mex);

        }else {


           Picasso.with(getContext()).load("file://" + evidence.getEvidence())
                   .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                   .skipMemoryCache()
                   .resize(size, size)
                   .centerInside()
                   .into(holder.imageEvidencePath);
       }

       // holder.imageEvidencePath.setImageBitmap(avatarConvertBitmap(evidence.getEvidence()));


        return convertView;
    }

    private Bitmap avatarConvertBitmap(String path) {
        // BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmapAvatar = BitmapFactory.decodeFile(path, options);

        if (bitmapAvatar != null) {
            bitmapAvatar.recycle();
            bitmapAvatar = null;
        }

        return bitmapAvatar;
    }


    public static class ViewHolder {
        TextView txtTitlePath;
        ImageView imageEvidencePath;
    }
}
