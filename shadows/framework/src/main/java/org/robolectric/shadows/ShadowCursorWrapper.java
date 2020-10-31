package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.M;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(CursorWrapper.class)
public class ShadowCursorWrapper implements Cursor {

    private Cursor wrappedCursor;

    @Implementation
    protected void __constructor__(Cursor c) {
        System.out.println("ShadowCursorWrapper#__constructor__");
        wrappedCursor = c;
    }

    @Override
    @Implementation
    public int getCount() {
        System.out.println("ShadowCursorWrapper#getCount");
        return wrappedCursor.getCount();
    }

    @Override
    @Implementation
    public int getPosition() {
        System.out.println("ShadowCursorWrapper#getPosition");
        return wrappedCursor.getPosition();
    }

    @Override
    @Implementation
    public boolean move(int i) {
        System.out.println("ShadowCursorWrapper#move");
        return wrappedCursor.move(i);
    }

    @Override
    @Implementation
    public boolean moveToPosition(int i) {
        System.out.println("ShadowCursorWrapper#moveToPosition");
        return wrappedCursor.moveToPosition(i);
    }

    @Override
    @Implementation
    public boolean moveToFirst() {
        System.out.println("ShadowCursorWrapper#moveToFirst");
        return wrappedCursor.moveToFirst();
    }

    @Override
    @Implementation
    public boolean moveToLast() {
        System.out.println("ShadowCursorWrapper#moveToLast");
        return wrappedCursor.moveToLast();
    }

    @Override
    @Implementation
    public boolean moveToNext() {
        System.out.println("ShadowCursorWrapper#moveToNext");
        return wrappedCursor.moveToNext();
    }

    @Override
    @Implementation
    public boolean moveToPrevious() {
        System.out.println("ShadowCursorWrapper#moveToPrevious");
        return wrappedCursor.moveToPrevious();
    }

    @Override
    @Implementation
    public boolean isFirst() {
        System.out.println("ShadowCursorWrapper#isFirst");
        return wrappedCursor.isFirst();
    }

    @Override
    @Implementation
    public boolean isLast() {
        System.out.println("ShadowCursorWrapper#isLast");
        return wrappedCursor.isLast();
    }

    @Override
    @Implementation
    public boolean isBeforeFirst() {
        System.out.println("ShadowCursorWrapper#isBeforeFirst");
        return wrappedCursor.isBeforeFirst();
    }

    @Override
    @Implementation
    public boolean isAfterLast() {
        System.out.println("ShadowCursorWrapper#isAfterLast");
        return wrappedCursor.isAfterLast();
    }

    @Override
    @Implementation
    public int getColumnIndex(String s) {
        System.out.println("ShadowCursorWrapper#getColumnIndex");
        return wrappedCursor.getColumnIndex(s);
    }

