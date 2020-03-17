/*
 * Copyright (c) 2016 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oneplus.parts.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import com.oneplus.parts.R;
import com.oneplus.parts.utils.Preferences;

public class ButtonsFragment extends PreferenceFragment {

    private Handler mHandler;

    private SwitchPreference mVirtual;
    private ListPreference mSliderPositionTop;
    private ListPreference mSliderPositionMiddle;
    private ListPreference mSliderPositionBottom;
    private SwitchPreference mSwap;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_buttons);

        mHandler = new Handler();

        mVirtual = (SwitchPreference) findPreference("buttons_virtual");
        mVirtual.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_BUTTONS_VIRTUAL));
        mVirtual.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_BUTTONS_VIRTUAL, value);
                Settings.System.putInt(getActivity().getContentResolver(), Settings.System.NAVIGATION_BAR_SHOW, value);

                Preferences.setPreferenceToNode(Preferences.KEY_BUTTONS_VIRTUAL, Integer.toString(value));

                mVirtual.setEnabled(false);
                mSwap.setEnabled(!(boolean) objectValue);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVirtual.setEnabled(true);
                    }
                }, 1000);
                return true;
            }
        });

        mSliderPositionTop = (ListPreference) findPreference("buttons_slider_position_top");
        mSliderPositionTop.setValue((String) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_BUTTONS_SLIDER_TOP));
        mSliderPositionTop.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                String value = (String) objectValue;

                Settings.System.putString(getActivity().getContentResolver(), Preferences.KEY_BUTTONS_SLIDER_TOP, value);

                Preferences.setPreferenceToNode(Preferences.KEY_BUTTONS_SLIDER_TOP, value);
                return true;
            }
        });

        mSliderPositionMiddle = (ListPreference) findPreference("buttons_slider_position_middle");
        mSliderPositionMiddle.setValue((String) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_BUTTONS_SLIDER_MIDDLE));
        mSliderPositionMiddle.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                String value = (String) objectValue;

                Settings.System.putString(getActivity().getContentResolver(), Preferences.KEY_BUTTONS_SLIDER_MIDDLE, value);

                Preferences.setPreferenceToNode(Preferences.KEY_BUTTONS_SLIDER_MIDDLE, value);
                return true;
            }
        });

        mSliderPositionBottom = (ListPreference) findPreference("buttons_slider_position_bottom");
        mSliderPositionBottom.setValue((String) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_BUTTONS_SLIDER_BOTTOM));
        mSliderPositionBottom.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                String value = (String) objectValue;

                Settings.System.putString(getActivity().getContentResolver(), Preferences.KEY_BUTTONS_SLIDER_BOTTOM, value);

                Preferences.setPreferenceToNode(Preferences.KEY_BUTTONS_SLIDER_BOTTOM, value);
                return true;
            }
        });

        mSwap = (SwitchPreference) findPreference("buttons_swap");
        mSwap.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_BUTTONS_SWAP));
        mSwap.setEnabled(!mVirtual.isChecked());
        mSwap.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_BUTTONS_SWAP, value);

                Preferences.setPreferenceToNode(Preferences.KEY_BUTTONS_SWAP, Integer.toString(value));
                return true;
            }
        });
    }
}
