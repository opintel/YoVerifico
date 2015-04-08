package la.opi.verificacionciudadana.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.MapaActivity;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;


public class OcurrencesAdapter extends RecyclerView.Adapter<OcurrencesAdapter.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private ArrayList<Ocurrence> ocurrenceArrayList;
    private int itemLayout;
    ItemListRecycleClickListener itemListRecycleClickListener;
    private Context context;

    public OcurrencesAdapter(ArrayList<Ocurrence> ocurrenceArrayList, int itemLayout, Context context) {
        this.ocurrenceArrayList = ocurrenceArrayList;
        this.itemLayout = itemLayout;
        this.context = context;
    }


    public void setItemListRecycleClickListener(ItemListRecycleClickListener itemListRecycleClickListener) {
        this.itemListRecycleClickListener = itemListRecycleClickListener;
    }


    @Override
    public OcurrencesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        ViewHolder viewHolderItem, viewHolderHeader;

        if (viewType == ITEM) {


            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_ocurrences, viewGroup, false);
            viewHolderItem = new ViewHolder(root, viewType);
            return viewHolderItem;


        } else if (viewType == HEADER) {

            View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_list_ocurrences, viewGroup, false);
            viewHolderHeader = new ViewHolder(root, viewType);
            return viewHolderHeader;


        }

        return null;
    }


    @Override
    public void onBindViewHolder(OcurrencesAdapter.ViewHolder viewHolder, int position) {


        final Ocurrence ocurrence = ocurrenceArrayList.get(position);

        if (viewHolder.typeHolder == ITEM) {

            viewHolder.txtTitleOcurrence.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(context));
            viewHolder.txtDescriptionOcurrence.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(context));
            viewHolder.txtColoniaOcurrence.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(context));
            viewHolder.txtTitleOcurrence.setText(ocurrence.getAction());
            viewHolder.txtDescriptionOcurrence.setText(ocurrence.getDescription());
            viewHolder.txtColoniaOcurrence.setText(ocurrence.getCalle() + ", " + ocurrence.getColonia() + " " + ocurrence.getAdmin2());


        } else if (viewHolder.typeHolder == HEADER) {


            System.out.println(" OCURRENCES" + ocurrenceArrayList.size());
          viewHolder.explorer.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if (ocurrenceArrayList.size() == 1) {

                      Toast.makeText(context,"No hay ocurrencias en este Municipio y Estado",Toast.LENGTH_SHORT).show();

                  }else {
                      Activity activity = (Activity) context;
                      Intent intent = new Intent(context, MapaActivity.class);
                      context.startActivity(intent);
                      activity.overridePendingTransition(R.animator.open_next, R.animator.close_main);
                  }


              }
          });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER;


        return ITEM;

    }

    private boolean isPositionHeader(int position) {
        return position == HEADER;
    }

    @Override
    public int getItemCount() {
        return ocurrenceArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        int typeHolder;
        public TextView txtTitleOcurrence, txtDescriptionOcurrence, txtColoniaOcurrence;
        public Button explorer;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListRecycleClickListener != null) {


                        itemListRecycleClickListener.itemRecycleClicked(getPosition(), bundleOcurrences((Ocurrence) ocurrenceArrayList.get(getPosition())));


                    }
                }
            });

            if (viewType == ITEM) {

                txtTitleOcurrence = (TextView) itemView.findViewById(R.id.txt_title);
                txtDescriptionOcurrence = (TextView) itemView.findViewById(R.id.txt_description);
                txtColoniaOcurrence = (TextView) itemView.findViewById(R.id.txt_colonia);

                typeHolder = ITEM;
            } else if (viewType == HEADER) {

                explorer = (Button) itemView.findViewById(R.id.btn_explorer);
                typeHolder = HEADER;

            }


        }


    }


    private Bundle bundleOcurrences(Ocurrence ocurrence) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.TITLE_OCURRENCE, ocurrence.getAction());
        bundle.putString(Config.DATA_OCURRENCE, ocurrence.getOcurrenceData());
        bundle.putString(Config.TIME_OCURRENCE, ocurrence.getToHour() + " - " + ocurrence.getFromHour());
        bundle.putString(Config.DESCRIPTION_OCURRENCE, ocurrence.getDescription() + "\n \n" + ocurrence.getStrategyDescription());
        bundle.putString(Config.PLACE_OCURRENCE, ocurrence.getCalle() + " ," + ocurrence.getColonia() + " " + ocurrence.getAdmin2());
        bundle.putString(Config.CONTACT_OCURRENCE, ocurrence.getContactInfo());
        bundle.putString(Config.ID_OCURRENCE, ocurrence.getId());
        bundle.putString(Config.STATE_OCURRRENCE,ocurrence.getAdmin1());
        bundle.putString(Config.TWON_OCURRRENCE,ocurrence.getAdmin2());
        bundle.putString(Config.LATITUDE_OCURRENCE,ocurrence.getLatitude());
        bundle.putString(Config.LONGITUDE_OCURRENCE,ocurrence.getLongitude());
        return bundle;

    }


}
