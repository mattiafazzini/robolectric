package org.robolectric.shadows;

import android.app.QueuedWork;
import android.os.Build.VERSION_CODES;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadow.api.Shadow;

/**
 * Empty Shadow class for SharedPreferences
 */
public class ShadowSharedPreferences {

    /**
     * Shadow for SharedPreferencesImpl$EditorImpl
     */
    @Implements(className = "android.app.SharedPreferencesImpl$EditorImpl", minSdk = VERSION_CODES.O, isInAndroidSdk = false)
    public static class ShadowSharedPreferencesEditorImpl {

        @RealObject
        Object realObject;

        @Implementation
        protected void apply() {
            System.out.println("ShadowSharedPreferencesEditorImpl#apply");
            Shadow.directlyOn(realObject, "android.app.SharedPreferencesImpl$EditorImpl", "apply");
            // Flush QueuedWork. This resolves the deadlock of calling 'apply' followed by 'commit'.
            QueuedWork.waitToFinish();
        }
    }
}

