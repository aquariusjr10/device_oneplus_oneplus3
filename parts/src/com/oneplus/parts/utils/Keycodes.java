package com.oneplus.parts.utils;

import android.util.SparseIntArray;

public class Keycodes {

    public static final int KEY_TGESTURES_DOUBLE_TAP = 249;
    public static final int KEY_TGESTURES_CIRCLE = 250;
    public static final int KEY_TGESTURES_TWO_SWIPE = 251;
    public static final int KEY_TGESTURES_DOWN_ARROW = 252;
    public static final int KEY_TGESTURES_LEFT_V = 253;
    public static final int KEY_TGESTURES_RIGHT_V = 254;

    public static final int MODE_SLIDER_TOTAL_SILENCE = 600;
    public static final int MODE_SLIDER_PRIORITY_ONLY = 601;
    public static final int MODE_SLIDER_VIBRATION = 602;
    public static final int MODE_SLIDER_NONE = 603;

    public static final int ZEN_MODE_NO_INTERRUPTIONS = 2;
    public static final int ZEN_MODE_IMPORTANT_INTERRUPTIONS = 1;
    public static final int ZEN_MODE_VIBRATION = 4;
    public static final int ZEN_MODE_OFF = 0;

    public static int[] KEYS_TGESTURES = {KEY_TGESTURES_DOUBLE_TAP, KEY_TGESTURES_CIRCLE, KEY_TGESTURES_TWO_SWIPE, KEY_TGESTURES_DOWN_ARROW, KEY_TGESTURES_LEFT_V, KEY_TGESTURES_RIGHT_V};

    public static SparseIntArray MODES_SLIDER = new SparseIntArray();
    static {
        MODES_SLIDER.put(MODE_SLIDER_TOTAL_SILENCE, ZEN_MODE_NO_INTERRUPTIONS);
        MODES_SLIDER.put(MODE_SLIDER_PRIORITY_ONLY, ZEN_MODE_IMPORTANT_INTERRUPTIONS);
        MODES_SLIDER.put(MODE_SLIDER_VIBRATION, ZEN_MODE_VIBRATION);
        MODES_SLIDER.put(MODE_SLIDER_NONE, ZEN_MODE_OFF);
    }

}
