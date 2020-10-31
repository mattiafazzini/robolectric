package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.KITKAT_WATCH;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static android.os.Build.VERSION_CODES.O_MR1;
import static android.os.Build.VERSION_CODES.P;
import static org.robolectric.annotation.TextLayoutMode.Mode.REALISTIC;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.graphics.Typeface;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.annotation.TextLayoutMode;
import org.robolectric.config.ConfigurationRegistry;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.ReflectionHelpers.ClassParameter;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(value = Paint.class, looseSignatures = true)
public class ShadowPaint {

    private int color;

    private Paint.Style style;

    private Paint.Cap cap;

    private Paint.Join join;

    private float width;

    private float shadowRadius;

    private float shadowDx;

    private float shadowDy;

    private int shadowColor;

    private Shader shader;

    private int alpha;

    private ColorFilter filter;

    private boolean antiAlias;

    private boolean dither;

    private int flags;

    private PathEffect pathEffect;

    @RealObject
    Paint paint;

    private Typeface typeface;

    private float textSize;

    private Paint.Align textAlign = Paint.Align.LEFT;

    @Implementation
    protected void __constructor__(Paint otherPaint) {
        System.out.println("ShadowPaint#__constructor__");
        ShadowPaint otherShadowPaint = Shadow.extract(otherPaint);
        this.color = otherShadowPaint.color;
        this.style = otherShadowPaint.style;
        this.cap = otherShadowPaint.cap;
        this.join = otherShadowPaint.join;
        this.width = otherShadowPaint.width;
        this.shadowRadius = otherShadowPaint.shadowRadius;
        this.shadowDx = otherShadowPaint.shadowDx;
        this.shadowDy = otherShadowPaint.shadowDy;
        this.shadowColor = otherShadowPaint.shadowColor;
        this.shader = otherShadowPaint.shader;
        this.alpha = otherShadowPaint.alpha;
        this.filter = otherShadowPaint.filter;
        this.antiAlias = otherShadowPaint.antiAlias;
        this.dither = otherShadowPaint.dither;
        this.flags = otherShadowPaint.flags;
        this.pathEffect = otherShadowPaint.pathEffect;
        Shadow.invokeConstructor(Paint.class, paint, ClassParameter.from(Paint.class, otherPaint));
    }

    @Implementation(minSdk = N)
    protected static long nInit() {
        System.out.println("ShadowPaint#nInit");
        return 1;
    }

    @Implementation
    protected int getFlags() {
        System.out.println("ShadowPaint#getFlags");
        return flags;
    }

    @Implementation
    protected void setFlags(int flags) {
        System.out.println("ShadowPaint#setFlags");
        this.flags = flags;
    }

    @Implementation
    protected Shader setShader(Shader shader) {
        System.out.println("ShadowPaint#setShader");
        this.shader = shader;
        return shader;
    }

    @Implementation
    protected int getAlpha() {
        System.out.println("ShadowPaint#getAlpha");
        return alpha;
    }

    @Implementation
    protected void setAlpha(int alpha) {
        System.out.println("ShadowPaint#setAlpha");
        this.alpha = alpha;
    }

    @Implementation
    protected Shader getShader() {
        System.out.println("ShadowPaint#getShader");
        return shader;
    }

    @Implementation
    protected void setColor(int color) {
        System.out.println("ShadowPaint#setColor");
        this.color = color;
    }

    @Implementation
    protected int getColor() {
        System.out.println("ShadowPaint#getColor");
        return color;
    }

    @Implementation
    protected void setStyle(Paint.Style style) {
        System.out.println("ShadowPaint#setStyle");
        this.style = style;
    }

    @Implementation
    protected Paint.Style getStyle() {
        System.out.println("ShadowPaint#getStyle");
        return style;
    }

    @Implementation
    protected void setStrokeCap(Paint.Cap cap) {
        System.out.println("ShadowPaint#setStrokeCap");
        this.cap = cap;
    }

    @Implementation
    protected Paint.Cap getStrokeCap() {
        System.out.println("ShadowPaint#getStrokeCap");
        return cap;
    }

    @Implementation
    protected void setStrokeJoin(Paint.Join join) {
        System.out.println("ShadowPaint#setStrokeJoin");
        this.join = join;
    }

    @Implementation
    protected Paint.Join getStrokeJoin() {
        System.out.println("ShadowPaint#getStrokeJoin");
        return join;
    }

    @Implementation
    protected void setStrokeWidth(float width) {
        System.out.println("ShadowPaint#setStrokeWidth");
        this.width = width;
    }

