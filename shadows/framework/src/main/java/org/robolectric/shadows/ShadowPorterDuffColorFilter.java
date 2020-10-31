package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.P;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(PorterDuffColorFilter.class)
public class ShadowPorterDuffColorFilter {

    private int color;

    private PorterDuff.Mode mode;

    @Implementation
    protected void __constructor__(int color, PorterDuff.Mode mode) {
        System.out.println("ShadowPorterDuffColorFilter#__constructor__");
        this.color = color;
        this.mode = mode;
    }

    @Implementation(minSdk = LOLLIPOP, maxSdk = P)
    protected void setColor(int color) {
        System.out.println("ShadowPorterDuffColorFilter#setColor");
        this.color = color;
    }

    @Implementation(minSdk = LOLLIPOP, maxSdk = P)
    protected void setMode(PorterDuff.Mode mode) {
        System.out.println("ShadowPorterDuffColorFilter#setMode");
        this.mode = mode;
    }

    @Override
    @Implementation
    public boolean equals(Object object) {
        System.out.println("ShadowPorterDuffColorFilter#equals");
        if (this == object) {
            return true;
        }
        if (object == null || !(object instanceof PorterDuffColorFilter)) {
            return false;
        }
        final PorterDuffColorFilter other = (PorterDuffColorFilter) object;
        return (color == other.getColor() && mode.nativeInt == other.getMode().nativeInt);
    }

    @Override
    @Implementation
    public int hashCode() {
        System.out.println("ShadowPorterDuffColorFilter#hashCode");
        return 31 * mode.hashCode() + color;
    }

    /**
     * @return Returns the ARGB color used to tint the source pixels when this filter
     * is applied.
     */
    public int getColor() {
        return color;
    }

    /**
     * @return Returns the Porter-Duff mode used to composite this color filter's
     * color with the source pixel when this filter is applied.
     */
    public PorterDuff.Mode getMode() {
        return mode;
    }
}

