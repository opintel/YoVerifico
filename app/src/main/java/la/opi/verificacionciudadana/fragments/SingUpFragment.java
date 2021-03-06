package la.opi.verificacionciudadana.fragments;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.adapters.SpinnerCustomAdapter;
import la.opi.verificacionciudadana.adapters.SpinnerCustomTownAdapter;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.dialogs.ConnectionRegisterDialog;
import la.opi.verificacionciudadana.dialogs.CustomDialog;
import la.opi.verificacionciudadana.models.State;
import la.opi.verificacionciudadana.models.Town;
import la.opi.verificacionciudadana.parser.ParserStatesSpinner;
import la.opi.verificacionciudadana.parser.RegisterResponse;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import la.opi.verificacionciudadana.util.InternetConnection;
import la.opi.verificacionciudadana.util.VerificaCiudadFonts;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 08/03/15.
 */
public class SingUpFragment extends Fragment implements View.OnClickListener {

    public SingUpFragment() {
    }

    public static SingUpFragment newInstance() {

        SingUpFragment loginFragment = new SingUpFragment();
        Bundle extraArguments = new Bundle();
        loginFragment.setArguments(extraArguments);
        return loginFragment;
    }

    private EditText editTxtName, editTxtEmail, editTxtPassword, editTxtPassConfirm;
    private Button btnRegisterme;
    Spinner spinnerMunicipal, spinnerStates;
    private String userName, userMail, userPassword, userConfirm, userMunicipio, userState;
    private Boolean textChangeA, textChangeB, textChangeC, textChangeD;
    SpinnerCustomAdapter spinnerCustomAdapter ;
    String keyMunicipio, keyState;
    String estado;
    String municipio;
    String idState;
    String idTwon;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.register_with_email);
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

        if(spinnerCustomAdapter == null){
           requestStates();
        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register_user:

                try {
                    if (InternetConnection.connectionState(getActivity())) {

                        connnectionPitagoras();


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

    private void connnectionPitagoras() {


        String token = "";

        userName = editTxtName.getText().toString();
        userMail = editTxtEmail.getText().toString();
        userPassword = editTxtPassword.getText().toString();
        userConfirm = editTxtPassConfirm.getText().toString();
        userMunicipio = itemSpinnerTownListener();
        boolean emailValida = isEmailValid(userMail);

        if (!emailValida) {

            dialogCustom(R.string.message_mail);


        } else if (!userPassword.equals(userConfirm) || userPassword.length() <= 7) {

            dialogCustom(R.string.message_password_equals);

        } else if (idState == null || idTwon == null) {

            dialogCustom(R.string.expected_connection);
            editTxtPassword.setText("");
            editTxtPassConfirm.setText("");
            editTxtName.setText("");
            editTxtEmail.setText("");



        } else {

              singUserRequest(EndPoint.PARAMETER_TOKEN, EndPoint.PARAMETER_UTF8, userName, userMail, EndPoint.PARAMETER_USER_ROLE, idState, idTwon,
                    userPassword, userConfirm, EndPoint.PARAMETER_COMMIT_SIGN_UP);
        }


    }


    private void singUserRequest(String token, String utf, String userName, String userMail, String userRole,
                                 String userState, String userMunicipio, String userPassword, String userConfirm, String commit) {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_sing_up), true);
        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userSignUp(token, utf, userName, userMail, userRole, userState, userMunicipio, userPassword,
                userConfirm, commit).observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                try {
                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, "UTF-8");
                    RegisterResponse.registerResponseSucces(writer.toString());
                    progressDialog.dismiss();
                    if(response.getStatus() == 200){
                        showToast("Se ha registrado a "+  RegisterResponse.getName() + " exitosamente" + "");
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();

                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                if( throwable.getMessage().equals("422 Unprocessable Entity") || throwable.getMessage().equals("422")){
                    showToast("usuario ya esta registrado");
                    progressDialog.dismiss();

                }else{
                    Log.e(Config.ERROR_RETROFIT, throwable.getMessage());
                    showToast(R.string.expected_error);
                    progressDialog.dismiss();
                }


            }
        });

    }



    private void requestStates() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.progress_dialog_help), true);
        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.getMexicoStates().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {


                try {

                    final StringWriter writer = new StringWriter();
                    IOUtils.copy(response.getBody().in(), writer, "UTF-8");

                    final SpinnerCustomAdapter spinnerCustomAdapter = new
                            SpinnerCustomAdapter(getActivity(), ParserStatesSpinner.paserState(writer.toString()));

                    progressDialog.dismiss();


                    //set userState by idstate
                    for (Map.Entry<Integer, String> entry : ParserStatesSpinner.getStatehashMap().entrySet()) {
                        Integer key = entry.getKey();
                        String value = entry.getValue();

                        if (value.equals(ConfigurationPreferences.getIdStatePreference(getActivity()))) {
                            keyState = key.toString();

                        }


                    }


                    spinnerStates.setAdapter(spinnerCustomAdapter);



                    spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            estado = ((State) spinnerStates.getItemAtPosition(position)).getName();
                            idState = ((State) spinnerStates.getItemAtPosition(position)).getId();

                            ArrayList<Town> townArrayList = ((State) spinnerStates.getItemAtPosition(position)).getTownArrayList();


                            //set userTwon by idTwon

                            HashMap<Integer, String> townHash = new HashMap<>();
                            int positionList = 0;

                            Collections.sort(townArrayList, new ParserStatesSpinner.CustomComparator());
                            for (Town town : townArrayList) {
                                townHash.put(positionList, town.getId());
                                positionList++;
                            }
                            for (Map.Entry<Integer, String> entry : townHash.entrySet()) {
                                Integer key = entry.getKey();
                                String value = entry.getValue();

                                if (value.equals(ConfigurationPreferences.getIdMunicipioPreference(getActivity()))) {
                                    keyMunicipio = key.toString();
                                }


                            }


                            final SpinnerCustomTownAdapter spinnerCustomTownAdapter = new SpinnerCustomTownAdapter(getActivity(), townArrayList);

                            Collections.sort(townArrayList, new ParserStatesSpinner.CustomComparator());

                            spinnerMunicipal.setAdapter(spinnerCustomTownAdapter);




                            spinnerMunicipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    idTwon = ((Town) spinnerMunicipal.getItemAtPosition(position)).getId();
                                    municipio = ((Town) spinnerMunicipal.getItemAtPosition(position)).getName();

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


                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(Config.ERROR_RETROFIT, throwable.getMessage());
                progressDialog.dismiss();
            }
        });


    }

    private ArrayList<String> getNameTown(State state) {
        return state.getTown();
    }

    private String getState(State state) {
        return state.getName();
    }

    private String itemSpinnerTownListener() {
        return String.valueOf(spinnerMunicipal.getSelectedItem());
    }

    private void showToast(int message) {
        Toast.makeText(getActivity(), getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void dialogNoConnection() {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ConnectionRegisterDialog dialog = new ConnectionRegisterDialog();
        dialog.show(fragmentManager, Config.DIALOG_TEXT);
    }

    private void dialogCustom(int message) {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CustomDialog dialog = CustomDialog.newInstance(message);
        dialog.show(fragmentManager, Config.DIALOG_TEXT);
    }

    private void buttonEnabled() {

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
                        btnRegisterme.setTextColor(getResources().getColor(R.color.white));



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
                        btnRegisterme.setTextColor(getResources().getColor(R.color.white));



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
                        btnRegisterme.setTextColor(getResources().getColor(R.color.white));



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
                        btnRegisterme.setTextColor(getResources().getColor(R.color.white));



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

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void widgets(View rootView) {

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
        btnRegisterme.setEnabled(false);

        btnRegisterme.setTextColor(getResources().getColor(R.color.white_nitido));


    }

}