    @Implementation
    protected float getStrokeWidth() {
        System.out.println("ShadowPaint#getStrokeWidth");
        return width;
    }

    @Implementation
    protected void setShadowLayer(float radius, float dx, float dy, int color) {
        System.out.println("ShadowPaint#setShadowLayer");
        shadowRadius = radius;
        shadowDx = dx;
        shadowDy = dy;
        shadowColor = color;
    }

    @Implementation
    protected Typeface getTypeface() {
        System.out.println("ShadowPaint#getTypeface");
        return typeface;
    }

    @Implementation
    protected Typeface setTypeface(Typeface typeface) {
        System.out.println("ShadowPaint#setTypeface");
        this.typeface = typeface;
        return typeface;
    }

    @Implementation
    protected float getTextSize() {
        System.out.println("ShadowPaint#getTextSize");
        return textSize;
    }

    @Implementation
    protected void setTextSize(float textSize) {
        System.out.println("ShadowPaint#setTextSize");
        this.textSize = textSize;
    }

    @Implementation
    protected void setTextAlign(Paint.Align align) {
        System.out.println("ShadowPaint#setTextAlign");
        textAlign = align;
    }

    @Implementation
    protected Paint.Align getTextAlign() {
        System.out.println("ShadowPaint#getTextAlign");
        return textAlign;
    }

    /**
     * @return shadow radius (Paint related shadow, not Robolectric Shadow)
     */
    public float getShadowRadius() {
        return shadowRadius;
    }

    /**
     * @return shadow Dx (Paint related shadow, not Robolectric Shadow)
     */
    public float getShadowDx() {
        return shadowDx;
    }

    /**
     * @return shadow Dx (Paint related shadow, not Robolectric Shadow)
     */
    public float getShadowDy() {
        return shadowDy;
    }

    /**
     * @return shadow color (Paint related shadow, not Robolectric Shadow)
     */
    public int getShadowColor() {
        return shadowColor;
    }

    public Paint.Cap getCap() {
        return cap;
    }

    public Paint.Join getJoin() {
        return join;
    }

    public float getWidth() {
        return width;
    }

    @Implementation
    protected ColorFilter getColorFilter() {
        System.out.println("ShadowPaint#getColorFilter");
        return filter;
    }

    @Implementation
    protected ColorFilter setColorFilter(ColorFilter filter) {
        System.out.println("ShadowPaint#setColorFilter");
        this.filter = filter;
        return filter;
    }

    @Implementation
    protected void setAntiAlias(boolean antiAlias) {
        System.out.println("ShadowPaint#setAntiAlias");
        this.flags = (flags & ~Paint.ANTI_ALIAS_FLAG) | (antiAlias ? Paint.ANTI_ALIAS_FLAG : 0);
    }

    @Implementation
    protected void setDither(boolean dither) {
        System.out.println("ShadowPaint#setDither");
        this.dither = dither;
    }

    @Implementation
    protected final boolean isDither() {
        System.out.println("ShadowPaint#isDither");
        return dither;
    }

    @Implementation
    protected final boolean isAntiAlias() {
        System.out.println("ShadowPaint#isAntiAlias");
        return (flags & Paint.ANTI_ALIAS_FLAG) == Paint.ANTI_ALIAS_FLAG;
    }

    @Implementation
    protected PathEffect getPathEffect() {
        System.out.println("ShadowPaint#getPathEffect");
        return pathEffect;
    }

    @Implementation
    protected PathEffect setPathEffect(PathEffect effect) {
        System.out.println("ShadowPaint#setPathEffect");
        this.pathEffect = effect;
        return effect;
    }

    @Implementation
    protected float measureText(String text) {
        System.out.println("ShadowPaint#measureText");
        return text.length();
    }

    @Implementation
    protected float measureText(CharSequence text, int start, int end) {
        System.out.println("ShadowPaint#measureText");
        return end - start;
    }

    @Implementation
    protected float measureText(String text, int start, int end) {
        System.out.println("ShadowPaint#measureText");
        return end - start;
    }

    @Implementation
    protected float measureText(char[] text, int index, int count) {
        System.out.println("ShadowPaint#measureText");
        return count;
    }

