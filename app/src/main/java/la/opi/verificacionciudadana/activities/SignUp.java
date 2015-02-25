package la.opi.verificacionciudadana.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.SpinnerCustomAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.interfaces.ActivityChange;
import la.opi.verificacionciudadana.interfaces.ActivitySettings;
import la.opi.verificacionciudadana.models.*;
import la.opi.verificacionciudadana.models.Town;
import la.opi.verificacionciudadana.parser.ParserStatesSpinner;
import la.opi.verificacionciudadana.util.SystemConfigurationBars;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SignUp extends BaseActivity implements ActivityChange, ActivitySettings {

    public static String responseStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemBarsCustom();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SignUpFragment())
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        responseStates = requestStates();
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
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void systemBarsCustom() {
        SystemConfigurationBars systemConfigurationBars = new SystemConfigurationBars(this);
        systemConfigurationBars.configurationSystemBars();
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

    private String requestStates() {

        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getMexicoStates().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, "UTF-8");
                    responseStates = writer.toString();
                    Log.i("datos mexico", responseStates);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return responseStates;
    }


    /**
     * SingUp Fragment start
     */
    public static class SignUpFragment extends Fragment implements View.OnClickListener {

        public SignUpFragment() {

        }

        private EditText editTxtName, editTxtEmail, editTxtPassword, editTxtPassConfirm;
        private Button btnRegisterme;
        Spinner spinnerMunicipal, spinnerStates;
        private java.lang.String userName, userMail, userPassword, userConfirm, userMunicipio, userState, dataStates;
        private Boolean textChangeA, textChangeB, textChangeC, textChangeD;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.sign_up_title);
            initializeWidgets(rootView);
            btnRegisterme.setEnabled(false);


            return rootView;
        }


        @Override
        public void onResume() {
            super.onResume();


            textChanged();

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_register_user:
                    validate();
                    break;
            }
        }


        private void spinnerData() {


            if (responseStates != null) {


                SpinnerCustomAdapter spinnerCustomAdapter = new
                        SpinnerCustomAdapter(getActivity(), ParserStatesSpinner.paserState(responseStates));

                spinnerStates.setAdapter(spinnerCustomAdapter);


            } else {
                System.out.println("VACIO");
            }


            spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    userState = getState((State) spinnerStates.getItemAtPosition(position));
                    ArrayList<String> town = getNameTown((State) spinnerStates.getItemAtPosition(position));
                    Collections.sort(town);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            R.layout.item_spinner_mexico,
                            R.id.item_spinner,
                            town);

                    spinnerMunicipal.setAdapter(adapter);

                    spinnerMunicipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                          //  userMunicipio = adapter.getItem(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


        private void validate() {

            userName = editTxtName.getText().toString();
            userMail = editTxtEmail.getText().toString();
            userPassword = editTxtPassword.getText().toString();
            userConfirm = editTxtPassConfirm.getText().toString();
            userMunicipio = itemSpinnerTownListener();
            boolean emailValida = isEmailValid(userMail);

            if (emailValida && userPassword.length() >= 8 && userPassword.equals(userConfirm)) {

                showToast(userName + userMail + userPassword + userConfirm + userState + userMunicipio);

            } else if (userPassword.length() <= 8 && userPassword.equals(userConfirm)) {
                showToast("Contraseña de contener minimo 8 caracteres ");
            } else {
                showToast("Email invalido o Contraseñas invalidas ");
            }


        }

        private void initializeWidgets(View rootView) {

            editTxtName = (EditText) rootView.findViewById(R.id.edit_txt_name);
            editTxtEmail = (EditText) rootView.findViewById(R.id.edit_txt_email);
            editTxtPassword = (EditText) rootView.findViewById(R.id.edit_txt_password);
            editTxtPassConfirm = (EditText) rootView.findViewById(R.id.edit_txt_password_confirm);
            btnRegisterme = (Button) rootView.findViewById(R.id.btn_register_user);
            spinnerMunicipal = (Spinner) rootView.findViewById(R.id.spinner_municipal);
            spinnerStates = (Spinner) rootView.findViewById(R.id.spinner_state);
            editTxtName.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtEmail.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtPassword.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            editTxtPassConfirm.setTypeface(VerificaCiudadFonts.typefaceRobotoRegular(getActivity()));
            btnRegisterme.setTypeface(VerificaCiudadFonts.typefaceRobotoBold(getActivity()));
            btnRegisterme.setOnClickListener(this);

        }

        private boolean isEmailValid(CharSequence email) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        private void textChanged() {

            editTxtName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (s.length() > 0) {

                        textChangeA = true;
                        if (textChangeA == textChangeB && textChangeA == textChangeC && textChangeA == textChangeD) {

                            btnRegisterme.setEnabled(true);

                        }


                    } else {
                        textChangeA = false;
                        btnRegisterme.setEnabled(false);


                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            editTxtEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (s.length() > 0) {

                        textChangeB = true;
                        if (textChangeA == textChangeB && textChangeC == textChangeB && textChangeD == textChangeB) {
                            btnRegisterme.setEnabled(true);

                        }

                    } else {
                        textChangeB = false;
                        btnRegisterme.setEnabled(false);


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

                        textChangeC = true;


                        if (textChangeA == textChangeC && textChangeB == textChangeC && textChangeD == textChangeC) {
                            btnRegisterme.setEnabled(true);

                        }


                    } else {
                        textChangeC = false;
                        btnRegisterme.setEnabled(false);


                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            editTxtPassConfirm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (s.length() > 0) {

                        textChangeD = true;

                        if (textChangeA == textChangeD && textChangeD == textChangeB && textChangeD == textChangeC) {
                            btnRegisterme.setEnabled(true);
                            spinnerData();

                        }


                    } else {
                        textChangeD = false;
                        btnRegisterme.setEnabled(false);


                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        private String itemSpinnerTownListener() {
            return String.valueOf(spinnerMunicipal.getSelectedItem());
        }

        private void showToast(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

        private ArrayList<String> getNameTown(State state) {
            return state.getTown();
        }

        private String getState(State state) {
            return state.getName();
        }


    }


}
