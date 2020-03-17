/*
 * Copyright (C) 2015 The CyanogenMod Project
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

package com.oneplus.parts.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.util.ArrayUtils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Preferences {
    private static String TAG = "OnePlusParts-Preferences";

    public static boolean BUTTONS_BOOTED = false;
    public static boolean TGESTURES_BOOTED = false;

    public static final String KEY_BUTTONS_VIRTUAL = "buttons_virtual";
    public static final String KEY_BUTTONS_SLIDER_TOP = "buttons_slider_top";
    public static final String KEY_BUTTONS_SLIDER_MIDDLE = "buttons_slider_middle";
    public static final String KEY_BUTTONS_SLIDER_BOTTOM = "buttons_slider_bottom";
    public static final String KEY_BUTTONS_SWAP = "buttons_swap";

    public static final String KEY_TGESTURES_CAMERA = "tgestures_camera";
    public static final String KEY_TGESTURES_FLASHLIGHT = "tgestures_flashlight";
    public static final String KEY_TGESTURES_MUSIC = "tgestures_music";
    public static final String KEY_TGESTURES_HAPTIC_FEEDBACK = "tgestures_haptic_feedback";

    public static final String NODE_BUTTONS_SLIDER_TOP = "/proc/tri-state-key/keyCode_top";
    public static final String NODE_BUTTONS_SLIDER_MIDDLE = "/proc/tri-state-key/keyCode_middle";
    public static final String NODE_BUTTONS_SLIDER_BOTTOM = "/proc/tri-state-key/keyCode_bottom";
    public static final String NODE_BUTTONS_SWAP = "/proc/s1302/key_rep";

    public static final String NODE_TGESTURES_CAMERA = "/proc/touchpanel/letter_o_enable";
    public static final String NODE_TGESTURES_FLASHLIGHT = "/proc/touchpanel/down_arrow_enable";
    public static final String NODE_TGESTURES_MUSIC_1 = "/proc/touchpanel/double_swipe_enable";
    public static final String NODE_TGESTURES_MUSIC_2 = "/proc/touchpanel/left_arrow_enable";
    public static final String NODE_TGESTURES_MUSIC_3 = "/proc/touchpanel/right_arrow_enable";

    public static String[] KEYS_BUTTONS = {KEY_BUTTONS_VIRTUAL, KEY_BUTTONS_SLIDER_TOP, KEY_BUTTONS_SLIDER_MIDDLE, KEY_BUTTONS_SLIDER_BOTTOM, KEY_BUTTONS_SWAP};
    public static String[] KEYS_BUTTONS_SLIDER = {KEY_BUTTONS_SLIDER_TOP, KEY_BUTTONS_SLIDER_MIDDLE, KEY_BUTTONS_SLIDER_BOTTOM};
    public static String[] KEYS_TGESTURES = {KEY_TGESTURES_CAMERA, KEY_TGESTURES_FLASHLIGHT, KEY_TGESTURES_MUSIC, KEY_TGESTURES_HAPTIC_FEEDBACK};
    public static String[] NODES_TGESTURES_MUSIC = {NODE_TGESTURES_MUSIC_1, NODE_TGESTURES_MUSIC_2, NODE_TGESTURES_MUSIC_3};

    public static HashMap<String, Object> DEFAULTS = new HashMap<>();
    static {
        DEFAULTS.put(KEY_BUTTONS_VIRTUAL, 1);
        DEFAULTS.put(KEY_BUTTONS_SLIDER_TOP, "601");
        DEFAULTS.put(KEY_BUTTONS_SLIDER_MIDDLE, "602");
        DEFAULTS.put(KEY_BUTTONS_SLIDER_BOTTOM, "603");
        DEFAULTS.put(KEY_BUTTONS_SWAP, 0);

        DEFAULTS.put(KEY_TGESTURES_CAMERA, 0);
        DEFAULTS.put(KEY_TGESTURES_FLASHLIGHT, 0);
        DEFAULTS.put(KEY_TGESTURES_MUSIC, 0);
        DEFAULTS.put(KEY_TGESTURES_HAPTIC_FEEDBACK, 1);
    }

    public static HashMap<String, String> NODES = new HashMap<>();
    static {
        NODES.put(KEY_BUTTONS_VIRTUAL, null);
        NODES.put(KEY_BUTTONS_SLIDER_TOP, NODE_BUTTONS_SLIDER_TOP);
        NODES.put(KEY_BUTTONS_SLIDER_MIDDLE, NODE_BUTTONS_SLIDER_MIDDLE);
        NODES.put(KEY_BUTTONS_SLIDER_BOTTOM, NODE_BUTTONS_SLIDER_BOTTOM);
        NODES.put(KEY_BUTTONS_SWAP, NODE_BUTTONS_SWAP);

        NODES.put(KEY_TGESTURES_CAMERA, NODE_TGESTURES_CAMERA);
        NODES.put(KEY_TGESTURES_FLASHLIGHT, NODE_TGESTURES_FLASHLIGHT);
        NODES.put(KEY_TGESTURES_MUSIC, NODE_TGESTURES_MUSIC_1);
        NODES.put(KEY_TGESTURES_HAPTIC_FEEDBACK, null);
    }

    public static Object getPreferenceValue(Context context, String key) {
        if (!ArrayUtils.contains(KEYS_BUTTONS_SLIDER, key)) {
            int value = Settings.System.getInt(context.getContentResolver(), key, -1);
            if (value == -1) {
                value = (int) DEFAULTS.get(key);
            }

            return value == 1;
        } else {
            String value = Settings.System.getString(context.getContentResolver(), key);
            if (value == null) {
                value = (String) DEFAULTS.get(key);
            }

            return value;
        }
    }

    public static void setPreferenceToNode(String key, String value) {
        if (key.equals(KEY_BUTTONS_VIRTUAL) || key.equals(KEY_TGESTURES_HAPTIC_FEEDBACK)) {
            return;
        }

        String node;
        if (!ArrayUtils.contains(NODES_TGESTURES_MUSIC, key)) {
            node = NODES.get(key);
        } else {
            node = key;
        }

        if (!writeLine(node, value)) {
            Log.w(TAG, "Write " + value + " to node " + node + " failed");
        }
    }

    public static boolean writeLine(String fileName, String value) {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(value);
        } catch (FileNotFoundException ex) {
            Log.w(TAG, "No such file " + fileName + " for writing");

            return false;
        } catch (IOException ex) {
            Log.e(TAG, "Could not write to file " + fileName, ex);

            return false;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                // Ignored, not much we can do anyway
            }
        }

        return true;
    }

}
