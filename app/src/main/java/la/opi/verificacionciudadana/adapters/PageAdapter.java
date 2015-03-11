package la.opi.verificacionciudadana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Jhordan on 20/10/14.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;


    public PageAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragments) {

        super(fm);
        // TODO Auto-generated constructor stub
        this.fragments = fragments;


    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "TODOS";
        } else if (position == 1) {
            return "FAVORITOS";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
