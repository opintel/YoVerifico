package la.opi.verificacionciudadana.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.FragmentPhotoPictures;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;

public class EvidenceActivity extends BaseActivity implements ActivitySettings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_evidence, new FragmentPhotoPictures())
                    .commit();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_evidence;
    }

    /**
     * back navigation icon (android.R.id.home) back LoginActivity
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:


                break;
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void activitySettingsToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_register));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_register_text));
    }


}
