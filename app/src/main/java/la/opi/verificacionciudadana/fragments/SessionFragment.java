package la.opi.verificacionciudadana.fragments;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.LoginScreenActivity;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.api.HttpHelper;
import la.opi.verificacionciudadana.dialogs.ConnectionDialog;
import la.opi.verificacionciudadana.dialogs.ErrorDialog;
import la.opi.verificacionciudadana.drawer.HomeMain;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.models.User;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.InternetConnection;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 08/03/15.
 */
public class SessionFragment extends Fragment implements View.OnClickListener, ActivityChange {

    public SessionFragment() {
    }

    public static SessionFragment newInstance() {

        SessionFragment loginFragment = new SessionFragment();
        Bundle extraArguments = new Bundle();
        loginFragment.setArguments(extraArguments);
        return loginFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private EditText editTxtEmail, editTxtPassword;
    private Button btnLogin;
    boolean textChangedEmail, textChangedPassword;
    String html, userToken, email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.sessión_with_email);
        widgets(rootView);


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (InternetConnection.isNetworkMobile(getActivity())) {
            if (!InternetConnection.connectionState(getActivity()) && !InternetConnection.mobileConnection(getActivity())) {
                showToast(R.string.not_internet_conection);
            }
        } else if (!InternetConnection.connectionState(getActivity())) {
            showToast(R.string.not_internet_conection);
        }

        buttonEnabled();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:

                try {
                    if (InternetConnection.connectionState(getActivity())) {
                        connectionPitagoras();
                    } else {
                        dialogNoConnection();
                        showToast(R.string.not_internet_conection);
                    }
                } catch (Exception e) {
                    showToast(R.string.expected_error);
                }


                break;


        }

    }


    //<editor-fold desc="Request Pitagoras">

    private void connectionPitagoras() {

        tokenRequest();
        email = editTxtEmail.getText().toString();
        password = editTxtPassword.getText().toString();
        boolean emailValida = isEmailValid(email);
        if (!emailValida) {
            dialogError();
        } else {

            singInRequest(EndPoint.PARAMETER_UTF8, userToken, email, password, EndPoint.PARAMETER_REMEMBERME, EndPoint.PARAMETER_COMMIT_SIGN_IN);
        }

    }

    public void tokenRequest() {

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

    private void singInRequest(String utf, String token, final String userMail, String userPassword, String rememberme, String commit) {

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_session), true);
        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userSingIn(utf, token, userMail, userPassword, rememberme, commit)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, VerificaCiudadConstants.UTF_8);
                    if (HttpHelper.regexLoginSuccess(writer.toString())) {

                        home();
                        ConfigurationPreferences.setMailPreference(getActivity(), userMail);
                        progressDialog.dismiss();
                    } else {

                        dialogError();

                        progressDialog.dismiss();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(R.string.expected_error);
                progressDialog.dismiss();

            }
        });

    }


    //</editor-fold>


    //<editor-fold desc="login success go home">
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void home() {
        getActivity().startActivity(new Intent(getActivity(), HomeMain.class), activitiesChanged(R.animator.animator_enter, R.animator.animator_exit));
        getActivity().finish();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Bundle activitiesChanged(int animateEnter, int animateExit) {
        return ActivityOptions.makeCustomAnimation(getActivity(), R.animator.animator_enter, R.animator.animator_exit).toBundle();
    }
    //</editor-fold>


    //<editor-fold desc="alerts and others">

    private void widgets(View rootView) {

        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        editTxtEmail = (EditText) rootView.findViewById(R.id.edit_txt_email);
        editTxtPassword = (EditText) rootView.findViewById(R.id.edit_txt_password);
        btnLogin.setOnClickListener(this);
        btnLogin.setEnabled(false);
        btnLogin.setTextColor(getResources().getColor(R.color.white_nitido));
        btnLogin.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(getActivity()));
        editTxtEmail.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
        editTxtPassword.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
    }

    private void buttonEnabled() {

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

    private void dialogNoConnection() {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ConnectionDialog dialog = new ConnectionDialog();
        dialog.show(fragmentManager, VerificaCiudadConstants.DIALOG_TEXT);
    }

    private void dialogError() {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ErrorDialog dialogo = new ErrorDialog();
        dialogo.show(fragmentManager, VerificaCiudadConstants.DIALOG_TEXT);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showToast(int message) {
        Toast.makeText(getActivity(), getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    //</editor-fold>

}
