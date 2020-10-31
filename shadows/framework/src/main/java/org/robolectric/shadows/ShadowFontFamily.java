package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.O;
import android.graphics.FontFamily;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Shadow for {@link FontFamily}
 */
@Implements(value = FontFamily.class, minSdk = LOLLIPOP, isInAndroidSdk = false)
public class ShadowFontFamily {

    @Implementation(minSdk = O)
    protected static long nInitBuilder(String lang, int variant) {
        System.out.println("ShadowFontFamily#nInitBuilder");
        return 1;
    }

    @Implementation(minSdk = O)
    protected void abortCreation() {
    }
}

