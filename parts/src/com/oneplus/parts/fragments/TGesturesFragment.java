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
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import com.oneplus.parts.R;
import com.oneplus.parts.utils.Preferences;

public class TGesturesFragment extends PreferenceFragment {

    private SwitchPreference mGestureCamera;
    private SwitchPreference mGestureFlashlight;
    private SwitchPreference mGestureMusic;
    private SwitchPreference mHapticFeedback;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_tgestures);

        mGestureCamera = (SwitchPreference) findPreference("tgestures_camera");
        mGestureCamera.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_TGESTURES_CAMERA));
        mGestureCamera.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_TGESTURES_CAMERA, value);

                Preferences.setPreferenceToNode(Preferences.KEY_TGESTURES_CAMERA, Integer.toString(value));
                return true;
            }
        });

        mGestureFlashlight = (SwitchPreference) findPreference("tgestures_flashlight");
        mGestureFlashlight.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_TGESTURES_FLASHLIGHT));
        mGestureFlashlight.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_TGESTURES_FLASHLIGHT, value);

                Preferences.setPreferenceToNode(Preferences.KEY_TGESTURES_FLASHLIGHT, Integer.toString(value));
                return true;
            }
        });

        mGestureMusic = (SwitchPreference) findPreference("tgestures_music");
        mGestureMusic.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_TGESTURES_MUSIC));
        mGestureMusic.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_TGESTURES_MUSIC, value);

                for (String node : Preferences.NODES_TGESTURES_MUSIC) {
                    Preferences.setPreferenceToNode(node, Integer.toString(value));
                }
                return true;
            }
        });

        mHapticFeedback = (SwitchPreference) findPreference("tgestures_haptic_feedback");
        mHapticFeedback.setChecked((boolean) Preferences.getPreferenceValue(getActivity().getApplicationContext(), Preferences.KEY_TGESTURES_HAPTIC_FEEDBACK));
        mHapticFeedback.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object objectValue) {
                int value = (boolean) objectValue ? 1 : 0;

                Settings.System.putInt(getActivity().getContentResolver(), Preferences.KEY_TGESTURES_HAPTIC_FEEDBACK, value);

                Preferences.setPreferenceToNode(Preferences.KEY_TGESTURES_HAPTIC_FEEDBACK, Integer.toString(value));
                return true;
            }
        });
    }
}
