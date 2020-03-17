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

package com.oneplus.parts.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.oneplus.parts.utils.Preferences;
import com.oneplus.parts.fragments.ButtonsFragment;

public class ButtonsActivity extends PreferenceActivity {
    public String TAG = "OnePlusParts-ButtonsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (!Preferences.BUTTONS_BOOTED) {
                Log.i(TAG, "Setting/writing preference values to nodes");

                for (String key : Preferences.KEYS_BUTTONS) {
                    Object objectValue = Preferences.getPreferenceValue(getApplicationContext(), key);

                    if (objectValue instanceof Boolean) {
                        int value = (boolean) objectValue ? 1 : 0;

                        Preferences.setPreferenceToNode(key, Integer.toString(value));
                    } else if (objectValue instanceof String) {
                        String value = (String) objectValue;

                        Preferences.setPreferenceToNode(key, value);
                    }
                }

                Preferences.BUTTONS_BOOTED = true;
            }

            getFragmentManager().beginTransaction().replace(android.R.id.content, new ButtonsFragment()).commit();
        }
    }

}
