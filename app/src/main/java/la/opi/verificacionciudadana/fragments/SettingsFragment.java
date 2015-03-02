package la.opi.verificacionciudadana.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.opi.verificacionciudadana.R;
import la.opi.verificacionciudadana.util.ConfigurationPreferences;

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
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);

        addPreferencesFromResource(R.xml.general_preferences);


    }

    @Override
    public void onResume() {
        super.onResume();

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.recycle_events_background));

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


}
