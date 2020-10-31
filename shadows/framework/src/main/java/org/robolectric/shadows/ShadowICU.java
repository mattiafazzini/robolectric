package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.KITKAT_WATCH;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.N;
import android.icu.util.ULocale;
import java.util.Locale;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = libcore.icu.ICU.class, isInAndroidSdk = false)
public class ShadowICU {

    @Implementation
    public static String addLikelySubtags(String locale) {
        System.out.println("ShadowICU#addLikelySubtags");
        if (RuntimeEnvironment.getApiLevel() >= N) {
            return ULocale.addLikelySubtags(ULocale.forLanguageTag(locale)).toLanguageTag();
        } else {
            // Return what is essentially the given locale, normalized by passing through the Locale
            // factory method.
            return Locale.forLanguageTag(locale).toLanguageTag();
        }
    }

    @Implementation(minSdk = LOLLIPOP)
    public static String getBestDateTimePattern(String skeleton, Locale locale) {
        System.out.println("ShadowICU#getBestDateTimePattern");
        switch(skeleton) {
            case "jmm":
                return getjmmPattern(locale);
            default:
                return skeleton;
        }
    }

    @Implementation(minSdk = JELLY_BEAN_MR2, maxSdk = KITKAT_WATCH)
    public static String getBestDateTimePattern(String skeleton, String locale) {
        System.out.println("ShadowICU#getBestDateTimePattern");
        return skeleton;
    }

    private static String getjmmPattern(Locale locale) {
        if (locale.equals(new Locale("pt", "BR")) || locale.equals(Locale.UK)) {
            return "H:mm";
        }
        return "h:mm a";
    }
}

