package org.projects.shoppinglist;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by Kamal on 31/05/16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager manager = getPreferenceManager();
        //has to be the same as in MainActivity
        manager.setSharedPreferencesName("my_prefs");
        //adding the layout from the xml file
        addPreferencesFromResource(R.xml.prefs);
        //setContentView(R.layout.activity_settings);
    }
}
