package org.robolectric.shadows;

import android.content.Context;
import android.webkit.WebViewDatabase;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;

@Implements(value = WebViewDatabase.class, callThroughByDefault = false)
public class ShadowWebViewDatabase {

    @Implementation
    protected static WebViewDatabase getInstance(Context ignored) {
        System.out.println("ShadowWebViewDatabase#getInstance");
        return Shadow.newInstanceOf(WebViewDatabase.class);
    }
}

