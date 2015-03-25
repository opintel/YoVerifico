package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.EvidenceOneFragment;
import la.opi.verificacionciudadana.interfaces.ActivityAnimate;


public class EvidenceActivity extends BaseActivity implements ActivityAnimate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_evidence, new EvidenceOneFragment())
                    .commit();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_evidence;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                FragmentManager fragmentManager = getSupportFragmentManager();

                System.out.println(fragmentManager.getBackStackEntryCount());

             /*   if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }*/


                break;
        }

        return super.onOptionsItemSelected(item);


    }





    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void home() {
        startActivity(new Intent(EvidenceActivity.this, DetailActivity.class), animateActivity(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle animateActivity(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }


}
