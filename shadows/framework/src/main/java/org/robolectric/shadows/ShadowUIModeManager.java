package org.robolectric.shadows;

import android.app.UiModeManager;
import android.content.res.Configuration;
import com.google.common.collect.ImmutableSet;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 */
@Implements(UiModeManager.class)
public class ShadowUIModeManager {

    public int currentModeType = Configuration.UI_MODE_TYPE_UNDEFINED;

    public int currentNightMode = UiModeManager.MODE_NIGHT_AUTO;

    private static final ImmutableSet<Integer> VALID_NIGHT_MODES = ImmutableSet.of(UiModeManager.MODE_NIGHT_AUTO, UiModeManager.MODE_NIGHT_NO, UiModeManager.MODE_NIGHT_YES);

    @Implementation
    public int getCurrentModeType() {
        System.out.println("ShadowUIModeManager#getCurrentModeType");
        return currentModeType;
    }

    @Implementation
    public void enableCarMode(int flags) {
        System.out.println("ShadowUIModeManager#enableCarMode");
        currentModeType = Configuration.UI_MODE_TYPE_CAR;
    }

    @Implementation
    public void disableCarMode(int flags) {
        System.out.println("ShadowUIModeManager#disableCarMode");
        currentModeType = Configuration.UI_MODE_TYPE_NORMAL;
    }

    @Implementation
    public int getNightMode() {
        System.out.println("ShadowUIModeManager#getNightMode");
        return currentNightMode;
    }

    @Implementation
    public void setNightMode(int mode) {
        System.out.println("ShadowUIModeManager#setNightMode");
        if (VALID_NIGHT_MODES.contains(mode)) {
            currentNightMode = mode;
        } else {
            currentNightMode = UiModeManager.MODE_NIGHT_AUTO;
        }
    }
}

