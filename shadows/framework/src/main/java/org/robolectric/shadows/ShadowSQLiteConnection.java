package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.KITKAT_WATCH;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.O;
import static android.os.Build.VERSION_CODES.O_MR1;
import static org.robolectric.RuntimeEnvironment.castNativePtr;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteCustomFunction;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteMisuseException;
import android.database.sqlite.SQLiteOutOfMemoryException;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.OperationCanceledException;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteConstants;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;
import org.robolectric.shadows.util.SQLiteLibraryLoader;

@Implements(value = android.database.sqlite.SQLiteConnection.class, isInAndroidSdk = false)
public class ShadowSQLiteConnection {

    private static final String IN_MEMORY_PATH = ":memory:";

    private static final Connections CONNECTIONS = new Connections();

    private static final Pattern COLLATE_LOCALIZED_UNICODE_PATTERN = Pattern.compile("\\s+COLLATE\\s+(LOCALIZED|UNICODE)", Pattern.CASE_INSENSITIVE);

    // indicates an ignored statement
    private static final int IGNORED_REINDEX_STMT = -2;

    private static AtomicBoolean useInMemoryDatabase = new AtomicBoolean();

    public static void setUseInMemoryDatabase(boolean value) {
        useInMemoryDatabase.set(value);
    }

    @Implementation(maxSdk = O)
    public static Number nativeOpen(String path, int openFlags, String label, boolean enableTrace, boolean enableProfile) {
        System.out.println("ShadowSQLiteConnection#nativeOpen");
        SQLiteLibraryLoader.load();
        return castNativePtr(CONNECTIONS.open(path));
    }

