package la.opi.verificacionciudadana.fragments;

import android.annotation.TargetApi;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.viewpagerindicator.CirclePageIndicator;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.PagerAdapterDemo;

/**
 * Created by Jhordan on 08/03/15.
 */

public class TutorialFragment extends Fragment {

    public TutorialFragment() {
    }
    public static TutorialFragment newInstance() {

        TutorialFragment tutorialFragment = new TutorialFragment();
        Bundle extraArguments = new Bundle();
        tutorialFragment.setArguments(extraArguments);
        return tutorialFragment;
    }


    RelativeLayout relativeLayout;
    int vieneDelUltimo = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_activity, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.activity_login_screen_name);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        PagerAdapterDemo pagerAdapterImages;
        pagerAdapterImages = new PagerAdapterDemo(getFragmentManager());
        pagerAdapterImages.addFragment(FragmentTutorialItem.newInstance(R.string.text_demo_1, R.string.txt_demo_1_description));
        pagerAdapterImages.addFragment(FragmentTutorialItem.newInstance(R.string.text_demo_2, R.string.txt_demo_2_description));
        pagerAdapterImages.addFragment(FragmentTutorialItem.newInstance(R.string.text_demo_3, R.string.txt_demo_3_description));
        pagerAdapterImages.addFragment(FragmentTutorialItem.newInstance(R.string.text_demo_4, R.string.txt_demo_4_description));

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.background_pager_layout);

        pager.setAdapter(pagerAdapterImages);


        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);

        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {


                switch (position) {

                    case 0:


                        relativeLayout.setBackgroundResource(R.drawable.tres);
                        TransitionDrawable transitions = (TransitionDrawable) relativeLayout.getBackground();
                        transitions.startTransition(500);

                        break;
                    case 1:

                        if (vieneDelUltimo == 3) {
                            relativeLayout.setBackgroundResource(R.drawable.cua);
                            TransitionDrawable transi = (TransitionDrawable) relativeLayout.getBackground();
                            transi.startTransition(500);
                            vieneDelUltimo = 0;

                        } else {
                            relativeLayout.setBackgroundResource(R.drawable.dos);
                            TransitionDrawable transitionTwo = (TransitionDrawable) relativeLayout.getBackground();
                            transitionTwo.startTransition(500);

                        }


                        break;
                    case 2:
                        relativeLayout.setBackgroundResource(R.drawable.uno);
                        TransitionDrawable transitionThree = (TransitionDrawable) relativeLayout.getBackground();
                        transitionThree.startTransition(500);
                        vieneDelUltimo = 3;
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        circlePageIndicator.setViewPager(pager);


        return rootView;
    }

}

