package la.opi.verificacionciudadana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhordan on 07/02/15.
 */
public class PagerAdapterDemo extends FragmentPagerAdapter {

    private List<Fragment> fragmentListDemo;

    public PagerAdapterDemo(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentListDemo = new ArrayList<>();

    }

    @Override
    public Fragment getItem(int argument) {
        return fragmentListDemo.get(argument);
    }

    @Override
    public int getCount() {
        return fragmentListDemo.size();
    }

    public void addFragment(Fragment fragment){
        fragmentListDemo.add(fragment);
    }
}
