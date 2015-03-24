package la.opi.verificacionciudadana.activities;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.FragmentPictureEvidences;
import la.opi.verificacionciudadana.fragments.InformacionFragment;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;

public class InformacionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
       // getToolbar().setNavigationIcon(getResources().getDrawable(R.drawable.ic_exit));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_informacion, FragmentPictureEvidences.newInstance())
                    .commit();
        }


    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_informacion;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_evidence, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                animateActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animateActivity();
    }

    private void animateActivity() {
        overridePendingTransition(R.animator.open_main, R.animator.close_next);
    }


}
