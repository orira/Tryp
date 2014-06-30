package com.rsd.tryp.util;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by Raukawa on 6/30/2014.
 */
public class KeyboardUtil {

    public static boolean isKeyboardShowing(View rootContainer) {
        Rect r = new Rect();
        rootContainer.getWindowVisibleDisplayFrame(r);
        int heightDiff = rootContainer.getRootView().getHeight() - (r.bottom - r.top);

        return heightDiff > 100;
    }
}
