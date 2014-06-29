package com.rsd.tryp.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Raukawa on 6/29/2014.
 */
public class OrientationUtil {
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
