package la.opi.verificacionciudadana.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.interfaces.ItemListRecycleClickListener;
import la.opi.verificacionciudadana.models.Persona;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<Persona> personas;
    private int itemLayout;
    ItemListRecycleClickListener itemListRecycleClickListener;


    public RecycleAdapter(ArrayList<Persona> data, int itemLayout) {
        personas = data;
        this.itemLayout = itemLayout;
    }
    public void setItemListRecycleClickListener(ItemListRecycleClickListener itemListRecycleClickListener) {
        this.itemListRecycleClickListener = itemListRecycleClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView image;
        public TextView name;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListRecycleClickListener != null) {

                        Persona course = personas.get(getPosition());



                       itemListRecycleClickListener.itemRecycleClicked(getPosition(),course.getName());


                    }
                }
            });
            image = (TextView) itemView.findViewById(R.id.texto);
            name = (TextView) itemView.findViewById(R.id.calification_txt);
            description = (TextView) itemView.findViewById(R.id.comunity);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Persona course = personas.get(position);
        viewHolder.name.setText(course.getName());
        viewHolder.description.setText(course.getComunity());
        viewHolder.image.setText(course.getId());

        viewHolder.itemView.setTag(course);
    }


    @Override
    public int getItemCount() {
        return personas.size();
    }


}
