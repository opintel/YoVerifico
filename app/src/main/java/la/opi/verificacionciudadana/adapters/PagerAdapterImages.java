package la.opi.verificacionciudadana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Jhordan on 15/01/15.
 */
public class PagerAdapterImages extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    public PagerAdapterImages(FragmentManager manager) {
        super(manager);
        fragments = new ArrayList<Fragment>();

    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
