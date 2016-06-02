package org.projects.shoppinglist;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by Kamal on 31/05/16.
 * Here we make the functionality for remembering the name
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName("my_prefs");
        addPreferencesFromResource(R.xml.prefs);
    }
}