    @Implementation(minSdk = P)
    protected static int nGetFontMetricsInt(long paintPtr, FontMetricsInt fmi) {
        System.out.println("ShadowPaint#nGetFontMetricsInt");
        if (ConfigurationRegistry.get(TextLayoutMode.Mode.class) == REALISTIC) {
            // TODO: hack, just set values to those we see on emulator
            int descent = 7;
            int ascent = -28;
            int leading = 0;
            if (fmi != null) {
                fmi.top = -32;
                fmi.ascent = ascent;
                fmi.descent = descent;
                fmi.bottom = 9;
                fmi.leading = leading;
            }
            return descent - ascent + leading;
        }
        return 0;
    }

    @Implementation(minSdk = O, maxSdk = O_MR1)
    protected static int nGetFontMetricsInt(long nativePaint, long nativeTypeface, FontMetricsInt fmi) {
        System.out.println("ShadowPaint#nGetFontMetricsInt");
        return nGetFontMetricsInt(nativePaint, fmi);
    }

    @Implementation(minSdk = N, maxSdk = N_MR1)
    protected int nGetFontMetricsInt(Object nativePaint, Object nativeTypeface, Object fmi) {
        System.out.println("ShadowPaint#nGetFontMetricsInt");
        return nGetFontMetricsInt((long) nativePaint, (FontMetricsInt) fmi);
    }

    @Implementation(maxSdk = M)
    protected int getFontMetricsInt(FontMetricsInt fmi) {
        System.out.println("ShadowPaint#getFontMetricsInt");
        return nGetFontMetricsInt(0, fmi);
    }

    @Implementation(minSdk = P)
    protected static float nGetRunAdvance(long paintPtr, char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset) {
        System.out.println("ShadowPaint#nGetRunAdvance");
        if (ConfigurationRegistry.get(TextLayoutMode.Mode.class) == REALISTIC) {
            // be consistent with measureText for measurements, and measure 1 pixel per char
            return end - start;
        }
        return 0f;
    }

    @Implementation(minSdk = N, maxSdk = O_MR1)
    protected static float nGetRunAdvance(long paintPtr, long typefacePtr, char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset) {
        System.out.println("ShadowPaint#nGetRunAdvance");
        return nGetRunAdvance(paintPtr, text, start, end, contextStart, contextEnd, isRtl, offset);
    }

    @Implementation(minSdk = M, maxSdk = M)
    protected static float native_getRunAdvance(long nativeObject, long nativeTypeface, char[] text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset) {
        System.out.println("ShadowPaint#native_getRunAdvance");
        return nGetRunAdvance(0, text, start, end, contextStart, contextEnd, isRtl, offset);
    }

    @Implementation(minSdk = KITKAT_WATCH, maxSdk = LOLLIPOP_MR1)
    protected static float native_getTextRunAdvances(long nativeObject, long nativeTypeface, char[] text, int index, int count, int contextIndex, int contextCount, boolean isRtl, float[] advances, int advancesIndex) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text, index, index + count, contextIndex, contextIndex + contextCount, isRtl, index);
    }

    @Implementation(minSdk = KITKAT_WATCH, maxSdk = LOLLIPOP_MR1)
    protected static float native_getTextRunAdvances(long nativeObject, long nativeTypeface, String text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float[] advances, int advancesIndex) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text.toCharArray(), start, end, contextStart, contextEnd, isRtl, 0);
    }

    @Implementation(minSdk = JELLY_BEAN_MR2, maxSdk = KITKAT)
    protected static float native_getTextRunAdvances(int nativeObject, char[] text, int index, int count, int contextIndex, int contextCount, int flags, float[] advances, int advancesIndex) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text, index, index + count, contextIndex, contextIndex + contextCount, false, index);
    }

    @Implementation(minSdk = JELLY_BEAN_MR2, maxSdk = KITKAT)
    protected static float native_getTextRunAdvances(int nativeObject, String text, int start, int end, int contextStart, int contextEnd, int flags, float[] advances, int advancesIndex) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text.toCharArray(), start, end, contextStart, contextEnd, false, 0);
    }

    @Implementation(maxSdk = JELLY_BEAN_MR1)
    protected static float native_getTextRunAdvances(int nativeObject, char[] text, int index, int count, int contextIndex, int contextCount, int flags, float[] advances, int advancesIndex, int reserved) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text, index, index + count, contextIndex, contextIndex + contextCount, false, index);
    }

    @Implementation(maxSdk = JELLY_BEAN_MR1)
    protected static float native_getTextRunAdvances(int nativeObject, String text, int start, int end, int contextStart, int contextEnd, int flags, float[] advances, int advancesIndex, int reserved) {
        System.out.println("ShadowPaint#native_getTextRunAdvances");
        return nGetRunAdvance(0, text.toCharArray(), start, end, contextStart, contextEnd, false, 0);
    }
}

