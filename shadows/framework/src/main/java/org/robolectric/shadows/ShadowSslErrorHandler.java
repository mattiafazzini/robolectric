package org.robolectric.shadows;

import android.webkit.SslErrorHandler;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(SslErrorHandler.class)
public class ShadowSslErrorHandler extends ShadowHandler {

    private boolean cancelCalled = false;

    private boolean proceedCalled = false;

    @Implementation
    protected void cancel() {
        System.out.println("ShadowSslErrorHandler#cancel");
        cancelCalled = true;
    }

    public boolean wasCancelCalled() {
        return cancelCalled;
    }

    @Implementation
    protected void proceed() {
        System.out.println("ShadowSslErrorHandler#proceed");
        proceedCalled = true;
    }

    public boolean wasProceedCalled() {
        return proceedCalled;
    }
}

