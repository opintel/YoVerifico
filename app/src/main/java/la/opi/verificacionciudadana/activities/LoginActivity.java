package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.api.HttpHelper;
import la.opi.verificacionciudadana.api.RetrofitErrorHandler;
import la.opi.verificacionciudadana.dialogs.ConnectionDialog;
import la.opi.verificacionciudadana.dialogs.ErrorDialog;
import la.opi.verificacionciudadana.drawer.MainActivity;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.models.User;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.InternetConnection;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener, ActivityChange {

    private EditText editTxtEmail, editTxtPassword;
    private String email, password;
    Button btnLogin, btnRegister;
    boolean textChangedEmail, textChangedPassword;
    String html;
    String userToken = "";

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDirectory();
        systemBarsCustom();
        widgets();
        btnLogin.setEnabled(false);
        btnLogin.setTextColor(getResources().getColor(R.color.white_nitido));

        imageView = (ImageView) findViewById(R.id.pruebas);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!InternetConnection.connectionState(this) && !InternetConnection.mobileConnection(this)) {
            showToast(R.string.not_internet_conection);
        }
        enabledButtonLogin();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:

                try {
                    if (InternetConnection.connectionState(this)) {
                        connectionPitagoras();
                    } else {
                        dialogConnection();
                        showToast(R.string.not_internet_conection);
                    }
                } catch (Exception e) {
                    showToast("no pudimos conectarnos vatp");
                }


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

    private void connectionPitagoras() {


        if (!InternetConnection.mobileConnection(this) && !InternetConnection.connectionState(this)) {
            dialogConnection();
        }
        homeRequest();
        email = editTxtEmail.getText().toString();
        password = editTxtPassword.getText().toString();
        boolean emailValida = isEmailValid(email);
        if (!emailValida) {

            dialogError();

        } else {

            User user = new User();
            user.setUserEmail(email);
            user.setUserPassword(password);
            singInRequest(EndPoint.PARAMETER_UTF8, userToken, email, password, EndPoint.PARAMETER_REMEMBERME, EndPoint.PARAMETER_COMMIT_SIGN_IN);
        }

    }

    private void singInRequest(String utf, String token, final String userMail, String userPassword, String rememberme, String commit) {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userSingIn(utf, token, userMail, userPassword, rememberme, commit)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, VerificaCiudadConstants.UTF_8);
                    if (HttpHelper.regexLoginSuccess(writer.toString())) {

                        activitiesChanged();
                        ConfigurationPreferences.setMailPreference(LoginActivity.this, userMail);

                    } else {

                        dialogError();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(R.string.expected_error);

            }
        });

    }

    private void dialogError() {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ErrorDialog dialogo = new ErrorDialog();
        dialogo.show(fragmentManager, VerificaCiudadConstants.DIALOG_TEXT);
    }

    private void dialogConnection() {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ConnectionDialog dialog = new ConnectionDialog();
        dialog.show(fragmentManager, VerificaCiudadConstants.DIALOG_TEXT);
    }

    private void enabledButtonLogin() {

        editTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {

                    textChangedEmail = true;
                    if (textChangedEmail == textChangedPassword) {
                        btnLogin.setEnabled(true);
                        btnLogin.setTextColor(getResources().getColor(R.color.white));

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
                if (s.length() > 0) {

                    textChangedPassword = true;
                    if (textChangedEmail == textChangedPassword) {
                        btnLogin.setEnabled(true);
                        btnLogin.setTextColor(getResources().getColor(R.color.white));


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

    public void homeRequest() {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getHome().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, VerificaCiudadConstants.UTF_8);

                    String token = "";
                    try {
                        html = writer.toString();
                        token = HttpHelper.regexToken(html);
                    } catch (Exception e) {
                        Log.e(VerificaCiudadConstants.ERROR_REGULAR_EXPRESION, e.toString());
                    }

                    userToken = token;
                    System.out.println("TOKEN API:  " + token);

                /*    if(token != null){
                        TokenPreference.setTokenPreference(context, token);
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(VerificaCiudadConstants.ERROR_RETROFIT, throwable.getMessage());
            }
        });


    }

}
