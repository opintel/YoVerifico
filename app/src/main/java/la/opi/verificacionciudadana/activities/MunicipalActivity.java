package la.opi.verificacionciudadana.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.MunicipalFragment;


public class MunicipalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MunicipalFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_municipal;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_municipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
