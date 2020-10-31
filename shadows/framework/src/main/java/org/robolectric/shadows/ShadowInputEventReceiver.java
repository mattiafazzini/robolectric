package org.robolectric.shadows;

import android.view.InputEventReceiver;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = InputEventReceiver.class, isInAndroidSdk = false)
public class ShadowInputEventReceiver {

    @Implementation
    @SuppressWarnings("robolectric.ShadowReturnTypeMismatch")
    public void consumeBatchedInputEvents(long frameTimeNanos) {
    }
}

