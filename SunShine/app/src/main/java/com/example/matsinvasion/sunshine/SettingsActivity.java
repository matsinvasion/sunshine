package com.example.matsinvasion.sunshine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import com.example.matsinvasion.sunshine.R;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add 'general' preferences located in the xml file
        addPreferencesFromResource(R.xml.pref_general);

        //For all Preferences attach an OnPreferenceChangeListener so that UI Summary
        //can be updated when values change
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        //Temperature Units preferences
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));


    }

    /**
     * Attaches a listener so that the summary is always updated with preference values
     */

    private void bindPreferenceSummaryToValue(Preference preference){
        //set listener to listen for value changes
        preference.setOnPreferenceChangeListener(this);

        // trigger preference immediaately with the preference's
        //current value
        onPreferenceChange(preference,
                PreferenceManager.
        getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if(preference instanceof ListPreference ){
            //for list preferences, lookup the correct display value in
            // the preference's 'entries' list (since they have seperate labels/value)

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0){
                preference.setSummary(listPreference.getEntries()[prefIndex]);

            }else{
                //for other preferences, set summary to the value's simple string representation
                preference.setSummary(stringValue);
            }
        }
        return false;
    }
}
