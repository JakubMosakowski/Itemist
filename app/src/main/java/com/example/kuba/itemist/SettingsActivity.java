package com.example.kuba.itemist;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity
    {
        @Override
        protected void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            LinearLayout linearLayout = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, linearLayout, false);

            linearLayout.addView(toolbar, 0);


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overridePendingTransition(0,0);
                    finish();
                }
            });
            ImageButton settingsButton=(ImageButton)findViewById(R.id.settings_button);
            settingsButton.setVisibility(View.INVISIBLE);
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        }

        public static class MyPreferenceFragment extends PreferenceFragment
        {
            @Override
            public void onCreate(final Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.app_preferences);

            }
        }
    }