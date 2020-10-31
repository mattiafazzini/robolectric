package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static org.robolectric.shadow.api.Shadow.directlyOn;
import android.content.res.Configuration;
import android.os.Build.VERSION_CODES;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.List;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.annotation.Resetter;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.ReflectionHelpers;
import org.robolectric.util.ReflectionHelpers.ClassParameter;

@Implements(value = WindowManagerImpl.class, isInAndroidSdk = false)
public class ShadowWindowManagerImpl extends ShadowWindowManager {

    private static Display defaultDisplayJB;

    /**
     * internal only
     */
    public static void configureDefaultDisplayForJBOnly(Configuration configuration, DisplayMetrics displayMetrics) {
        Class<?> arg2Type = ReflectionHelpers.loadClass(ShadowWindowManagerImpl.class.getClassLoader(), "android.view.CompatibilityInfoHolder");
        defaultDisplayJB = ReflectionHelpers.callConstructor(Display.class, ClassParameter.from(int.class, 0), ClassParameter.from(arg2Type, null));
        ShadowDisplay shadowDisplay = Shadow.extract(defaultDisplayJB);
        shadowDisplay.configureForJBOnly(configuration, displayMetrics);
    }

    @RealObject
    WindowManagerImpl realObject;

    private static Multimap<Display, View> views = ArrayListMultimap.create();

    @Implementation
    public void addView(View view, android.view.ViewGroup.LayoutParams layoutParams) {
        System.out.println("ShadowWindowManagerImpl#addView");
        views.put(realObject.getDefaultDisplay(), view);
        // views.add(view);
        directlyOn(realObject, WindowManagerImpl.class, "addView", ClassParameter.from(View.class, view), ClassParameter.from(ViewGroup.LayoutParams.class, layoutParams));
    }

    @Implementation
    public void removeView(View view) {
        System.out.println("ShadowWindowManagerImpl#removeView");
        views.remove(realObject.getDefaultDisplay(), view);
        directlyOn(realObject, WindowManagerImpl.class, "removeView", ClassParameter.from(View.class, view));
    }

    public List<View> getViews() {
        return ImmutableList.copyOf(views.get(realObject.getDefaultDisplay()));
    }

    @Implementation(maxSdk = JELLY_BEAN)
    public Display getDefaultDisplay() {
        System.out.println("ShadowWindowManagerImpl#getDefaultDisplay");
        if (RuntimeEnvironment.getApiLevel() > JELLY_BEAN) {
            return directlyOn(realObject, WindowManagerImpl.class).getDefaultDisplay();
        } else {
            return defaultDisplayJB;
        }
    }

    @Implements(className = "android.view.WindowManagerImpl$CompatModeWrapper", maxSdk = JELLY_BEAN)
    public static class ShadowCompatModeWrapper {

        @Implementation(maxSdk = JELLY_BEAN)
        protected Display getDefaultDisplay() {
            System.out.println("ShadowCompatModeWrapper#getDefaultDisplay");
            return defaultDisplayJB;
        }
    }

    @Resetter
    public static void reset() {
        defaultDisplayJB = null;
        views.clear();
        if (RuntimeEnvironment.getApiLevel() <= VERSION_CODES.JELLY_BEAN) {
            ReflectionHelpers.setStaticField(WindowManagerImpl.class, "sWindowManager", ReflectionHelpers.newInstance(WindowManagerImpl.class));
            HashMap windowManagers = ReflectionHelpers.getStaticField(WindowManagerImpl.class, "sCompatWindowManagers");
            windowManagers.clear();
        }
    }
}

