package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;

public class SignUp extends BaseActivity implements ActivityChange, ActivitySettings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsToolbar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SignUpFragment())
                    .commit();
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                loginActivity();
                break;
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void activitySettingsToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_register));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_register_text));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        loginActivity();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void loginActivity() {
        startActivity(new Intent(SignUp.this, LoginActivity.class), activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        finish();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }


    /**
     * SingUp Fragment start
     */
    public static class SignUpFragment extends Fragment {

        public SignUpFragment() {

        }

        private EditText editTxtName, editTxtEmail, editTxtPassword, editTxtPassConfirm;
        private Button btnRegisterme;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.sign_up_title);
            initializeWidgets(rootView);


            return rootView;
        }

        private void initializeWidgets(View rootView) {

            editTxtName = (EditText) rootView.findViewById(R.id.edit_txt_name);
            editTxtEmail = (EditText) rootView.findViewById(R.id.edit_txt_email);
            editTxtPassword = (EditText) rootView.findViewById(R.id.edit_txt_password);
            editTxtPassConfirm = (EditText) rootView.findViewById(R.id.edit_txt_password_confirm);
            btnRegisterme = (Button) rootView.findViewById(R.id.btn_register);
            editTxtName.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtEmail.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtPassword.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtPassConfirm.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            btnRegisterme.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(getActivity()));

        }

    }


}
