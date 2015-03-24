package la.opi.verificacionciudadana.activities;

import android.os.Bundle;
import android.view.MenuItem;
import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.AboutFragment;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_about, AboutFragment.newInstance())
                    .commit();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
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
