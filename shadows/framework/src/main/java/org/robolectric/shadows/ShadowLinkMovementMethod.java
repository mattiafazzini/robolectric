package org.robolectric.shadows;

import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(LinkMovementMethod.class)
public class ShadowLinkMovementMethod {

    @Implementation
    protected static MovementMethod getInstance() {
        System.out.println("ShadowLinkMovementMethod#getInstance");
        return new LinkMovementMethod();
    }
}

