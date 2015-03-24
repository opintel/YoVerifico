package la.opi.verificacionciudadana.activities;

import android.os.Bundle;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.HelpFragment;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, HelpFragment.newInstance())
                    .commit();
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_help;
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
