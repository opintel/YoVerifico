package la.opi.verificacionciudadana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.models.ItemNavigationDrawer;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;

/**
 * Created by Jhordan on 07/02/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    private List<ItemNavigationDrawer> itemNavigationDrawerList;
    private LayoutInflater layoutInflater;
    private Context context;

    public NavigationDrawerAdapter(List<ItemNavigationDrawer> itemNavigationDrawerList, Context context) {
        this.itemNavigationDrawerList = itemNavigationDrawerList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getCount() {
        return itemNavigationDrawerList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemNavigationDrawerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemNavigationDrawerList.indexOf(itemNavigationDrawerList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = layoutInflater.inflate(R.layout.item_navigation_drawer, parent, false);
        ImageView itemIcon = (ImageView) rootView.findViewById(R.id.imageViewContentIcon);
        TextView itemTitle = (TextView) rootView.findViewById(R.id.textViewContentTitle);
        itemIcon.setImageResource(itemNavigationDrawerList.get(position).getIdIcon());
        itemTitle.setText(itemNavigationDrawerList.get(position).getIdTitle());
       itemTitle.setTypeface(VerificaCiudadFonts.typefaceRobotoMedium(context));

        return rootView;
    }


}
