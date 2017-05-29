package de.mi.ur.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import de.mi.ur.SettingsFragment;

/**
 * Created by Anna-Marie on 10.08.2016.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }


}
