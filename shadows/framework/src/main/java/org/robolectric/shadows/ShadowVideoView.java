package org.robolectric.shadows;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(VideoView.class)
@SuppressWarnings({ "UnusedDeclaration" })
public class ShadowVideoView extends ShadowSurfaceView {

    private MediaPlayer.OnCompletionListener completionListner;

    private MediaPlayer.OnErrorListener errorListener;

    private MediaPlayer.OnPreparedListener preparedListener;

    private Uri uri;

    private String path;

    private int duration = 0;

    public static final int STOP = 0;

    public static final int START = 1;

    public static final int SUSPEND = 2;

    public static final int PAUSE = 3;

    public static final int RESUME = 4;

    private int currentState = -1;

    private int prevState;

    private int currentPosition;

    @Implementation
    protected void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        System.out.println("ShadowVideoView#setOnPreparedListener");
        preparedListener = l;
    }

    @Implementation
    protected void setOnErrorListener(MediaPlayer.OnErrorListener l) {
        System.out.println("ShadowVideoView#setOnErrorListener");
        errorListener = l;
    }

    @Implementation
    protected void setOnCompletionListener(MediaPlayer.OnCompletionListener l) {
        System.out.println("ShadowVideoView#setOnCompletionListener");
        completionListner = l;
    }

    @Implementation
    protected void setVideoPath(String path) {
        System.out.println("ShadowVideoView#setVideoPath");
        this.path = path;
    }

    @Implementation
    protected void setVideoURI(Uri uri) {
        System.out.println("ShadowVideoView#setVideoURI");
        this.uri = uri;
    }

    @Implementation
    protected void start() {
        System.out.println("ShadowVideoView#start");
        savePrevState();
        currentState = ShadowVideoView.START;
    }

    @Implementation
    protected void stopPlayback() {
        System.out.println("ShadowVideoView#stopPlayback");
        savePrevState();
        currentState = ShadowVideoView.STOP;
    }

    @Implementation
    protected void suspend() {
        System.out.println("ShadowVideoView#suspend");
        savePrevState();
        currentState = ShadowVideoView.SUSPEND;
    }

    @Implementation
    protected void pause() {
        System.out.println("ShadowVideoView#pause");
        savePrevState();
        currentState = ShadowVideoView.PAUSE;
    }

    @Implementation
    protected void resume() {
        System.out.println("ShadowVideoView#resume");
        savePrevState();
        currentState = ShadowVideoView.RESUME;
    }

    @Implementation
    protected boolean isPlaying() {
        System.out.println("ShadowVideoView#isPlaying");
        return (currentState == ShadowVideoView.START);
    }

    @Implementation
    protected boolean canPause() {
        System.out.println("ShadowVideoView#canPause");
        return (currentState != ShadowVideoView.PAUSE && currentState != ShadowVideoView.STOP && currentState != ShadowVideoView.SUSPEND);
    }

    @Implementation
    protected void seekTo(int msec) {
        System.out.println("ShadowVideoView#seekTo");
        currentPosition = msec;
    }

    @Implementation
    protected int getCurrentPosition() {
        System.out.println("ShadowVideoView#getCurrentPosition");
        return currentPosition;
    }

    @Implementation
    protected int getDuration() {
        System.out.println("ShadowVideoView#getDuration");
        return duration;
    }

    /**
     * @return On prepared listener.
     */
    public MediaPlayer.OnPreparedListener getOnPreparedListener() {
        return preparedListener;
    }

    /**
     * @return On error listener.
     */
    public MediaPlayer.OnErrorListener getOnErrorListener() {
        return errorListener;
    }

    /**
     * @return On completion listener.
     */
    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return completionListner;
    }

    /**
     * @return Video path.
     */
    public String getVideoPath() {
        return path;
    }

    /**
     * @return Video URI.
     */
    public String getVideoURIString() {
        return uri == null ? null : uri.toString();
    }

    /**
     * @return Current video state.
     */
    public int getCurrentVideoState() {
        return currentState;
    }

    /**
     * @return Previous video state.
     */
    public int getPrevVideoState() {
        return prevState;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private void savePrevState() {
        prevState = currentState;
    }
}

