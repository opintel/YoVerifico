package la.opi.verificacionciudadana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;

import la.opi.verificacionciudadana.R;

public class MyPagerAdapter extends FragmentPagerAdapter
        implements PagerSlidingTabStrip.IconTabProvider {

    private List<Fragment> fragments;

    private final int[] ICONS = {
            R.drawable.icon_selector,
            R.drawable.icon_selector

    };

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override public int getPageIconResId(int i) {
        return ICONS[i];
    }
}