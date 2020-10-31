package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.util.ReflectionHelpers;

@Implements(ContentProviderClient.class)
public class ShadowContentProviderClient {

    @RealObject
    private ContentProviderClient realContentProviderClient;

    private boolean released;

    private ContentProvider provider;

    @Implementation(minSdk = JELLY_BEAN_MR1)
    protected Bundle call(String method, String arg, Bundle extras) throws RemoteException {
        System.out.println("ShadowContentProviderClient#call");
        return provider.call(method, arg, extras);
    }

    @Implementation
    protected String getType(Uri uri) throws RemoteException {
        System.out.println("ShadowContentProviderClient#getType");
        return provider.getType(uri);
    }

    @Implementation
    protected String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        System.out.println("ShadowContentProviderClient#getStreamTypes");
        return provider.getStreamTypes(uri, mimeTypeFilter);
    }

    @Implementation
    protected Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws RemoteException {
        System.out.println("ShadowContentProviderClient#query");
        return provider.query(url, projection, selection, selectionArgs, sortOrder);
    }

    @Implementation
    protected Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) throws RemoteException {
        System.out.println("ShadowContentProviderClient#query");
        return provider.query(url, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Implementation
    protected Uri insert(Uri url, ContentValues initialValues) throws RemoteException {
        System.out.println("ShadowContentProviderClient#insert");
        return provider.insert(url, initialValues);
    }

    @Implementation
    protected int bulkInsert(Uri url, ContentValues[] initialValues) throws RemoteException {
        System.out.println("ShadowContentProviderClient#bulkInsert");
        return provider.bulkInsert(url, initialValues);
    }

    @Implementation
    protected int delete(Uri url, String selection, String[] selectionArgs) throws RemoteException {
        System.out.println("ShadowContentProviderClient#delete");
        return provider.delete(url, selection, selectionArgs);
    }

    @Implementation
    protected int update(Uri url, ContentValues values, String selection, String[] selectionArgs) throws RemoteException {
        System.out.println("ShadowContentProviderClient#update");
        return provider.update(url, values, selection, selectionArgs);
    }

    @Implementation
    protected ParcelFileDescriptor openFile(Uri url, String mode) throws RemoteException, FileNotFoundException {
        System.out.println("ShadowContentProviderClient#openFile");
        return provider.openFile(url, mode);
    }

    @Implementation
    protected AssetFileDescriptor openAssetFile(Uri url, String mode) throws RemoteException, FileNotFoundException {
        System.out.println("ShadowContentProviderClient#openAssetFile");
        return provider.openAssetFile(url, mode);
    }

    @Implementation
    protected final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String mimeType, Bundle opts) throws RemoteException, FileNotFoundException {
        System.out.println("ShadowContentProviderClient#openTypedAssetFileDescriptor");
        return provider.openTypedAssetFile(uri, mimeType, opts);
    }

    @Implementation
    protected ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        System.out.println("ShadowContentProviderClient#applyBatch");
        return provider.applyBatch(operations);
    }

    @Implementation
    protected boolean release() {
        System.out.println("ShadowContentProviderClient#release");
        synchronized (this) {
            if (released) {
                throw new IllegalStateException("Already released");
            }
            released = true;
        }
        return true;
    }

    @Implementation
    protected ContentProvider getLocalContentProvider() {
        System.out.println("ShadowContentProviderClient#getLocalContentProvider");
        return ContentProvider.coerceToLocalContentProvider(provider.getIContentProvider());
    }

    public boolean isStable() {
        return ReflectionHelpers.getField(realContentProviderClient, "mStable");
    }

    public boolean isReleased() {
        return released;
    }

    void setContentProvider(ContentProvider provider) {
        this.provider = provider;
    }
}

