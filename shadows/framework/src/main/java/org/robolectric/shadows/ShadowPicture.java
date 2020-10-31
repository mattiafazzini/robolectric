package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.KITKAT_WATCH;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Picture.class)
public class ShadowPicture {

    private int width;

    private int height;

    private static long nativePtr = 0;

    @Implementation(maxSdk = KITKAT)
    protected static int nativeConstructor(int nativeSrc) {
        System.out.println("ShadowPicture#nativeConstructor");
        // just return a non zero value, so it appears that native allocation was successful
        return (int) nativeConstructor((long) nativeSrc);
    }

    @Implementation(minSdk = KITKAT_WATCH)
    protected static long nativeConstructor(long nativeSrc) {
        System.out.println("ShadowPicture#nativeConstructor");
        // just return a non zero value, so it appears that native allocation was successful
        return ++nativePtr;
    }

    @Implementation
    protected void __constructor__(Picture src) {
        System.out.println("ShadowPicture#__constructor__");
        width = src.getWidth();
        height = src.getHeight();
    }

    @Implementation
    protected int getWidth() {
        System.out.println("ShadowPicture#getWidth");
        return width;
    }

    @Implementation
    protected int getHeight() {
        System.out.println("ShadowPicture#getHeight");
        return height;
    }

    @Implementation
    protected Canvas beginRecording(int width, int height) {
        System.out.println("ShadowPicture#beginRecording");
        this.width = width;
        this.height = height;
        return new Canvas(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
    }
}

