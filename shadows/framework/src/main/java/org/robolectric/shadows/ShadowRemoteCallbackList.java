package org.robolectric.shadows;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import java.util.HashMap;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(RemoteCallbackList.class)
public class ShadowRemoteCallbackList<E extends IInterface> {

    private final HashMap<IBinder, Callback> callbacks = new HashMap<>();

    private Object[] activeBroadcast;

    private int broadcastCount = -1;

    private boolean killed = false;

    private final class Callback implements IBinder.DeathRecipient {

        final E callback;

        final Object cookie;

        Callback(E callback, Object cookie) {
            this.callback = callback;
            this.cookie = cookie;
        }

        @Override
        public void binderDied() {
            synchronized (callbacks) {
                callbacks.remove(callback.asBinder());
            }
            onCallbackDied(callback, cookie);
        }
    }

    @Implementation
    protected boolean register(E callback) {
        System.out.println("ShadowRemoteCallbackList#register");
        return register(callback, null);
    }

    @Implementation
    protected boolean register(E callback, Object cookie) {
        System.out.println("ShadowRemoteCallbackList#register");
        synchronized (callbacks) {
            if (killed) {
                return false;
            }
            IBinder binder = callback.asBinder();
            try {
                Callback cb = new Callback(callback, cookie);
                binder.linkToDeath(cb, 0);
                callbacks.put(binder, cb);
                return true;
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    @Implementation
    protected boolean unregister(E callback) {
        System.out.println("ShadowRemoteCallbackList#unregister");
        synchronized (callbacks) {
            Callback cb = callbacks.remove(callback.asBinder());
            if (cb != null) {
                cb.callback.asBinder().unlinkToDeath(cb, 0);
                return true;
            }
            return false;
        }
    }

    @Implementation
    protected void kill() {
        System.out.println("ShadowRemoteCallbackList#kill");
        synchronized (callbacks) {
            for (Callback cb : callbacks.values()) {
                cb.callback.asBinder().unlinkToDeath(cb, 0);
            }
            callbacks.clear();
            killed = true;
        }
    }

    @Implementation
    protected void onCallbackDied(E callback) {
    }

    @Implementation
    protected void onCallbackDied(E callback, Object cookie) {
        System.out.println("ShadowRemoteCallbackList#onCallbackDied");
        onCallbackDied(callback);
    }

    @Implementation
    protected int beginBroadcast() {
        System.out.println("ShadowRemoteCallbackList#beginBroadcast");
        synchronized (callbacks) {
            if (broadcastCount > 0) {
                throw new IllegalStateException("beginBroadcast() called while already in a broadcast");
            }
            final int N = broadcastCount = callbacks.size();
            if (N <= 0) {
                return 0;
            }
            Object[] active = activeBroadcast;
            if (active == null || active.length < N) {
                activeBroadcast = active = new Object[N];
            }
            int i = 0;
            for (Callback cb : callbacks.values()) {
                active[i++] = cb;
            }
            return i;
        }
    }

    @Implementation
    protected E getBroadcastItem(int index) {
        System.out.println("ShadowRemoteCallbackList#getBroadcastItem");
        return ((Callback) activeBroadcast[index]).callback;
    }

    @Implementation
    protected Object getBroadcastCookie(int index) {
        System.out.println("ShadowRemoteCallbackList#getBroadcastCookie");
        return ((Callback) activeBroadcast[index]).cookie;
    }

    @Implementation
    protected void finishBroadcast() {
        System.out.println("ShadowRemoteCallbackList#finishBroadcast");
        if (broadcastCount < 0) {
            throw new IllegalStateException("finishBroadcast() called outside of a broadcast");
        }
        Object[] active = activeBroadcast;
        if (active != null) {
            final int N = broadcastCount;
            for (int i = 0; i < N; i++) {
                active[i] = null;
            }
        }
        broadcastCount = -1;
    }
}

