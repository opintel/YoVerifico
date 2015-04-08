package la.opi.verificacionciudadana.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.io.IOUtils;

import java.io.StringWriter;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.activities.LoginActivity;
import la.opi.verificacionciudadana.activities.TownActivity;
import la.opi.verificacionciudadana.activities.TutorialActivity;
import la.opi.verificacionciudadana.api.ApiPitagorasService;
import la.opi.verificacionciudadana.api.ClientServicePitagoras;
import la.opi.verificacionciudadana.util.Config;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jhordan on 07/02/15.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {

        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle extraArguments = new Bundle();
        settingsFragment.setArguments(extraArguments);
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.general_preferences);


    }

    @Override
    public void onResume() {
        super.onResume();

        final Preference closeSession = findPreference(getString(R.string.pref_close_session_key));

        closeSession.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                getActivity().finish();
                getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);
               ConfigurationPreferences.clearUserSessionPreference(getActivity());

                return false;
            }
        });


        Preference tutorial = findPreference(getString(R.string.pref_tutorial_key));
        tutorial.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                intent.putExtra(Config.FRAGMENT_TUTORIAL, Config.SHOWME_FROM_PREFERENCES_TUTORIAL);
                startActivity(intent);
                getActivity().overridePendingTransition(R.animator.open_next, R.animator.close_main);
                return false;
            }
        });

        Preference location = findPreference(getString(R.string.pref_location_key));
        location.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), TownActivity.class);
                startActivity(intent);

                return false;
            }
        });


        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.settings_tabs));

        return view;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String localidad = ConfigurationPreferences.getStatePreference(getActivity()) + " - " +
                ConfigurationPreferences.getMunicipioPreference(getActivity());
        preference.setSummary(localidad);
        return true;

    }

    private void logOut() {


        ApiPitagorasService apiPitagorasService = ClientServicePitagoras.getRestAdapter().create(ApiPitagorasService.class);
        apiPitagorasService.userLogOut().observeOn(AndroidSchedulers.handlerThread(new Handler())).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {

                final StringWriter writer = new StringWriter();

                try {
                    IOUtils.copy(response.getBody().in(), writer, Config.UTF_8);
                    System.out.println("response close session" + writer);
                } catch (Exception e) {

                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                throwable.printStackTrace();
            }
        });

    }



}
