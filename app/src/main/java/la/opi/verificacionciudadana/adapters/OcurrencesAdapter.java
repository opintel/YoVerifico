package la.opi.verificacionciudadana.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.DetailActivity;
import la.opi.verificacionciudadana.activities.MapaActivity;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.models.Ocurrence;
import la.opi.verificacionciudadana.models.Persona;


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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Ocurrence course = ocurrenceArrayList.get(position);

        if (viewHolder.typeHolder == ITEM) {

            viewHolder.name.setText(course.getComments());
            viewHolder.description.setText(course.getDescription());
            viewHolder.image.setText(course.getId());

        } else if (viewHolder.typeHolder == HEADER) {


            viewHolder.explorer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(new Intent(context, MapaActivity.class));
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
        public TextView image;
        public TextView name;
        public TextView description;
        public Button explorer;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListRecycleClickListener != null) {

                        Ocurrence course = ocurrenceArrayList.get(getPosition());


                        itemListRecycleClickListener.itemRecycleClicked(getPosition(), course.getComments());


                    }
                }
            });

            if (viewType == ITEM) {
                image = (TextView) itemView.findViewById(R.id.texto);
                name = (TextView) itemView.findViewById(R.id.calification_txt);
                description = (TextView) itemView.findViewById(R.id.comunity);

                typeHolder = ITEM;
            } else if (viewType == HEADER) {

                explorer = (Button) itemView.findViewById(R.id.btn_explorer);
                typeHolder = HEADER;

            }


        }


    }


}
