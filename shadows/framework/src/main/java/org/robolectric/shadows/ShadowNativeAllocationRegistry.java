package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.N;
import libcore.util.NativeAllocationRegistry;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = NativeAllocationRegistry.class, minSdk = N, isInAndroidSdk = false, looseSignatures = true)
public class ShadowNativeAllocationRegistry {

    @Implementation
    protected Runnable registerNativeAllocation(Object referent, Object allocator) {
        System.out.println("ShadowNativeAllocationRegistry#registerNativeAllocation");
        return () -> {
        };
    }

    @Implementation
    protected Runnable registerNativeAllocation(Object referent, long nativePtr) {
        System.out.println("ShadowNativeAllocationRegistry#registerNativeAllocation");
        return () -> {
        };
    }
}

