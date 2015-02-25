package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.drawer.MainActivity;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.models.User;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener, ActivityChange {

    private EditText editTxtEmail, editTxtPassword;
    private String email, password;
    Button btnLogin, btnRegister;
    boolean textChangedEmail, textChangedPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDirectory();
        systemBarsCustom();
        widgets();
        btnLogin.setEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        enabledButtonLogin();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:

                //  activitiesChangedMunicipal();
                activitiesChanged();
                //   validateData();

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


        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent, activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
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

    private void activitiesChangedMunicipal() {
        startActivity(new Intent(LoginActivity.this, MunicipalActivity.class));
        finish();
    }

    private void validateData() {
        email = editTxtEmail.getText().toString();
        password = editTxtPassword.getText().toString();
        boolean emailValida = isEmailValid(email);
        if (!emailValida) {
            showToast("password o mail invalido");

        } else {

            User user = new User();
            user.setUserEmail(email);
            user.setUserPassword(password);
            showToast(user.getUserEmail() + " " + user.getUserPassword());
        }

    }

    private void loginRequest() {


        Log.i("datos", email);
        Log.i("datos", password);


    }

    private void enabledButtonLogin() {

        editTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count != 0) {

                    textChangedEmail = true;
                    if (textChangedEmail == textChangedPassword) {
                        btnLogin.setEnabled(true);


                    }

                } else {
                    btnLogin.setEnabled(false);
                    textChangedEmail = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTxtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {

                    textChangedPassword = true;
                    if (textChangedEmail == textChangedPassword) {
                        btnLogin.setEnabled(true);


                    }

                } else {
                    textChangedPassword = false;
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void widgets() {

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
