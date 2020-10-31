package org.robolectric.shadows;

import android.net.NetworkScoreManager;
import android.os.Build;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Provides testing APIs for {@link NetworkScoreManager}.
 */
@Implements(value = NetworkScoreManager.class, isInAndroidSdk = false, minSdk = Build.VERSION_CODES.LOLLIPOP)
public class ShadowNetworkScoreManager {

    private String activeScorerPackage;

    @Implementation
    public String getActiveScorerPackage() {
        System.out.println("ShadowNetworkScoreManager#getActiveScorerPackage");
        return activeScorerPackage;
    }

    @Implementation
    public boolean setActiveScorer(String packageName) {
        System.out.println("ShadowNetworkScoreManager#setActiveScorer");
        activeScorerPackage = packageName;
        return true;
    }
}