    @Implementation(minSdk = O_MR1)
    public static long nativeOpen(String path, int openFlags, String label, boolean enableTrace, boolean enableProfile, int lookasideSlotSize, int lookasideSlotCount) {
        System.out.println("ShadowSQLiteConnection#nativeOpen");
        return nativeOpen(path, openFlags, label, enableTrace, enableProfile).longValue();
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativePrepareStatement(int connectionPtr, String sql) {
        System.out.println("ShadowSQLiteConnection#nativePrepareStatement");
        return (int) nativePrepareStatement((long) connectionPtr, sql);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static long nativePrepareStatement(long connectionPtr, String sql) {
        System.out.println("ShadowSQLiteConnection#nativePrepareStatement");
        final String newSql = convertSQLWithLocalizedUnicodeCollator(sql);
        return CONNECTIONS.prepareStatement(connectionPtr, newSql);
    }

    /**
     * Convert SQL with phrase COLLATE LOCALIZED or COLLATE UNICODE to COLLATE NOCASE.
     */
    static String convertSQLWithLocalizedUnicodeCollator(String sql) {
        Matcher matcher = COLLATE_LOCALIZED_UNICODE_PATTERN.matcher(sql);
        return matcher.replaceAll(" COLLATE NOCASE");
    }

    @Resetter
    public static void reset() {
        CONNECTIONS.reset();
        useInMemoryDatabase.set(false);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeClose(int connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeClose");
        nativeClose((long) connectionPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeClose(long connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeClose");
        CONNECTIONS.close(connectionPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeFinalizeStatement(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeFinalizeStatement");
        nativeFinalizeStatement((long) connectionPtr, statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeFinalizeStatement(long connectionPtr, long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeFinalizeStatement");
        CONNECTIONS.finalizeStmt(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativeGetParameterCount(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetParameterCount");
        return nativeGetParameterCount((long) connectionPtr, statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static int nativeGetParameterCount(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetParameterCount");
        return CONNECTIONS.getParameterCount(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static boolean nativeIsReadOnly(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeIsReadOnly");
        return nativeIsReadOnly((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static boolean nativeIsReadOnly(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeIsReadOnly");
        return CONNECTIONS.isReadOnly(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static long nativeExecuteForLong(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForLong");
        return nativeExecuteForLong((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static long nativeExecuteForLong(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForLong");
        return CONNECTIONS.executeForLong(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeExecute(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecute");
        nativeExecute((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeExecute(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecute");
        CONNECTIONS.executeStatement(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static String nativeExecuteForString(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForString");
        return nativeExecuteForString((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static String nativeExecuteForString(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForString");
        return CONNECTIONS.executeForString(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativeGetColumnCount(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetColumnCount");
        return nativeGetColumnCount((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static int nativeGetColumnCount(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetColumnCount");
        return CONNECTIONS.getColumnCount(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static String nativeGetColumnName(int connectionPtr, int statementPtr, int index) {
        System.out.println("ShadowSQLiteConnection#nativeGetColumnName");
        return nativeGetColumnName((long) connectionPtr, (long) statementPtr, index);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static String nativeGetColumnName(final long connectionPtr, final long statementPtr, final int index) {
        System.out.println("ShadowSQLiteConnection#nativeGetColumnName");
        return CONNECTIONS.getColumnName(connectionPtr, statementPtr, index);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeBindNull(int connectionPtr, int statementPtr, int index) {
        System.out.println("ShadowSQLiteConnection#nativeBindNull");
        nativeBindNull((long) connectionPtr, (long) statementPtr, index);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeBindNull(final long connectionPtr, final long statementPtr, final int index) {
        System.out.println("ShadowSQLiteConnection#nativeBindNull");
        CONNECTIONS.bindNull(connectionPtr, statementPtr, index);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeBindLong(int connectionPtr, int statementPtr, int index, long value) {
        System.out.println("ShadowSQLiteConnection#nativeBindLong");
        nativeBindLong((long) connectionPtr, (long) statementPtr, index, value);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeBindLong(final long connectionPtr, final long statementPtr, final int index, final long value) {
        System.out.println("ShadowSQLiteConnection#nativeBindLong");
        CONNECTIONS.bindLong(connectionPtr, statementPtr, index, value);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeBindDouble(int connectionPtr, int statementPtr, int index, double value) {
        System.out.println("ShadowSQLiteConnection#nativeBindDouble");
        nativeBindDouble((long) connectionPtr, (long) statementPtr, index, value);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeBindDouble(final long connectionPtr, final long statementPtr, final int index, final double value) {
        System.out.println("ShadowSQLiteConnection#nativeBindDouble");
        CONNECTIONS.bindDouble(connectionPtr, statementPtr, index, value);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeBindString(int connectionPtr, int statementPtr, int index, String value) {
        System.out.println("ShadowSQLiteConnection#nativeBindString");
        nativeBindString((long) connectionPtr, (long) statementPtr, index, value);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeBindString(final long connectionPtr, final long statementPtr, final int index, final String value) {
        System.out.println("ShadowSQLiteConnection#nativeBindString");
        CONNECTIONS.bindString(connectionPtr, statementPtr, index, value);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeBindBlob(int connectionPtr, int statementPtr, int index, byte[] value) {
        System.out.println("ShadowSQLiteConnection#nativeBindBlob");
        nativeBindBlob((long) connectionPtr, (long) statementPtr, index, value);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeBindBlob(final long connectionPtr, final long statementPtr, final int index, final byte[] value) {
        System.out.println("ShadowSQLiteConnection#nativeBindBlob");
        CONNECTIONS.bindBlob(connectionPtr, statementPtr, index, value);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeRegisterLocalizedCollators(int connectionPtr, String locale) {
        System.out.println("ShadowSQLiteConnection#nativeRegisterLocalizedCollators");
        nativeRegisterLocalizedCollators((long) connectionPtr, locale);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeRegisterLocalizedCollators(long connectionPtr, String locale) {
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativeExecuteForChangedRowCount(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForChangedRowCount");
        return nativeExecuteForChangedRowCount((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static int nativeExecuteForChangedRowCount(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForChangedRowCount");
        return CONNECTIONS.executeForChangedRowCount(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static long nativeExecuteForLastInsertedRowId(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForLastInsertedRowId");
        return nativeExecuteForLastInsertedRowId((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static long nativeExecuteForLastInsertedRowId(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForLastInsertedRowId");
        return CONNECTIONS.executeForLastInsertedRowId(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static long nativeExecuteForCursorWindow(int connectionPtr, int statementPtr, int windowPtr, int startPos, int requiredPos, boolean countAllRows) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForCursorWindow");
        return nativeExecuteForCursorWindow((long) connectionPtr, (long) statementPtr, (long) windowPtr, startPos, requiredPos, countAllRows);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static long nativeExecuteForCursorWindow(final long connectionPtr, final long statementPtr, final long windowPtr, final int startPos, final int requiredPos, final boolean countAllRows) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForCursorWindow");
        return CONNECTIONS.executeForCursorWindow(connectionPtr, statementPtr, windowPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeResetStatementAndClearBindings(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeResetStatementAndClearBindings");
        nativeResetStatementAndClearBindings((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeResetStatementAndClearBindings(final long connectionPtr, final long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeResetStatementAndClearBindings");
        CONNECTIONS.resetStatementAndClearBindings(connectionPtr, statementPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeCancel(int connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeCancel");
        nativeCancel((long) connectionPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeCancel(long connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeCancel");
        CONNECTIONS.cancel(connectionPtr);
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeResetCancel(int connectionPtr, boolean cancelable) {
        System.out.println("ShadowSQLiteConnection#nativeResetCancel");
        nativeResetCancel((long) connectionPtr, cancelable);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeResetCancel(long connectionPtr, boolean cancelable) {
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static void nativeRegisterCustomFunction(int connectionPtr, SQLiteCustomFunction function) {
        System.out.println("ShadowSQLiteConnection#nativeRegisterCustomFunction");
        nativeRegisterCustomFunction((long) connectionPtr, function);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static void nativeRegisterCustomFunction(long connectionPtr, SQLiteCustomFunction function) {
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativeExecuteForBlobFileDescriptor(int connectionPtr, int statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForBlobFileDescriptor");
        return nativeExecuteForBlobFileDescriptor((long) connectionPtr, (long) statementPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static int nativeExecuteForBlobFileDescriptor(long connectionPtr, long statementPtr) {
        System.out.println("ShadowSQLiteConnection#nativeExecuteForBlobFileDescriptor");
        // impossible to support without native code?
        return -1;
    }

    @Implementation(maxSdk = KITKAT_WATCH)
    public static int nativeGetDbLookaside(int connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetDbLookaside");
        return nativeGetDbLookaside((long) connectionPtr);
    }

    @Implementation(minSdk = LOLLIPOP)
    public static int nativeGetDbLookaside(long connectionPtr) {
        System.out.println("ShadowSQLiteConnection#nativeGetDbLookaside");
        // not supported by sqlite4java
        return 0;
    }

    // VisibleForTesting
    static class Connections {

        private final Object lock = new Object();

        private final AtomicLong pointerCounter = new AtomicLong(0);

        private final Map<Long, SQLiteStatement> statementsMap = new HashMap<>();

        private final Map<Long, SQLiteConnection> connectionsMap = new HashMap<>();

        private final Map<Long, List<Long>> statementPtrsForConnection = new HashMap<>();

        private ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

        SQLiteConnection getConnection(final long connectionPtr) {
            synchronized (lock) {
                final SQLiteConnection connection = connectionsMap.get(connectionPtr);
                if (connection == null) {
                    throw new IllegalStateException("Illegal connection pointer " + connectionPtr + ". Current pointers for thread " + Thread.currentThread() + " " + connectionsMap.keySet());
                }
                return connection;
            }
        }

        SQLiteStatement getStatement(final long connectionPtr, final long statementPtr) {
            synchronized (lock) {
                // ensure connection is ok
                getConnection(connectionPtr);
                final SQLiteStatement statement = statementsMap.get(statementPtr);
                if (statement == null) {
                    throw new IllegalArgumentException("Invalid prepared statement pointer: " + statementPtr + ". Current pointers: " + statementsMap.keySet());
                }
                if (statement.isDisposed()) {
                    throw new IllegalStateException("Statement " + statementPtr + " " + statement + " is disposed");
                }
                return statement;
            }
        }

        long open(final String path) {
            synchronized (lock) {
                final SQLiteConnection dbConnection = execute("open SQLite connection", new Callable<SQLiteConnection>() {

                    @Override
                    public SQLiteConnection call() throws Exception {
                        SQLiteConnection connection = useInMemoryDatabase.get() || IN_MEMORY_PATH.equals(path) ? new SQLiteConnection() : new SQLiteConnection(new File(path));
                        connection.open();
                        return connection;
                    }
                });
                final long connectionPtr = pointerCounter.incrementAndGet();
                connectionsMap.put(connectionPtr, dbConnection);
                statementPtrsForConnection.put(connectionPtr, new ArrayList<>());
                return connectionPtr;
            }
        }

        long prepareStatement(final long connectionPtr, final String sql) {
            // TODO: find a way to create collators
            if ("REINDEX LOCALIZED".equals(sql)) {
                return IGNORED_REINDEX_STMT;
            }
            synchronized (lock) {
                final SQLiteConnection connection = getConnection(connectionPtr);
                final SQLiteStatement statement = execute("prepare statement", new Callable<SQLiteStatement>() {

                    @Override
                    public SQLiteStatement call() throws Exception {
                        return connection.prepare(sql);
                    }
                });
                final long statementPtr = pointerCounter.incrementAndGet();
                statementsMap.put(statementPtr, statement);
                statementPtrsForConnection.get(connectionPtr).add(statementPtr);
                return statementPtr;
            }
        }

        void close(final long connectionPtr) {
            synchronized (lock) {
                final SQLiteConnection connection = getConnection(connectionPtr);
                execute("close connection", new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        connection.dispose();
                        return null;
                    }
                });
                connectionsMap.remove(connectionPtr);
                statementPtrsForConnection.remove(connectionPtr);
            }
        }

        void reset() {
            ExecutorService oldDbExecutor;
            Collection<SQLiteConnection> openConnections;
            synchronized (lock) {
                oldDbExecutor = dbExecutor;
                openConnections = new ArrayList<>(connectionsMap.values());
                dbExecutor = Executors.newSingleThreadExecutor();
                connectionsMap.clear();
                statementsMap.clear();
                statementPtrsForConnection.clear();
            }
            shutdownDbExecutor(oldDbExecutor, openConnections);
        }

        private static void shutdownDbExecutor(ExecutorService executorService, Collection<SQLiteConnection> connections) {
            for (final SQLiteConnection connection : connections) {
                getFuture("close connection on reset", executorService.submit(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        connection.dispose();
                        return null;
                    }
                }));
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void finalizeStmt(final long connectionPtr, final long statementPtr) {
            if (statementPtr == IGNORED_REINDEX_STMT) {
                return;
            }
            synchronized (lock) {
                final SQLiteStatement statement = getStatement(connectionPtr, statementPtr);
                statementsMap.remove(statementPtr);
                execute("finalize statement", new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        statement.dispose();
                        return null;
                    }
                });
            }
        }

        void cancel(final long connectionPtr) {
            synchronized (lock) {
                // check connection
                getConnection(connectionPtr);
                for (Long statementPtr : statementPtrsForConnection.get(connectionPtr)) {
                    final SQLiteStatement statement = statementsMap.get(statementPtr);
                    if (statement != null) {
                        execute("cancel", new Callable<Void>() {

                            @Override
                            public Void call() throws Exception {
                                statement.cancel();
                                return null;
                            }
                        });
                    }
                }
            }
        }

        int getParameterCount(final long connectionPtr, final long statementPtr) {
            if (statementPtr == IGNORED_REINDEX_STMT) {
                return 0;
            }
            return executeStatementOperation(connectionPtr, statementPtr, "get parameters count in prepared statement", new StatementOperation<Integer>() {

                @Override
                public Integer call(final SQLiteStatement statement) throws Exception {
                    return statement.getBindParameterCount();
                }
            });
        }

        boolean isReadOnly(final long connectionPtr, final long statementPtr) {
            if (statementPtr == IGNORED_REINDEX_STMT) {
                return true;
            }
            return executeStatementOperation(connectionPtr, statementPtr, "call isReadOnly", new StatementOperation<Boolean>() {

                @Override
                public Boolean call(final SQLiteStatement statement) throws Exception {
                    return statement.isReadOnly();
                }
            });
        }

        long executeForLong(final long connectionPtr, final long statementPtr) {
            return executeStatementOperation(connectionPtr, statementPtr, "execute for long", new StatementOperation<Long>() {

                @Override
                public Long call(final SQLiteStatement statement) throws Exception {
                    if (!statement.step()) {
                        throw new SQLiteException(SQLiteConstants.SQLITE_DONE, "No rows returned from query");
                    }
                    return statement.columnLong(0);
                }
            });
        }

        void executeStatement(final long connectionPtr, final long statementPtr) {
            if (statementPtr == IGNORED_REINDEX_STMT) {
                return;
            }
            executeStatementOperation(connectionPtr, statementPtr, "execute", new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.stepThrough();
                    return null;
                }
            });
        }

        String executeForString(final long connectionPtr, final long statementPtr) {
            return executeStatementOperation(connectionPtr, statementPtr, "execute for string", new StatementOperation<String>() {

                @Override
                public String call(final SQLiteStatement statement) throws Exception {
                    if (!statement.step()) {
                        throw new SQLiteException(SQLiteConstants.SQLITE_DONE, "No rows returned from query");
                    }
                    return statement.columnString(0);
                }
            });
        }

        int getColumnCount(final long connectionPtr, final long statementPtr) {
            return executeStatementOperation(connectionPtr, statementPtr, "get columns count", new StatementOperation<Integer>() {

                @Override
                public Integer call(final SQLiteStatement statement) throws Exception {
                    return statement.columnCount();
                }
            });
        }

        String getColumnName(final long connectionPtr, final long statementPtr, final int index) {
            return executeStatementOperation(connectionPtr, statementPtr, "get column name at index " + index, new StatementOperation<String>() {

                @Override
                public String call(final SQLiteStatement statement) throws Exception {
                    return statement.getColumnName(index);
                }
            });
        }

        void bindNull(final long connectionPtr, final long statementPtr, final int index) {
            executeStatementOperation(connectionPtr, statementPtr, "bind null at index " + index, new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.bindNull(index);
                    return null;
                }
            });
        }

        void bindLong(final long connectionPtr, final long statementPtr, final int index, final long value) {
            executeStatementOperation(connectionPtr, statementPtr, "bind long at index " + index + " with value " + value, new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.bind(index, value);
                    return null;
                }
            });
        }

        void bindDouble(final long connectionPtr, final long statementPtr, final int index, final double value) {
            executeStatementOperation(connectionPtr, statementPtr, "bind double at index " + index + " with value " + value, new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.bind(index, value);
                    return null;
                }
            });
        }

        void bindString(final long connectionPtr, final long statementPtr, final int index, final String value) {
            executeStatementOperation(connectionPtr, statementPtr, "bind string at index " + index, new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.bind(index, value);
                    return null;
                }
            });
        }

        void bindBlob(final long connectionPtr, final long statementPtr, final int index, final byte[] value) {
            executeStatementOperation(connectionPtr, statementPtr, "bind blob at index " + index, new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.bind(index, value);
                    return null;
                }
            });
        }

        int executeForChangedRowCount(final long connectionPtr, final long statementPtr) {
            synchronized (lock) {
                final SQLiteConnection connection = getConnection(connectionPtr);
                final SQLiteStatement statement = getStatement(connectionPtr, statementPtr);
                return execute("execute for changed row count", new Callable<Integer>() {

                    @Override
                    public Integer call() throws Exception {
                        statement.stepThrough();
                        return connection.getChanges();
                    }
                });
            }
        }

        long executeForLastInsertedRowId(final long connectionPtr, final long statementPtr) {
            synchronized (lock) {
                final SQLiteConnection connection = getConnection(connectionPtr);
                final SQLiteStatement statement = getStatement(connectionPtr, statementPtr);
                return execute("execute for last inserted row ID", new Callable<Long>() {

                    @Override
                    public Long call() throws Exception {
                        statement.stepThrough();
                        return connection.getLastInsertId();
                    }
                });
            }
        }

        long executeForCursorWindow(final long connectionPtr, final long statementPtr, final long windowPtr) {
            return executeStatementOperation(connectionPtr, statementPtr, "execute for cursor window", new StatementOperation<Integer>() {

                @Override
                public Integer call(final SQLiteStatement statement) throws Exception {
                    return ShadowCursorWindow.setData(windowPtr, statement);
                }
            });
        }

        void resetStatementAndClearBindings(final long connectionPtr, final long statementPtr) {
            executeStatementOperation(connectionPtr, statementPtr, "reset statement", new StatementOperation<Void>() {

                @Override
                public Void call(final SQLiteStatement statement) throws Exception {
                    statement.reset(true);
                    return null;
                }
            });
        }

        interface StatementOperation<T> {

            T call(final SQLiteStatement statement) throws Exception;
        }

        private <T> T executeStatementOperation(final long connectionPtr, final long statementPtr, final String comment, final StatementOperation<T> statementOperation) {
            synchronized (lock) {
                final SQLiteStatement statement = getStatement(connectionPtr, statementPtr);
                return execute(comment, new Callable<T>() {

                    @Override
                    public T call() throws Exception {
                        return statementOperation.call(statement);
                    }
                });
            }
        }

        /**
         * Any Callable passed in to execute must not synchronize on lock, as this will result in a deadlock
         */
        private <T> T execute(final String comment, final Callable<T> work) {
            synchronized (lock) {
                return getFuture(comment, dbExecutor.submit(work));
            }
        }

        private static <T> T getFuture(final String comment, final Future<T> future) {
            try {
                return Uninterruptibles.getUninterruptibly(future);
            // No need to catch cancellationexception - we never cancel these futures
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
                if (t instanceof SQLiteException) {
                    final RuntimeException sqlException = getSqliteException("Cannot " + comment, ((SQLiteException) t).getBaseErrorCode());
                    sqlException.initCause(e);
                    throw sqlException;
                } else {
                    throw new RuntimeException(e);
                }
            }
        }

        private static RuntimeException getSqliteException(final String message, final int baseErrorCode) {
            // Mapping is from throw_sqlite3_exception in android_database_SQLiteCommon.cpp
            switch(baseErrorCode) {
                case SQLiteConstants.SQLITE_ABORT:
                    return new SQLiteAbortException(message);
                case SQLiteConstants.SQLITE_PERM:
                    return new SQLiteAccessPermException(message);
                case SQLiteConstants.SQLITE_RANGE:
                    return new SQLiteBindOrColumnIndexOutOfRangeException(message);
                case SQLiteConstants.SQLITE_TOOBIG:
                    return new SQLiteBlobTooBigException(message);
                case SQLiteConstants.SQLITE_CANTOPEN:
                    return new SQLiteCantOpenDatabaseException(message);
                case SQLiteConstants.SQLITE_CONSTRAINT:
                    return new SQLiteConstraintException(message);
                // fall through
                case SQLiteConstants.SQLITE_NOTADB:
                case SQLiteConstants.SQLITE_CORRUPT:
                    return new SQLiteDatabaseCorruptException(message);
                case SQLiteConstants.SQLITE_BUSY:
                    return new SQLiteDatabaseLockedException(message);
                case SQLiteConstants.SQLITE_MISMATCH:
                    return new SQLiteDatatypeMismatchException(message);
                case SQLiteConstants.SQLITE_IOERR:
                    return new SQLiteDiskIOException(message);
                case SQLiteConstants.SQLITE_DONE:
                    return new SQLiteDoneException(message);
                case SQLiteConstants.SQLITE_FULL:
                    return new SQLiteFullException(message);
                case SQLiteConstants.SQLITE_MISUSE:
                    return new SQLiteMisuseException(message);
                case SQLiteConstants.SQLITE_NOMEM:
                    return new SQLiteOutOfMemoryException(message);
                case SQLiteConstants.SQLITE_READONLY:
                    return new SQLiteReadOnlyDatabaseException(message);
                case SQLiteConstants.SQLITE_LOCKED:
                    return new SQLiteTableLockedException(message);
                case SQLiteConstants.SQLITE_INTERRUPT:
                    return new OperationCanceledException(message);
                default:
                    return new android.database.sqlite.SQLiteException(message + ", base error code: " + baseErrorCode);
            }
        }
    }
}

