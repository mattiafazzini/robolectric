package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.M;
import static org.robolectric.shadow.api.Shadow.directlyOn;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import org.robolectric.annotation.HiddenApi;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(value = Icon.class, minSdk = M)
public class ShadowIcon {

    @RealObject
    private Icon realIcon;

    @HiddenApi
    @Implementation
    public int getType() {
        System.out.println("ShadowIcon#getType");
        return directlyOn(realIcon, Icon.class).getType();
    }

    @HiddenApi
    @Implementation
    public int getResId() {
        System.out.println("ShadowIcon#getResId");
        return directlyOn(realIcon, Icon.class).getResId();
    }

    @HiddenApi
    @Implementation
    public Bitmap getBitmap() {
        System.out.println("ShadowIcon#getBitmap");
        return directlyOn(realIcon, Icon.class).getBitmap();
    }

    @HiddenApi
    @Implementation
    public Uri getUri() {
        System.out.println("ShadowIcon#getUri");
        return directlyOn(realIcon, Icon.class).getUri();
    }

    @HiddenApi
    @Implementation
    public int getDataLength() {
        System.out.println("ShadowIcon#getDataLength");
        return directlyOn(realIcon, Icon.class).getDataLength();
    }

    @HiddenApi
    @Implementation
    public int getDataOffset() {
        System.out.println("ShadowIcon#getDataOffset");
        return directlyOn(realIcon, Icon.class).getDataOffset();
    }

    @HiddenApi
    @Implementation
    public byte[] getDataBytes() {
        System.out.println("ShadowIcon#getDataBytes");
        return directlyOn(realIcon, Icon.class).getDataBytes();
    }
}

