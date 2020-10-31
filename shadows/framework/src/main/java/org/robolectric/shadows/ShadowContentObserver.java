package org.robolectric.shadows;

import android.database.ContentObserver;
import android.net.Uri;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@Implements(ContentObserver.class)
public class ShadowContentObserver {

    @RealObject
    private ContentObserver realObserver;

    @Implementation
    protected void dispatchChange(boolean selfChange, Uri uri) {
        System.out.println("ShadowContentObserver#dispatchChange");
        realObserver.onChange(selfChange, uri);
    }

    @Implementation
    protected void dispatchChange(boolean selfChange) {
        System.out.println("ShadowContentObserver#dispatchChange");
        realObserver.onChange(selfChange);
    }
}

