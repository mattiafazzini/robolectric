package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.os.Build.VERSION_CODES.M;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.robolectric.annotation.HiddenApi;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.util.ReflectionHelpers;

@Implements(value = TelecomManager.class, minSdk = LOLLIPOP)
public class ShadowTelecomManager {

    @RealObject
    private TelecomManager realObject;

    private PhoneAccountHandle simCallManager;

    private LinkedHashMap<PhoneAccountHandle, PhoneAccount> accounts = new LinkedHashMap();

    private List<CallRecord> incomingCalls = new ArrayList<>();

    private List<CallRecord> unknownCalls = new ArrayList<>();

    private String defaultDialerPackageName;

    private boolean isInCall;

    @Implementation
    protected PhoneAccountHandle getDefaultOutgoingPhoneAccount(String uriScheme) {
        System.out.println("ShadowTelecomManager#getDefaultOutgoingPhoneAccount");
        return null;
    }

    @Implementation
    @HiddenApi
    public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount() {
        System.out.println("ShadowTelecomManager#getUserSelectedOutgoingPhoneAccount");
        return null;
    }

    @Implementation
    @HiddenApi
    public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle accountHandle) {
    }

    @Implementation
    protected PhoneAccountHandle getSimCallManager() {
        System.out.println("ShadowTelecomManager#getSimCallManager");
        return simCallManager;
    }

    @Implementation(minSdk = M)
    @HiddenApi
    public PhoneAccountHandle getSimCallManager(int userId) {
        System.out.println("ShadowTelecomManager#getSimCallManager");
        return null;
    }

    @Implementation
    @HiddenApi
    public PhoneAccountHandle getConnectionManager() {
        System.out.println("ShadowTelecomManager#getConnectionManager");
        return this.getSimCallManager();
    }

    @Implementation
    @HiddenApi
    public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String uriScheme) {
        System.out.println("ShadowTelecomManager#getPhoneAccountsSupportingScheme");
        List<PhoneAccountHandle> result = new ArrayList<>();
        for (PhoneAccountHandle handle : accounts.keySet()) {
            PhoneAccount phoneAccount = accounts.get(handle);
            if (phoneAccount.getSupportedUriSchemes().contains(uriScheme)) {
                result.add(handle);
            }
        }
        return result;
    }

    @Implementation(minSdk = M)
    protected List<PhoneAccountHandle> getCallCapablePhoneAccounts() {
        System.out.println("ShadowTelecomManager#getCallCapablePhoneAccounts");
        return this.getCallCapablePhoneAccounts(false);
    }

    @Implementation(minSdk = M)
    @HiddenApi
    public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean includeDisabledAccounts) {
        System.out.println("ShadowTelecomManager#getCallCapablePhoneAccounts");
        List<PhoneAccountHandle> result = new ArrayList<>();
        for (PhoneAccountHandle handle : accounts.keySet()) {
            PhoneAccount phoneAccount = accounts.get(handle);
            if (!phoneAccount.isEnabled() && !includeDisabledAccounts) {
                continue;
            }
            result.add(handle);
        }
        return result;
    }

    @Implementation
    @HiddenApi
    public List<PhoneAccountHandle> getPhoneAccountsForPackage() {
        System.out.println("ShadowTelecomManager#getPhoneAccountsForPackage");
        Context context = ReflectionHelpers.getField(realObject, "mContext");
        List<PhoneAccountHandle> results = new ArrayList<>();
        for (PhoneAccountHandle handle : accounts.keySet()) {
            if (handle.getComponentName().getPackageName().equals(context.getPackageName())) {
                results.add(handle);
            }
        }
        return results;
    }

    @Implementation
    protected PhoneAccount getPhoneAccount(PhoneAccountHandle account) {
        System.out.println("ShadowTelecomManager#getPhoneAccount");
        return accounts.get(account);
    }

    @Implementation
    @HiddenApi
    public int getAllPhoneAccountsCount() {
        System.out.println("ShadowTelecomManager#getAllPhoneAccountsCount");
        return accounts.size();
    }

    @Implementation
    @HiddenApi
    public List<PhoneAccount> getAllPhoneAccounts() {
        System.out.println("ShadowTelecomManager#getAllPhoneAccounts");
        return ImmutableList.copyOf(accounts.values());
    }

    @Implementation
    @HiddenApi
    public List<PhoneAccountHandle> getAllPhoneAccountHandles() {
        System.out.println("ShadowTelecomManager#getAllPhoneAccountHandles");
        return ImmutableList.copyOf(accounts.keySet());
    }

    @Implementation
    protected void registerPhoneAccount(PhoneAccount account) {
        System.out.println("ShadowTelecomManager#registerPhoneAccount");
        accounts.put(account.getAccountHandle(), account);
    }

    @Implementation
    protected void unregisterPhoneAccount(PhoneAccountHandle accountHandle) {
        System.out.println("ShadowTelecomManager#unregisterPhoneAccount");
        accounts.remove(accountHandle);
    }

    /**
     * @deprecated
     */
    @Deprecated
    @Implementation
    @HiddenApi
    public void clearAccounts() {
        System.out.println("ShadowTelecomManager#clearAccounts");
        accounts.clear();
    }

    @Implementation(minSdk = LOLLIPOP_MR1)
    @HiddenApi
    public void clearAccountsForPackage(String packageName) {
        System.out.println("ShadowTelecomManager#clearAccountsForPackage");
        Set<PhoneAccountHandle> phoneAccountHandlesInPackage = new HashSet<>();
        for (PhoneAccountHandle handle : accounts.keySet()) {
            if (handle.getComponentName().getPackageName().equals(packageName)) {
                phoneAccountHandlesInPackage.add(handle);
            }
        }
        for (PhoneAccountHandle handle : phoneAccountHandlesInPackage) {
            accounts.remove(handle);
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    @Implementation
    @HiddenApi
    public ComponentName getDefaultPhoneApp() {
        System.out.println("ShadowTelecomManager#getDefaultPhoneApp");
        return null;
    }

    @Implementation(minSdk = M)
    protected String getDefaultDialerPackage() {
        System.out.println("ShadowTelecomManager#getDefaultDialerPackage");
        return defaultDialerPackageName;
    }

    @Implementation(minSdk = M)
    @HiddenApi
    public boolean setDefaultDialer(String packageName) {
        System.out.println("ShadowTelecomManager#setDefaultDialer");
        this.defaultDialerPackageName = packageName;
        return true;
    }

    @Implementation(minSdk = M)
    @HiddenApi
    public String getSystemDialerPackage() {
        System.out.println("ShadowTelecomManager#getSystemDialerPackage");
        return null;
    }

    @Implementation(minSdk = LOLLIPOP_MR1)
    protected boolean isVoiceMailNumber(PhoneAccountHandle accountHandle, String number) {
        System.out.println("ShadowTelecomManager#isVoiceMailNumber");
        return false;
    }

    @Implementation(minSdk = M)
    protected String getVoiceMailNumber(PhoneAccountHandle accountHandle) {
        System.out.println("ShadowTelecomManager#getVoiceMailNumber");
        return null;
    }

    @Implementation(minSdk = LOLLIPOP_MR1)
    protected String getLine1Number(PhoneAccountHandle accountHandle) {
        System.out.println("ShadowTelecomManager#getLine1Number");
        return null;
    }

    /**
     * Sets the return value for {@link TelecomManager#isInCall}.
     */
    public void setIsInCall(boolean isInCall) {
        this.isInCall = isInCall;
    }

    /**
     * Overrides behavior of {@link TelecomManager#isInCall} to return pre-set result.
     *
     * @return Value set by calling {@link ShadowTelecomManager#setIsInCall}. If setIsInCall has not
     *     previously been called, will return false.
     */
    @Implementation
    protected boolean isInCall() {
        System.out.println("ShadowTelecomManager#isInCall");
        return isInCall;
    }

    @Implementation
    @HiddenApi
    public int getCallState() {
        System.out.println("ShadowTelecomManager#getCallState");
        return 0;
    }

    @Implementation
    @HiddenApi
    public boolean isRinging() {
        System.out.println("ShadowTelecomManager#isRinging");
        for (CallRecord callRecord : incomingCalls) {
            if (callRecord.isRinging) {
                return true;
            }
        }
        for (CallRecord callRecord : unknownCalls) {
            if (callRecord.isRinging) {
                return true;
            }
        }
        return false;
    }

    @Implementation
    @HiddenApi
    public boolean endCall() {
        System.out.println("ShadowTelecomManager#endCall");
        return false;
    }

    @Implementation
    protected void acceptRingingCall() {
    }

    @Implementation
    protected void silenceRinger() {
        System.out.println("ShadowTelecomManager#silenceRinger");
        for (CallRecord callRecord : incomingCalls) {
            callRecord.isRinging = false;
        }
        for (CallRecord callRecord : unknownCalls) {
            callRecord.isRinging = false;
        }
    }

    @Implementation
    protected boolean isTtySupported() {
        System.out.println("ShadowTelecomManager#isTtySupported");
        return false;
    }

    @Implementation
    @HiddenApi
    public int getCurrentTtyMode() {
        System.out.println("ShadowTelecomManager#getCurrentTtyMode");
        return 0;
    }

    @Implementation
    protected void addNewIncomingCall(PhoneAccountHandle phoneAccount, Bundle extras) {
        System.out.println("ShadowTelecomManager#addNewIncomingCall");
        incomingCalls.add(new CallRecord(phoneAccount, extras));
    }

    public List<CallRecord> getAllIncomingCalls() {
        return incomingCalls;
    }

    @Implementation
    @HiddenApi
    public void addNewUnknownCall(PhoneAccountHandle phoneAccount, Bundle extras) {
        System.out.println("ShadowTelecomManager#addNewUnknownCall");
        unknownCalls.add(new CallRecord(phoneAccount, extras));
    }

    public List<CallRecord> getAllUnknownCalls() {
        return unknownCalls;
    }

    @Implementation
    protected boolean handleMmi(String dialString) {
        System.out.println("ShadowTelecomManager#handleMmi");
        return false;
    }

    @Implementation(minSdk = M)
    protected boolean handleMmi(String dialString, PhoneAccountHandle accountHandle) {
        System.out.println("ShadowTelecomManager#handleMmi");
        return false;
    }

    @Implementation(minSdk = LOLLIPOP_MR1)
    protected Uri getAdnUriForPhoneAccount(PhoneAccountHandle accountHandle) {
        System.out.println("ShadowTelecomManager#getAdnUriForPhoneAccount");
        return Uri.parse("content://icc/adn");
    }

    @Implementation
    protected void cancelMissedCallsNotification() {
    }

    @Implementation
    protected void showInCallScreen(boolean showDialpad) {
    }

    @Implementation(minSdk = M)
    protected void placeCall(Uri address, Bundle extras) {
    }

    @Implementation(minSdk = M)
    @HiddenApi
    public void enablePhoneAccount(PhoneAccountHandle handle, boolean isEnabled) {
    }

    public void setSimCallManager(PhoneAccountHandle simCallManager) {
        this.simCallManager = simCallManager;
    }

    public static class CallRecord {

        public final PhoneAccountHandle phoneAccount;

        public final Bundle bundle;

        private boolean isRinging = true;

        public CallRecord(PhoneAccountHandle phoneAccount, Bundle extras) {
            this.phoneAccount = phoneAccount;
            this.bundle = extras;
        }
    }
}

