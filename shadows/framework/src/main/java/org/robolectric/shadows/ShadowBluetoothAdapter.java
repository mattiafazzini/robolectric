package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(BluetoothAdapter.class)
public class ShadowBluetoothAdapter {

    private static final int ADDRESS_LENGTH = 17;

    private Set<BluetoothDevice> bondedDevices = new HashSet<BluetoothDevice>();

    private Set<LeScanCallback> leScanCallbacks = new HashSet<LeScanCallback>();

    private boolean isDiscovering;

    private String address;

    private boolean enabled;

    private int state;

    private String name = "DefaultBluetoothDeviceName";

    private int scanMode = BluetoothAdapter.SCAN_MODE_NONE;

    private boolean isMultipleAdvertisementSupported = true;

    private Map<Integer, Integer> profileConnectionStateData = new HashMap<>();

    @Implementation
    protected static BluetoothAdapter getDefaultAdapter() {
        System.out.println("ShadowBluetoothAdapter#getDefaultAdapter");
        return (BluetoothAdapter) ShadowApplication.getInstance().getBluetoothAdapter();
    }

    @Implementation
    protected Set<BluetoothDevice> getBondedDevices() {
        System.out.println("ShadowBluetoothAdapter#getBondedDevices");
        return Collections.unmodifiableSet(bondedDevices);
    }

    public void setBondedDevices(Set<BluetoothDevice> bluetoothDevices) {
        bondedDevices = bluetoothDevices;
    }

    @Implementation
    protected BluetoothServerSocket listenUsingInsecureRfcommWithServiceRecord(String serviceName, UUID uuid) {
        System.out.println("ShadowBluetoothAdapter#listenUsingInsecureRfcommWithServiceRecord");
        return ShadowBluetoothServerSocket.newInstance(BluetoothSocket.TYPE_RFCOMM, /*auth=*/
        false, /*encrypt=*/
        false, new ParcelUuid(uuid));
    }

    @Implementation
    protected boolean startDiscovery() {
        System.out.println("ShadowBluetoothAdapter#startDiscovery");
        isDiscovering = true;
        return true;
    }

    @Implementation
    protected boolean cancelDiscovery() {
        System.out.println("ShadowBluetoothAdapter#cancelDiscovery");
        isDiscovering = false;
        return true;
    }

    @Implementation(minSdk = JELLY_BEAN_MR2)
    protected boolean startLeScan(LeScanCallback callback) {
        System.out.println("ShadowBluetoothAdapter#startLeScan");
        return startLeScan(null, callback);
    }

    @Implementation(minSdk = JELLY_BEAN_MR2)
    protected boolean startLeScan(UUID[] serviceUuids, LeScanCallback callback) {
        System.out.println("ShadowBluetoothAdapter#startLeScan");
        // Ignoring the serviceUuids param for now.
        leScanCallbacks.add(callback);
        return true;
    }

    @Implementation(minSdk = JELLY_BEAN_MR2)
    protected void stopLeScan(LeScanCallback callback) {
        System.out.println("ShadowBluetoothAdapter#stopLeScan");
        leScanCallbacks.remove(callback);
    }

    public Set<LeScanCallback> getLeScanCallbacks() {
        return Collections.unmodifiableSet(leScanCallbacks);
    }

    public LeScanCallback getSingleLeScanCallback() {
        if (leScanCallbacks.size() != 1) {
            throw new IllegalStateException("There are " + leScanCallbacks.size() + " callbacks");
        }
        return leScanCallbacks.iterator().next();
    }

    @Implementation
    protected boolean isDiscovering() {
        System.out.println("ShadowBluetoothAdapter#isDiscovering");
        return isDiscovering;
    }

    @Implementation
    protected boolean isEnabled() {
        System.out.println("ShadowBluetoothAdapter#isEnabled");
        return enabled;
    }

    @Implementation
    protected boolean enable() {
        System.out.println("ShadowBluetoothAdapter#enable");
        enabled = true;
        return true;
    }

    @Implementation
    protected boolean disable() {
        System.out.println("ShadowBluetoothAdapter#disable");
        enabled = false;
        return true;
    }

    @Implementation
    protected String getAddress() {
        System.out.println("ShadowBluetoothAdapter#getAddress");
        return this.address;
    }

    @Implementation
    protected int getState() {
        System.out.println("ShadowBluetoothAdapter#getState");
        return state;
    }

    @Implementation
    protected String getName() {
        System.out.println("ShadowBluetoothAdapter#getName");
        return name;
    }

    @Implementation
    protected boolean setName(String name) {
        System.out.println("ShadowBluetoothAdapter#setName");
        this.name = name;
        return true;
    }

    @Implementation
    protected boolean setScanMode(int scanMode) {
        System.out.println("ShadowBluetoothAdapter#setScanMode");
        if (scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE && scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE && scanMode != BluetoothAdapter.SCAN_MODE_NONE) {
            return false;
        }
        this.scanMode = scanMode;
        return true;
    }

    @Implementation
    protected int getScanMode() {
        System.out.println("ShadowBluetoothAdapter#getScanMode");
        return scanMode;
    }

    @Implementation(minSdk = LOLLIPOP)
    protected boolean isMultipleAdvertisementSupported() {
        System.out.println("ShadowBluetoothAdapter#isMultipleAdvertisementSupported");
        return isMultipleAdvertisementSupported;
    }

    /**
     * Validate a Bluetooth address, such as "00:43:A8:23:10:F0" Alphabetic characters must be
     * uppercase to be valid.
     *
     * @param address Bluetooth address as string
     * @return true if the address is valid, false otherwise
     */
    @Implementation
    protected static boolean checkBluetoothAddress(String address) {
        System.out.println("ShadowBluetoothAdapter#checkBluetoothAddress");
        if (address == null || address.length() != ADDRESS_LENGTH) {
            return false;
        }
        for (int i = 0; i < ADDRESS_LENGTH; i++) {
            char c = address.charAt(i);
            switch(i % 3) {
                case 0:
                case 1:
                    if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')) {
                        // hex character, OK
                        break;
                    }
                    return false;
                case 2:
                    if (c == ':') {
                        // OK
                        break;
                    }
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns the connection state for the given Bluetooth {@code profile}, defaulting to {@link
     * BluetoothProfile.STATE_DISCONNECTED} if the profile's connection state was never set.
     *
     * <p>Set a Bluetooth profile's connection state via {@link #setProfileConnectionState(int, int)}.
     */
    @Implementation
    protected int getProfileConnectionState(int profile) {
        System.out.println("ShadowBluetoothAdapter#getProfileConnectionState");
        Integer state = profileConnectionStateData.get(profile);
        if (state == null) {
            return BluetoothProfile.STATE_DISCONNECTED;
        }
        return state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setIsMultipleAdvertisementSupported(boolean supported) {
        isMultipleAdvertisementSupported = supported;
    }

    /**
     * Sets the connection state {@code state} for the given BLuetoothProfile {@code profile}
     */
    public void setProfileConnectionState(int profile, int state) {
        profileConnectionStateData.put(profile, state);
    }
}

