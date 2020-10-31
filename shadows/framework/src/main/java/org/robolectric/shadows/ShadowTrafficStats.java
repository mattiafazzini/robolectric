package org.robolectric.shadows;

import android.net.TrafficStats;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(TrafficStats.class)
public class ShadowTrafficStats {

    @Implementation
    protected static void setThreadStatsTag(int tag) {
    }

    @Implementation
    protected static int getThreadStatsTag() {
        System.out.println("ShadowTrafficStats#getThreadStatsTag");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static void clearThreadStatsTag() {
    }

    @Implementation
    protected static void tagSocket(java.net.Socket socket) throws java.net.SocketException {
    }

    @Implementation
    protected static void untagSocket(java.net.Socket socket) throws java.net.SocketException {
    }

    @Implementation
    protected static void incrementOperationCount(int operationCount) {
    }

    @Implementation
    protected static void incrementOperationCount(int tag, int operationCount) {
    }

    @Implementation
    protected static long getMobileTxPackets() {
        System.out.println("ShadowTrafficStats#getMobileTxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getMobileRxPackets() {
        System.out.println("ShadowTrafficStats#getMobileRxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getMobileTxBytes() {
        System.out.println("ShadowTrafficStats#getMobileTxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getMobileRxBytes() {
        System.out.println("ShadowTrafficStats#getMobileRxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getTotalTxPackets() {
        System.out.println("ShadowTrafficStats#getTotalTxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getTotalRxPackets() {
        System.out.println("ShadowTrafficStats#getTotalRxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getTotalTxBytes() {
        System.out.println("ShadowTrafficStats#getTotalTxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getTotalRxBytes() {
        System.out.println("ShadowTrafficStats#getTotalRxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidTxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidRxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidRxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTxPackets(int i) {
        System.out.println("ShadowTrafficStats#getUidTxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidRxPackets(int i) {
        System.out.println("ShadowTrafficStats#getUidRxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTcpTxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidTcpTxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTcpRxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidTcpRxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidUdpTxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidUdpTxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidUdpRxBytes(int i) {
        System.out.println("ShadowTrafficStats#getUidUdpRxBytes");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTcpTxSegments(int i) {
        System.out.println("ShadowTrafficStats#getUidTcpTxSegments");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidTcpRxSegments(int i) {
        System.out.println("ShadowTrafficStats#getUidTcpRxSegments");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidUdpTxPackets(int i) {
        System.out.println("ShadowTrafficStats#getUidUdpTxPackets");
        return TrafficStats.UNSUPPORTED;
    }

    @Implementation
    protected static long getUidUdpRxPackets(int i) {
        System.out.println("ShadowTrafficStats#getUidUdpRxPackets");
        return TrafficStats.UNSUPPORTED;
    }
}

