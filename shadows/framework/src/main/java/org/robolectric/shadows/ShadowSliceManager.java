package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.P;
import android.app.slice.SliceManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;

/**
 * Shadow of {@link SliceManager}.
 */
@Implements(value = SliceManager.class, minSdk = P)
public class ShadowSliceManager {

    private static final Map<Integer, Collection<Uri>> packageUidsToPermissionGrantedSliceUris = new HashMap<>();

    private Context context;

    @Implementation
    protected void __constructor__(Context context, Handler handler) {
        System.out.println("ShadowSliceManager#__constructor__");
        this.context = context;
    }

    @Implementation
    protected synchronized List<Uri> getPinnedSlices() {
        System.out.println("ShadowSliceManager#getPinnedSlices");
        return ImmutableList.of();
    }

    @Implementation
    protected synchronized void grantSlicePermission(String toPackage, Uri uri) {
        System.out.println("ShadowSliceManager#grantSlicePermission");
        int packageUid = getUidForPackage(toPackage);
        Collection<Uri> uris = packageUidsToPermissionGrantedSliceUris.get(packageUid);
        if (uris == null) {
            uris = new ArrayList<>();
            packageUidsToPermissionGrantedSliceUris.put(packageUid, uris);
        }
        uris.add(uri);
    }

    @Implementation
    protected synchronized void revokeSlicePermission(String toPackage, Uri uri) {
        System.out.println("ShadowSliceManager#revokeSlicePermission");
        int packageUid = getUidForPackage(toPackage);
        Collection<Uri> uris = packageUidsToPermissionGrantedSliceUris.get(packageUid);
        if (uris != null) {
            uris.remove(uri);
            if (uris.isEmpty()) {
                packageUidsToPermissionGrantedSliceUris.remove(packageUid);
            }
        }
    }

    @Implementation
    protected synchronized int checkSlicePermission(Uri uri, int pid, int uid) {
        System.out.println("ShadowSliceManager#checkSlicePermission");
        if (uid == 0) {
            return PackageManager.PERMISSION_GRANTED;
        }
        Collection<Uri> uris = packageUidsToPermissionGrantedSliceUris.get(uid);
        if (uris != null && uris.contains(uri)) {
            return PackageManager.PERMISSION_GRANTED;
        }
        return PackageManager.PERMISSION_DENIED;
    }

    private int getUidForPackage(String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageUid(packageName, 0);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Resetter
    public static synchronized void reset() {
        packageUidsToPermissionGrantedSliceUris.clear();
    }
}

