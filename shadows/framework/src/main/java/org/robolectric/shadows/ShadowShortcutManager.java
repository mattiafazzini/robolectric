package org.robolectric.shadows;

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 */
@Implements(value = ShortcutManager.class, minSdk = Build.VERSION_CODES.N_MR1)
public class ShadowShortcutManager {

    private static final int MAX_ICON_DIMENSION = 128;

    private final Map<String, ShortcutInfo> dynamicShortcuts = new HashMap<>();

    private final Map<String, ShortcutInfo> activePinnedShortcuts = new HashMap<>();

    private final Map<String, ShortcutInfo> disabledPinnedShortcuts = new HashMap<>();

    private List<ShortcutInfo> manifestShortcuts = ImmutableList.of();

    private boolean isRequestPinShortcutSupported = true;

    private int maxShortcutCountPerActivity = 16;

    @Implementation
    protected boolean addDynamicShortcuts(List<ShortcutInfo> shortcutInfoList) {
        System.out.println("ShadowShortcutManager#addDynamicShortcuts");
        for (ShortcutInfo shortcutInfo : shortcutInfoList) {
            if (activePinnedShortcuts.containsKey(shortcutInfo.getId())) {
                ShortcutInfo previousShortcut = activePinnedShortcuts.get(shortcutInfo.getId());
                if (!previousShortcut.isImmutable()) {
                    activePinnedShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            } else if (disabledPinnedShortcuts.containsKey(shortcutInfo.getId())) {
                ShortcutInfo previousShortcut = disabledPinnedShortcuts.get(shortcutInfo.getId());
                if (!previousShortcut.isImmutable()) {
                    disabledPinnedShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            } else if (dynamicShortcuts.containsKey(shortcutInfo.getId())) {
                ShortcutInfo previousShortcut = dynamicShortcuts.get(shortcutInfo.getId());
                if (!previousShortcut.isImmutable()) {
                    dynamicShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            } else {
                dynamicShortcuts.put(shortcutInfo.getId(), shortcutInfo);
            }
        }
        return true;
    }

    @Implementation(minSdk = Build.VERSION_CODES.O)
    protected Intent createShortcutResultIntent(ShortcutInfo shortcut) {
        System.out.println("ShadowShortcutManager#createShortcutResultIntent");
        if (disabledPinnedShortcuts.containsKey(shortcut.getId())) {
            throw new IllegalArgumentException();
        }
        return new Intent();
    }

    @Implementation
    protected void disableShortcuts(List<String> shortcutIds) {
        System.out.println("ShadowShortcutManager#disableShortcuts");
        disableShortcuts(shortcutIds, "Shortcut is disabled.");
    }

    @Implementation
    protected void disableShortcuts(List<String> shortcutIds, CharSequence unused) {
        System.out.println("ShadowShortcutManager#disableShortcuts");
        for (String shortcutId : shortcutIds) {
            ShortcutInfo shortcut = activePinnedShortcuts.remove(shortcutId);
            if (shortcut != null) {
                disabledPinnedShortcuts.put(shortcutId, shortcut);
            }
        }
    }

    @Implementation
    protected void enableShortcuts(List<String> shortcutIds) {
        System.out.println("ShadowShortcutManager#enableShortcuts");
        for (String shortcutId : shortcutIds) {
            ShortcutInfo shortcut = disabledPinnedShortcuts.remove(shortcutId);
            if (shortcut != null) {
                activePinnedShortcuts.put(shortcutId, shortcut);
            }
        }
    }

    @Implementation
    protected List<ShortcutInfo> getDynamicShortcuts() {
        System.out.println("ShadowShortcutManager#getDynamicShortcuts");
        return ImmutableList.copyOf(dynamicShortcuts.values());
    }

    @Implementation
    protected int getIconMaxHeight() {
        System.out.println("ShadowShortcutManager#getIconMaxHeight");
        return MAX_ICON_DIMENSION;
    }

    @Implementation
    protected int getIconMaxWidth() {
        System.out.println("ShadowShortcutManager#getIconMaxWidth");
        return MAX_ICON_DIMENSION;
    }

    @Implementation
    protected List<ShortcutInfo> getManifestShortcuts() {
        System.out.println("ShadowShortcutManager#getManifestShortcuts");
        return manifestShortcuts;
    }

    /**
     * Sets the value returned by {@link #getManifestShortcuts()}.
     */
    public void setManifestShortcuts(List<ShortcutInfo> manifestShortcuts) {
        this.manifestShortcuts = manifestShortcuts;
    }

    @Implementation
    protected int getMaxShortcutCountPerActivity() {
        System.out.println("ShadowShortcutManager#getMaxShortcutCountPerActivity");
        return maxShortcutCountPerActivity;
    }

    /**
     * Sets the value returned by {@link #getMaxShortcutCountPerActivity()} .
     */
    public void setMaxShortcutCountPerActivity(int value) {
        maxShortcutCountPerActivity = value;
    }

    @Implementation
    protected List<ShortcutInfo> getPinnedShortcuts() {
        System.out.println("ShadowShortcutManager#getPinnedShortcuts");
        ImmutableList.Builder<ShortcutInfo> pinnedShortcuts = ImmutableList.builder();
        pinnedShortcuts.addAll(activePinnedShortcuts.values());
        pinnedShortcuts.addAll(disabledPinnedShortcuts.values());
        return pinnedShortcuts.build();
    }

    @Implementation
    protected boolean isRateLimitingActive() {
        System.out.println("ShadowShortcutManager#isRateLimitingActive");
        return false;
    }

    @Implementation(minSdk = Build.VERSION_CODES.O)
    protected boolean isRequestPinShortcutSupported() {
        System.out.println("ShadowShortcutManager#isRequestPinShortcutSupported");
        return isRequestPinShortcutSupported;
    }

    public void setIsRequestPinShortcutSupported(boolean isRequestPinShortcutSupported) {
        this.isRequestPinShortcutSupported = isRequestPinShortcutSupported;
    }

    @Implementation
    protected void removeAllDynamicShortcuts() {
        System.out.println("ShadowShortcutManager#removeAllDynamicShortcuts");
        dynamicShortcuts.clear();
    }

    @Implementation
    protected void removeDynamicShortcuts(List<String> shortcutIds) {
        System.out.println("ShadowShortcutManager#removeDynamicShortcuts");
        for (String shortcutId : shortcutIds) {
            dynamicShortcuts.remove(shortcutId);
        }
    }

    @Implementation
    protected void reportShortcutUsed(String shortcutId) {
    }

    @Implementation(minSdk = Build.VERSION_CODES.O)
    protected boolean requestPinShortcut(ShortcutInfo shortcut, IntentSender resultIntent) {
        System.out.println("ShadowShortcutManager#requestPinShortcut");
        if (disabledPinnedShortcuts.containsKey(shortcut.getId())) {
            throw new IllegalArgumentException("Shortcut with ID [" + shortcut.getId() + "] already exists and is disabled.");
        }
        if (dynamicShortcuts.containsKey(shortcut.getId())) {
            activePinnedShortcuts.put(shortcut.getId(), dynamicShortcuts.remove(shortcut.getId()));
        } else {
            activePinnedShortcuts.put(shortcut.getId(), shortcut);
        }
        if (resultIntent != null) {
            try {
                resultIntent.sendIntent(RuntimeEnvironment.application, 0, null, null, null);
            } catch (SendIntentException e) {
                throw new IllegalStateException();
            }
        }
        return true;
    }

    @Implementation
    protected boolean setDynamicShortcuts(List<ShortcutInfo> shortcutInfoList) {
        System.out.println("ShadowShortcutManager#setDynamicShortcuts");
        dynamicShortcuts.clear();
        return addDynamicShortcuts(shortcutInfoList);
    }

    @Implementation
    protected boolean updateShortcuts(List<ShortcutInfo> shortcutInfoList) {
        System.out.println("ShadowShortcutManager#updateShortcuts");
        List<ShortcutInfo> existingShortcutsToUpdate = new ArrayList<>();
        for (ShortcutInfo shortcutInfo : shortcutInfoList) {
            if (dynamicShortcuts.containsKey(shortcutInfo.getId()) || activePinnedShortcuts.containsKey(shortcutInfo.getId()) || disabledPinnedShortcuts.containsKey(shortcutInfo.getId())) {
                existingShortcutsToUpdate.add(shortcutInfo);
            }
        }
        return addDynamicShortcuts(existingShortcutsToUpdate);
    }
}

