package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.O;
import static org.robolectric.shadow.api.Shadow.directlyOn;
import android.annotation.Nullable;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.annotation.Resetter;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.ReflectionHelpers;
import org.robolectric.util.ReflectionHelpers.ClassParameter;

@Implements(className = ShadowContextImpl.CLASS_NAME)
public class ShadowContextImpl {

    public static final String CLASS_NAME = "android.app.ContextImpl";

    private ContentResolver contentResolver;

    @RealObject
    private Context realContextImpl;

    private Map<String, Object> systemServices = new HashMap<String, Object>();

    private final Set<String> removedSystemServices = new HashSet<>();

    @Implementation
    @Nullable
    protected Object getSystemService(String name) {
        System.out.println("ShadowContextImpl#getSystemService");
        if (removedSystemServices.contains(name)) {
            return null;
        }
        if (!systemServices.containsKey(name)) {
            return directlyOn(realContextImpl, ShadowContextImpl.CLASS_NAME, "getSystemService", ClassParameter.from(String.class, name));
        }
        return systemServices.get(name);
    }

    public void setSystemService(String key, Object service) {
        systemServices.put(key, service);
    }

    /**
     * Makes {@link #getSystemService(String)} return {@code null} for the given system service name,
     * mimicking a device that doesn't have that system service.
     */
    public void removeSystemService(String name) {
        removedSystemServices.add(name);
    }

