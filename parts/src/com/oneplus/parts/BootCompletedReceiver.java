/*
 * Copyright (c) 2015 The CyanogenMod Project
 * Copyright (c) 2017 The LineageOS Project
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

package com.oneplus.parts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.oneplus.parts.utils.Preferences;

public class BootCompletedReceiver extends BroadcastReceiver {
    public String TAG = "OnePlusParts-BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || Intent.ACTION_PRE_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "Setting/writing preference values to nodes");

            if (!Preferences.BUTTONS_BOOTED) {
                for (String key : Preferences.KEYS_BUTTONS) {
                    Object objectValue = Preferences.getPreferenceValue(context, key);

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

            if (!Preferences.TGESTURES_BOOTED) {
                for (String key : Preferences.KEYS_TGESTURES) {
                    Object objectValue = Preferences.getPreferenceValue(context, key);

                    if (objectValue instanceof Boolean) {
                        int value = (boolean) objectValue ? 1 : 0;

                        if (!key.equals(Preferences.KEY_TGESTURES_MUSIC)) {
                            Preferences.setPreferenceToNode(key, Integer.toString(value));
                        } else {
                            for (String node : Preferences.NODES_TGESTURES_MUSIC) {
                                Preferences.setPreferenceToNode(node, Integer.toString(value));
                            }
                        }
                    }
                }

                Preferences.TGESTURES_BOOTED = true;
            }
        }
    }

}
