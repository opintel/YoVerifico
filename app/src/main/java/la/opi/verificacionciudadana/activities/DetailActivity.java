package la.opi.verificacionciudadana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.fragments.DetailFragment;
import la.opi.verificacionciudadana.util.Config;

public class DetailActivity extends BaseActivity {

    String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail_evidence, DetailFragment.newInstance())
                    .commit();
        }
        if (getIntent().getStringExtra(Config.KEY_INTENT) != null) {
            activity = getIntent().getStringExtra(Config.KEY_INTENT);
        } else {
            activity = "null";
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackpressedCustom();
                break;
            case R.id.map:

                if (activity.equals(Config.OCURRENCES_FRAGMENT)) {
                    Intent intent = new Intent(DetailActivity.this, MapaActivity.class);
                    intent.putExtra(Config.KEY_INTENT, Config.DETAIL_FRAGMENT);
                    startActivity(intent);
                    overridePendingTransition(R.animator.open_next, R.animator.close_main);

                } else if (activity.equals(Config.WINDOINFO_FRAGMENT)) {
                    super.onBackPressed();
                    animateActivity();
                }
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onBackpressedCustom();
    }

    private void onBackpressedCustom() {

        if (activity.equals(Config.OCURRENCES_FRAGMENT)) {
            super.onBackPressed();
            animateActivity();

        } else if (activity.equals(Config.WINDOINFO_FRAGMENT)) {
            super.onBackPressed();
            animateActivity();

        }

    }

    private void animateActivity() {
        overridePendingTransition(R.animator.open_main, R.animator.close_next);
    }


}