    @Override
    @Implementation
    public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
        System.out.println("ShadowCursorWrapper#getColumnIndexOrThrow");
        return wrappedCursor.getColumnIndexOrThrow(s);
    }

    @Override
    @Implementation
    public String getColumnName(int i) {
        System.out.println("ShadowCursorWrapper#getColumnName");
        return wrappedCursor.getColumnName(i);
    }

    @Override
    @Implementation
    public String[] getColumnNames() {
        System.out.println("ShadowCursorWrapper#getColumnNames");
        return wrappedCursor.getColumnNames();
    }

    @Override
    @Implementation
    public int getColumnCount() {
        System.out.println("ShadowCursorWrapper#getColumnCount");
        return wrappedCursor.getColumnCount();
    }

    @Override
    @Implementation
    public byte[] getBlob(int i) {
        System.out.println("ShadowCursorWrapper#getBlob");
        return wrappedCursor.getBlob(i);
    }

    @Override
    @Implementation
    public String getString(int i) {
        System.out.println("ShadowCursorWrapper#getString");
        return wrappedCursor.getString(i);
    }

    @Override
    @Implementation
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        System.out.println("ShadowCursorWrapper#copyStringToBuffer");
        wrappedCursor.copyStringToBuffer(i, charArrayBuffer);
    }

    @Override
    @Implementation
    public short getShort(int i) {
        System.out.println("ShadowCursorWrapper#getShort");
        return wrappedCursor.getShort(i);
    }

    @Override
    @Implementation
    public int getInt(int i) {
        System.out.println("ShadowCursorWrapper#getInt");
        return wrappedCursor.getInt(i);
    }

    @Override
    @Implementation
    public long getLong(int i) {
        System.out.println("ShadowCursorWrapper#getLong");
        return wrappedCursor.getLong(i);
    }

    @Override
    @Implementation
    public float getFloat(int i) {
        System.out.println("ShadowCursorWrapper#getFloat");
        return wrappedCursor.getFloat(i);
    }

    @Override
    @Implementation
    public double getDouble(int i) {
        System.out.println("ShadowCursorWrapper#getDouble");
        return wrappedCursor.getDouble(i);
    }

    @Override
    @Implementation
    public boolean isNull(int i) {
        System.out.println("ShadowCursorWrapper#isNull");
        return wrappedCursor.isNull(i);
    }

    @Override
    @Implementation
    public void deactivate() {
        System.out.println("ShadowCursorWrapper#deactivate");
        wrappedCursor.deactivate();
    }

    @Override
    @Implementation
    public boolean requery() {
        System.out.println("ShadowCursorWrapper#requery");
        return wrappedCursor.requery();
    }

    @Override
    @Implementation
    public void close() {
        System.out.println("ShadowCursorWrapper#close");
        wrappedCursor.close();
    }

    @Override
    @Implementation
    public boolean isClosed() {
        System.out.println("ShadowCursorWrapper#isClosed");
        return wrappedCursor.isClosed();
    }

    @Override
    @Implementation
    public void registerContentObserver(ContentObserver contentObserver) {
        System.out.println("ShadowCursorWrapper#registerContentObserver");
        wrappedCursor.registerContentObserver(contentObserver);
    }

    @Override
    @Implementation
    public void unregisterContentObserver(ContentObserver contentObserver) {
        System.out.println("ShadowCursorWrapper#unregisterContentObserver");
        wrappedCursor.unregisterContentObserver(contentObserver);
    }

    @Override
    @Implementation
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        System.out.println("ShadowCursorWrapper#registerDataSetObserver");
        wrappedCursor.registerDataSetObserver(dataSetObserver);
    }

    @Override
    @Implementation
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        System.out.println("ShadowCursorWrapper#unregisterDataSetObserver");
        wrappedCursor.unregisterDataSetObserver(dataSetObserver);
    }

    @Override
    @Implementation
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        System.out.println("ShadowCursorWrapper#setNotificationUri");
        wrappedCursor.setNotificationUri(contentResolver, uri);
    }

    @Override
    @Implementation(minSdk = KITKAT)
    public Uri getNotificationUri() {
        System.out.println("ShadowCursorWrapper#getNotificationUri");
        return wrappedCursor.getNotificationUri();
    }

    @Override
    @Implementation
    public boolean getWantsAllOnMoveCalls() {
        System.out.println("ShadowCursorWrapper#getWantsAllOnMoveCalls");
        return wrappedCursor.getWantsAllOnMoveCalls();
    }

    @Override
    @Implementation(minSdk = M)
    public void setExtras(Bundle extras) {
        System.out.println("ShadowCursorWrapper#setExtras");
        wrappedCursor.setExtras(extras);
    }

    @Override
    @Implementation
    public Bundle getExtras() {
        System.out.println("ShadowCursorWrapper#getExtras");
        return wrappedCursor.getExtras();
    }

    @Override
    @Implementation
    public Bundle respond(Bundle bundle) {
        System.out.println("ShadowCursorWrapper#respond");
        return wrappedCursor.respond(bundle);
    }

    @Override
    @Implementation
    public int getType(int columnIndex) {
        System.out.println("ShadowCursorWrapper#getType");
        return wrappedCursor.getType(columnIndex);
    }

    @Implementation
    protected Cursor getWrappedCursor() {
        System.out.println("ShadowCursorWrapper#getWrappedCursor");
        return wrappedCursor;
    }
}

