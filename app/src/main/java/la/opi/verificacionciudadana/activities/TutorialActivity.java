package la.opi.verificacionciudadana.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.api.HttpHelper;
import la.opi.verificacionciudadana.exceptions.ExceptionErrorWriter;
import la.opi.verificacionciudadana.fragments.TutorialFragment;
import la.opi.verificacionciudadana.tabs.HomeTabs;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.InternetConnection;
import la.opi.verificacionciudadana.util.SessionManager;
import la.opi.verificacionciudadana.util.StorageFiles;
import la.opi.verificacionciudadana.util.StorageState;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TutorialActivity extends BaseActivity {
    String activity;
    Button btnOmited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnOmited = (Button) findViewById(R.id.btn_omited);

        btnOmited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.equals("null")) {

                    Intent intent = new Intent(TutorialActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.animator.open_next, R.animator.close_main);

                } else if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)) {
                    onBackPressed();
                    overridePendingTransition(R.animator.open_main, R.animator.close_next);
                }
            }
        });
        Crashlytics.start(this);

       // super.getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        createDirectory();

        if (getIntent().getStringExtra(Config.FRAGMENT_TUTORIAL) != null) {
            activity = getIntent().getStringExtra(Config.FRAGMENT_TUTORIAL);
        } else {
            activity = "null";
        }


        if (ConfigurationPreferences.getTutorialPreference(this)) {
            if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)) {
                showTutorial(savedInstanceState);
            } else {

                Log.d("tag_aux", ConfigurationPreferences.getOnDestroy(this).toString());
                if (!ConfigurationPreferences.getUserSession(this).equals("close_session")) {


                    if (InternetConnection.connectionState(this)) {
                        try {
                          //  super.getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
                            SharedPreferences preferences = getSharedPreferences(ConfigurationPreferences.TOKEN, Context.MODE_PRIVATE);
                            singInRequest(this, EndPoint.PARAMETER_UTF8,
                                    preferences.getString(ConfigurationPreferences.USER_TOKEN, ""),
                                    preferences.getString(ConfigurationPreferences.USER_MAIL_LOGIN, ""), preferences.getString(ConfigurationPreferences.USER_PASS, "")
                                    , EndPoint.PARAMETER_REMEMBERME, EndPoint.PARAMETER_COMMIT_SIGN_IN);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {

                        showToast(R.string.not_internet_conection);
                    }





                } else {
                    startActivity(new Intent(TutorialActivity.this, LoginActivity.class));
                    finish();
                }

            }

        } else {
            showTutorial(savedInstanceState);
        }

        ConfigurationPreferences.setTutorialPreference(this, true);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_demo;
    }

    @Override
    public void onBackPressed() {


        if (activity.equals(Config.SHOWME_TUTORIAL) || activity.equals(Config.SHOWME_FROM_PREFERENCES_TUTORIAL)) {
            super.onBackPressed();
            overridePendingTransition(R.animator.open_main, R.animator.close_next);
        }
    }

    private void signIn() {
        super.onBackPressed();
        overridePendingTransition(R.animator.open_main, R.animator.close_next);
    }

    private void showTutorial(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TutorialFragment.newInstance())
                    .commit();

        }
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


    private void singInRequest(final Context context, String utf, final String tokenLogin, final String userMail, final String userPassword, String rememberme, String commit) {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userSingIn(utf, tokenLogin, userMail, userPassword, rememberme, commit)
                .observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);
                    if (HttpHelper.regexLoginSuccess(writer.toString())) {


                        ConfigurationPreferences.setTokenPreference(context, tokenLogin, userPassword, userMail);
                        ConfigurationPreferences.setUserSession(context, "inicio_session_user");

                        Intent intent = new Intent(context, HomeTabs.class);
                        startActivity(intent);
                        finish();


                    } else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {


            }
        });


    }

    private void showToast(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

}
