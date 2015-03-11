package la.opi.verificacionciudadana.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
import la.opi.verificacionciudadana.R;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private static int[] ICONS = new int[] {

            R.drawable.ic_events_custom,
            R.drawable.ic_perfil_custom

    };

    private List<Fragment> fragments;

    public HomePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    public int getDrawableId(int position) {
        return ICONS[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}