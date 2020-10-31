package org.robolectric.shadows;

import android.annotation.NonNull;
import android.util.ArraySet;
import android.view.accessibility.CaptioningManager;
import android.view.accessibility.CaptioningManager.CaptioningChangeListener;
import java.util.Locale;
import javax.annotation.Nullable;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Shadow of {@link android.view.accessibility.CaptioningManager}.
 */
@Implements(CaptioningManager.class)
public class ShadowCaptioningManager {

    private float fontScale = 1;

    private boolean isEnabled = false;

    @Nullable
    private Locale locale;

    private final ArraySet<CaptioningChangeListener> listeners = new ArraySet<>();

    /**
     * Returns 1.0 as default or the most recent value passed to {@link #setFontScale()}
     */
    @Implementation(minSdk = 19)
    protected float getFontScale() {
        System.out.println("ShadowCaptioningManager#getFontScale");
        return fontScale;
    }

    /**
     * Sets the value to be returned by {@link CaptioningManager#getFontScale()}
     */
    public void setFontScale(float fontScale) {
        this.fontScale = fontScale;
        for (CaptioningChangeListener captioningChangeListener : listeners) {
            captioningChangeListener.onFontScaleChanged(fontScale);
        }
    }

    /**
     * Returns false or the most recent value passed to {@link #setEnabled(boolean)}
     */
    @Implementation(minSdk = 19)
    protected boolean isEnabled() {
        System.out.println("ShadowCaptioningManager#isEnabled");
        return isEnabled;
    }

    /**
     * Sets the value to be returned by {@link CaptioningManager#isEnabled()}
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Implementation(minSdk = 19)
    protected void addCaptioningChangeListener(@NonNull CaptioningChangeListener listener) {
        System.out.println("ShadowCaptioningManager#addCaptioningChangeListener");
        listeners.add(listener);
    }

    @Implementation(minSdk = 19)
    protected void removeCaptioningChangeListener(@NonNull CaptioningChangeListener listener) {
        System.out.println("ShadowCaptioningManager#removeCaptioningChangeListener");
        listeners.remove(listener);
    }

    /**
     * Returns null or the most recent value passed to {@link #setLocale(Locale)}
     */
    @Implementation(minSdk = 19)
    @Nullable
    protected Locale getLocale() {
        System.out.println("ShadowCaptioningManager#getLocale");
        return locale;
    }

    /**
     * Sets the value to be returned by {@link CaptioningManager#getLocale()}
     */
    public void setLocale(@Nullable Locale locale) {
        this.locale = locale;
        for (CaptioningChangeListener captioningChangeListener : listeners) {
            captioningChangeListener.onLocaleChanged(locale);
        }
    }
}

