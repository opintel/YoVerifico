package la.opi.verificacionciudadana.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.models.PictureEvidence;

/**
 * Created by Jhordan on 12/02/15.
 */
public class PictureEvidenceAdapter extends RecyclerView.Adapter<PictureEvidenceAdapter.ViewHolder> {


    private ArrayList<PictureEvidence> pictureEvidenceArrayList;
    private int itemLayout;

    public PictureEvidenceAdapter(ArrayList<PictureEvidence> pictureEvidenceArrayList, int itemLayout) {
        this.pictureEvidenceArrayList = pictureEvidenceArrayList;
        this.itemLayout = itemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        PictureEvidence pictureEvidence = pictureEvidenceArrayList.get(position);
        holder.photoEvidence.setImageBitmap(convertBitmap(pictureEvidence.getPathImageEvidence()));
        holder.photoName.setText(pictureEvidence.getPhotoName());

       holder.itemView.setTag(pictureEvidence);

    }


    @Override
    public int getItemCount() {
        return pictureEvidenceArrayList.size();
    }


    private Bitmap convertBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapEvidence = BitmapFactory.decodeFile(path, options);
        return bitmapEvidence;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoEvidence;
        public TextView photoName;

        public ViewHolder(View itemView) {
            super(itemView);


            photoEvidence = (ImageView) itemView.findViewById(R.id.imageView_photo_evidence);
            photoName = (TextView) itemView.findViewById(R.id.textView_photo_evidence);


        }


    }
}
