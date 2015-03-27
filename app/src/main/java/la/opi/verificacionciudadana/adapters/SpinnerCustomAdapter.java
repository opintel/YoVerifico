package la.opi.verificacionciudadana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.models.State;


/**
 * Created by Jhordan on 25/01/15.
 */
public class SpinnerCustomAdapter extends ArrayAdapter<State> {

    ArrayList<State> stateArrayList;
    LayoutInflater layoutInflater;

    public SpinnerCustomAdapter(Context context, ArrayList<State> stateArrayList) {

        super(context, -1, stateArrayList);
        this.stateArrayList = stateArrayList;
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final State states = getItem(position);
        int layout = R.layout.item_spinner_mexico;

        if (convertView == null) {

            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtState = (TextView) convertView.findViewById(R.id.item_spinner);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtState.setText(states.getName());


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public static class ViewHolder {
        TextView txtState;

    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
