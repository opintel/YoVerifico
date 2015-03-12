package la.opi.verificacionciudadana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.models.State;
import la.opi.verificacionciudadana.models.Town;


/**
 * Created by Jhordan on 25/01/15.
 */
public class SpinnerCustomTownAdapter extends ArrayAdapter<Town> {

    ArrayList<Town> stateArrayList;
    LayoutInflater layoutInflater;

    public SpinnerCustomTownAdapter(Context context, ArrayList<Town> stateArrayList) {

        super(context, -1, stateArrayList);
        this.stateArrayList = stateArrayList;
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final Town town = getItem(position);


        int layout = R.layout.item_spinner_mexico;

        if (convertView == null) {

            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtState = (TextView) convertView.findViewById(R.id.item_spinner);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtState.setText(town.getName());


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public static class ViewHolder {
        TextView txtState;

    }
}
