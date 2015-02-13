package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.drawer.MainActivity;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener, ActivityChange {

    private EditText editTxtEmail, editTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDirectory();
        systemBarsCustom();
        widgets();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                activitiesChanged();
                showToast("user: " + editTxtEmail.getText().toString() + " password: " + editTxtPassword.getText().toString() + " !");
                break;
            case R.id.btn_continue_photo:
                singUp();
                break;
        }

    }

    private void showToast(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void singUp() {
        startActivity((new Intent(LoginActivity.this, SignUp.class)), activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(this, R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }

    private void activitiesChanged() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void widgets() {
        Button btnLogin, btnRegister;
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_continue_photo);
        editTxtEmail = (EditText) findViewById(R.id.edit_txt_email);
        editTxtPassword = (EditText) findViewById(R.id.edit_txt_password);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(this));
        btnRegister.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(this));
        editTxtEmail.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(this));
        editTxtPassword.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(this));

    }

    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationSystemBars();
    }

    private void createDirectory() {

        // FALTA VALIDAR CUANDO ESTA LLENA LA MEMORIA
        if (StorageState.validateStorage(this)) {
            Log.i("DIRECTORY", "se puede crear ");
            StorageFiles.createDirectory(this);

        } else {
            Log.i("DIRECTORY", "no se puede crear");
        }
    }


}
