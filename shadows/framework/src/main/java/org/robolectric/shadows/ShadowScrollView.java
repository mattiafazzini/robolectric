package org.robolectric.shadows;

import android.widget.ScrollView;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ScrollView.class)
public class ShadowScrollView extends ShadowViewGroup {

    @Implementation
    protected void smoothScrollTo(int x, int y) {
        System.out.println("ShadowScrollView#smoothScrollTo");
        scrollTo(x, y);
    }

    @Implementation
    protected void smoothScrollBy(int x, int y) {
        System.out.println("ShadowScrollView#smoothScrollBy");
        scrollBy(x, y);
    }
}

