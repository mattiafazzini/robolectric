package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.P;
import android.telephony.euicc.EuiccManager;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Shadow for {@link EuiccManager}.
 */
@Implements(value = EuiccManager.class, minSdk = P)
public class ShadowEuiccManager {

    private boolean enabled;

    /**
     * Returns {@code false}, or the value specified by calling {@link #setIsEnabled}.
     */
    @Implementation
    protected boolean isEnabled() {
        System.out.println("ShadowEuiccManager#isEnabled");
        return enabled;
    }

    /**
     * Set the value to be returned by {@link EuiccManager#isEnabled}.
     */
    public void setIsEnabled(boolean isEnabled) {
        enabled = isEnabled;
    }
}

