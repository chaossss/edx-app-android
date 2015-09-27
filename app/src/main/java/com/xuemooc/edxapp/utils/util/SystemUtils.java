package com.xuemooc.edxapp.utils.util;

import android.content.res.Resources;

public class SystemUtils {
    public static int getScreenOrientation() {
        return Resources.getSystem().getConfiguration().orientation;
    }
}