    @Implementation
    protected void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        System.out.println("ShadowContextImpl#startIntentSender");
        intent.sendIntent(realContextImpl, 0, fillInIntent, null, null, null);
    }

    @Implementation
    protected ClassLoader getClassLoader() {
        System.out.println("ShadowContextImpl#getClassLoader");
        return this.getClass().getClassLoader();
    }

    @Implementation
    protected int checkCallingPermission(String permission) {
        System.out.println("ShadowContextImpl#checkCallingPermission");
        return checkPermission(permission, -1, -1);
    }

    @Implementation
    protected int checkCallingOrSelfPermission(String permission) {
        System.out.println("ShadowContextImpl#checkCallingOrSelfPermission");
        return checkPermission(permission, -1, -1);
    }

    @Implementation
    protected ContentResolver getContentResolver() {
        System.out.println("ShadowContextImpl#getContentResolver");
        if (contentResolver == null) {
            contentResolver = new ContentResolver(realContextImpl) {

                @Override
                protected IContentProvider acquireProvider(Context c, String name) {
                    return null;
                }

                @Override
                public boolean releaseProvider(IContentProvider icp) {
                    return false;
                }

                @Override
                protected IContentProvider acquireUnstableProvider(Context c, String name) {
                    return null;
                }

                @Override
                public boolean releaseUnstableProvider(IContentProvider icp) {
                    return false;
                }

                @Override
                public void unstableProviderDied(IContentProvider icp) {
                }
            };
        }
        return contentResolver;
    }

    @Implementation
    protected void sendBroadcast(Intent intent) {
        System.out.println("ShadowContextImpl#sendBroadcast");
        getShadowInstrumentation().sendBroadcastWithPermission(intent, null, realContextImpl);
    }

    @Implementation
    protected void sendBroadcast(Intent intent, String receiverPermission) {
        System.out.println("ShadowContextImpl#sendBroadcast");
        getShadowInstrumentation().sendBroadcastWithPermission(intent, receiverPermission, realContextImpl);
    }

    @Implementation
    protected void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        System.out.println("ShadowContextImpl#sendOrderedBroadcast");
        getShadowInstrumentation().sendOrderedBroadcastWithPermission(intent, receiverPermission, realContextImpl);
    }

    @Implementation
    protected void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        System.out.println("ShadowContextImpl#sendOrderedBroadcast");
        getShadowInstrumentation().sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras, realContextImpl);
    }

    @Implementation
    protected void sendStickyBroadcast(Intent intent) {
        System.out.println("ShadowContextImpl#sendStickyBroadcast");
        getShadowInstrumentation().sendStickyBroadcast(intent, realContextImpl);
    }

    @Implementation
    protected int checkPermission(String permission, int pid, int uid) {
        System.out.println("ShadowContextImpl#checkPermission");
        return getShadowInstrumentation().checkPermission(permission, pid, uid);
    }

    @Implementation
    protected Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        System.out.println("ShadowContextImpl#registerReceiver");
        return getShadowInstrumentation().registerReceiver(receiver, filter, realContextImpl);
    }

    @Implementation
    protected Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        System.out.println("ShadowContextImpl#registerReceiver");
        return getShadowInstrumentation().registerReceiver(receiver, filter, broadcastPermission, scheduler, realContextImpl);
    }

    @Implementation(minSdk = JELLY_BEAN_MR1)
    protected Intent registerReceiverAsUser(BroadcastReceiver receiver, UserHandle user, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        System.out.println("ShadowContextImpl#registerReceiverAsUser");
        return getShadowInstrumentation().registerReceiverWithContext(receiver, filter, broadcastPermission, scheduler, realContextImpl);
    }

    @Implementation
    protected void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        System.out.println("ShadowContextImpl#unregisterReceiver");
        getShadowInstrumentation().unregisterReceiver(broadcastReceiver);
    }

    @Implementation
    protected ComponentName startService(Intent service) {
        System.out.println("ShadowContextImpl#startService");
        return getShadowInstrumentation().startService(service);
    }

    @Implementation(minSdk = O)
    protected ComponentName startForegroundService(Intent service) {
        System.out.println("ShadowContextImpl#startForegroundService");
        return getShadowInstrumentation().startService(service);
    }

    @Implementation
    protected boolean stopService(Intent name) {
        System.out.println("ShadowContextImpl#stopService");
        return getShadowInstrumentation().stopService(name);
    }

    @Implementation
    protected boolean bindService(Intent intent, final ServiceConnection serviceConnection, int i) {
        System.out.println("ShadowContextImpl#bindService");
        return getShadowInstrumentation().bindService(intent, serviceConnection, i);
    }

    /**
     * Binds to a service but ignores the given UserHandle.
     */
    @Implementation(minSdk = LOLLIPOP)
    protected boolean bindServiceAsUser(Intent intent, final ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        System.out.println("ShadowContextImpl#bindServiceAsUser");
        return getShadowInstrumentation().bindService(intent, serviceConnection, i);
    }

    @Implementation
    protected void unbindService(final ServiceConnection serviceConnection) {
        System.out.println("ShadowContextImpl#unbindService");
        getShadowInstrumentation().unbindService(serviceConnection);
    }

    @Implementation(minSdk = JELLY_BEAN_MR1)
    protected int getUserId() {
        System.out.println("ShadowContextImpl#getUserId");
        return 0;
    }

    @Implementation
    protected File getExternalCacheDir() {
        System.out.println("ShadowContextImpl#getExternalCacheDir");
        return Environment.getExternalStorageDirectory();
    }

    @Implementation(maxSdk = JELLY_BEAN_MR2)
    protected File getExternalFilesDir(String type) {
        System.out.println("ShadowContextImpl#getExternalFilesDir");
        return Environment.getExternalStoragePublicDirectory(type);
    }

    @Implementation(minSdk = KITKAT)
    protected File[] getExternalFilesDirs(String type) {
        System.out.println("ShadowContextImpl#getExternalFilesDirs");
        return new File[] { Environment.getExternalStoragePublicDirectory(type) };
    }

    @Resetter
    public static void reset() {
        String prefsCacheFieldName = RuntimeEnvironment.getApiLevel() >= N ? "sSharedPrefsCache" : "sSharedPrefs";
        Object prefsDefaultValue = RuntimeEnvironment.getApiLevel() >= KITKAT ? null : new HashMap<>();
        Class<?> contextImplClass = ReflectionHelpers.loadClass(ShadowContextImpl.class.getClassLoader(), "android.app.ContextImpl");
        ReflectionHelpers.setStaticField(contextImplClass, prefsCacheFieldName, prefsDefaultValue);
        if (RuntimeEnvironment.getApiLevel() <= VERSION_CODES.LOLLIPOP_MR1) {
            HashMap<String, Object> fetchers = ReflectionHelpers.getStaticField(contextImplClass, "SYSTEM_SERVICE_MAP");
            Class staticServiceFetcherClass = ReflectionHelpers.loadClass(ShadowContextImpl.class.getClassLoader(), "android.app.ContextImpl$StaticServiceFetcher");
            for (Object o : fetchers.values()) {
                if (staticServiceFetcherClass.isInstance(o)) {
                    ReflectionHelpers.setField(staticServiceFetcherClass, o, "mCachedInstance", null);
                }
            }
            if (RuntimeEnvironment.getApiLevel() >= KITKAT) {
                Class serviceFetcherClass = ReflectionHelpers.loadClass(ShadowContextImpl.class.getClassLoader(), "android.app.ContextImpl$ServiceFetcher");
                Object windowServiceFetcher = fetchers.get(Context.WINDOW_SERVICE);
                ReflectionHelpers.setField(windowServiceFetcher.getClass(), windowServiceFetcher, "mDefaultDisplay", null);
            }
        }
    }

    private ShadowInstrumentation getShadowInstrumentation() {
        ActivityThread activityThread = (ActivityThread) RuntimeEnvironment.getActivityThread();
        return Shadow.extract(activityThread.getInstrumentation());
    }
}

