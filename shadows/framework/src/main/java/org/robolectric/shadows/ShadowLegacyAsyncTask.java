package org.robolectric.shadows;

import android.os.AsyncTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.LooperMode;
import org.robolectric.annotation.RealObject;

/**
 * A {@link AsyncTask} shadow for {@link LooperMode.Mode.LEGACY}.
 */
@Implements(value = AsyncTask.class, shadowPicker = ShadowAsyncTask.Picker.class, // TODO: turn off shadowOf generation. Figure out why this is needed
isInAndroidSdk = false)
public class ShadowLegacyAsyncTask<Params, Progress, Result> extends ShadowAsyncTask {

    @RealObject
    private AsyncTask<Params, Progress, Result> realAsyncTask;

    private final FutureTask<Result> future;

    private final BackgroundWorker worker;

    private AsyncTask.Status status = AsyncTask.Status.PENDING;

    public ShadowLegacyAsyncTask() {
        worker = new BackgroundWorker();
        future = new FutureTask<Result>(worker) {

            @Override
            protected void done() {
                status = AsyncTask.Status.FINISHED;
                try {
                    final Result result = get();
                    try {
                        ShadowApplication.getInstance().getForegroundThreadScheduler().post(new Runnable() {

                            @Override
                            public void run() {
                                getBridge().onPostExecute(result);
                            }
                        });
                    } catch (Throwable t) {
                        throw new OnPostExecuteException(t);
                    }
                } catch (CancellationException e) {
                    ShadowApplication.getInstance().getForegroundThreadScheduler().post(new Runnable() {

                        @Override
                        public void run() {
                            getBridge().onCancelled();
                        }
                    });
                } catch (InterruptedException e) {
                // Ignore.
                } catch (OnPostExecuteException e) {
                    throw new RuntimeException(e.getCause());
                } catch (Throwable t) {
                    throw new RuntimeException("An error occurred while executing doInBackground()", t.getCause());
                }
            }
        };
    }

    @Implementation
    protected boolean isCancelled() {
        System.out.println("ShadowLegacyAsyncTask#isCancelled");
        return future.isCancelled();
    }

    @Implementation
    protected boolean cancel(boolean mayInterruptIfRunning) {
        System.out.println("ShadowLegacyAsyncTask#cancel");
        return future.cancel(mayInterruptIfRunning);
    }

    @Implementation
    protected Result get() throws InterruptedException, ExecutionException {
        System.out.println("ShadowLegacyAsyncTask#get");
        return future.get();
    }

    @Implementation
    protected Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("ShadowLegacyAsyncTask#get");
        return future.get(timeout, unit);
    }

    @Implementation
    protected AsyncTask<Params, Progress, Result> execute(final Params... params) {
        System.out.println("ShadowLegacyAsyncTask#execute");
        status = AsyncTask.Status.RUNNING;
        getBridge().onPreExecute();
        worker.params = params;
        ShadowApplication.getInstance().getBackgroundThreadScheduler().post(new Runnable() {

            @Override
            public void run() {
                future.run();
            }
        });
        return realAsyncTask;
    }

    @Implementation
    protected AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... params) {
        System.out.println("ShadowLegacyAsyncTask#executeOnExecutor");
        status = AsyncTask.Status.RUNNING;
        getBridge().onPreExecute();
        worker.params = params;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                future.run();
            }
        });
        return realAsyncTask;
    }

    @Implementation
    protected AsyncTask.Status getStatus() {
        System.out.println("ShadowLegacyAsyncTask#getStatus");
        return status;
    }

    /**
     * Enqueue a call to {@link AsyncTask#onProgressUpdate(Object[])} on UI looper (or run it
     * immediately if the looper it is not paused).
     *
     * @param values The progress values to update the UI with.
     * @see AsyncTask#publishProgress(Object[])
     */
    @Implementation
    protected void publishProgress(final Progress... values) {
        System.out.println("ShadowLegacyAsyncTask#publishProgress");
        ShadowApplication.getInstance().getForegroundThreadScheduler().post(new Runnable() {

            @Override
            public void run() {
                getBridge().onProgressUpdate(values);
            }
        });
    }

    private ShadowAsyncTaskBridge<Params, Progress, Result> getBridge() {
        return new ShadowAsyncTaskBridge<>(realAsyncTask);
    }

    private final class BackgroundWorker implements Callable<Result> {

        Params[] params;

        @Override
        public Result call() throws Exception {
            return getBridge().doInBackground(params);
        }
    }

    private static class OnPostExecuteException extends Exception {

        public OnPostExecuteException(Throwable throwable) {
            super(throwable);
        }
    }
}

