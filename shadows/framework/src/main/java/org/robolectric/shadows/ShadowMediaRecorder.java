package org.robolectric.shadows;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.Surface;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(MediaRecorder.class)
public class ShadowMediaRecorder {

    @SuppressWarnings("UnusedDeclaration")
    @Implementation
    protected static void __staticInitializer__() {
    }

    // Recording machine state, as per:
    // http://developer.android.com/reference/android/media/MediaRecorder.html
    public static final int STATE_ERROR = -1;

    public static final int STATE_INITIAL = 1;

    public static final int STATE_INITIALIZED = 2;

    public static final int STATE_DATA_SOURCE_CONFIGURED = 3;

    public static final int STATE_PREPARED = 4;

    public static final int STATE_RECORDING = 5;

    public static final int STATE_RELEASED = 6;

    private int state;

    private Camera camera;

    private int audioChannels;

    private int audioEncoder;

    private int audioBitRate;

    private int audioSamplingRate;

    private int audioSource;

    private int maxDuration;

    private long maxFileSize;

    private String outputPath;

    private int outputFormat;

    private int videoEncoder;

    private int videoBitRate;

    private int videoFrameRate;

    private int videoWidth;

    private int videoHeight;

    private int videoSource;

    private Surface previewDisplay;

    private MediaRecorder.OnErrorListener errorListener;

    private MediaRecorder.OnInfoListener infoListener;

    @Implementation
    protected void __constructor__() {
        System.out.println("ShadowMediaRecorder#__constructor__");
        state = STATE_INITIAL;
    }

    @Implementation
    protected void setAudioChannels(int numChannels) {
        System.out.println("ShadowMediaRecorder#setAudioChannels");
        audioChannels = numChannels;
    }

    @Implementation
    protected void setAudioEncoder(int audio_encoder) {
        System.out.println("ShadowMediaRecorder#setAudioEncoder");
        audioEncoder = audio_encoder;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setAudioEncodingBitRate(int bitRate) {
        System.out.println("ShadowMediaRecorder#setAudioEncodingBitRate");
        audioBitRate = bitRate;
    }

    @Implementation
    protected void setAudioSamplingRate(int samplingRate) {
        System.out.println("ShadowMediaRecorder#setAudioSamplingRate");
        audioSamplingRate = samplingRate;
    }

    @Implementation
    protected void setAudioSource(int audio_source) {
        System.out.println("ShadowMediaRecorder#setAudioSource");
        audioSource = audio_source;
        state = STATE_INITIALIZED;
    }

    @Implementation
    protected void setCamera(Camera c) {
        System.out.println("ShadowMediaRecorder#setCamera");
        camera = c;
    }

    @Implementation
    protected void setMaxDuration(int max_duration_ms) {
        System.out.println("ShadowMediaRecorder#setMaxDuration");
        maxDuration = max_duration_ms;
    }

    @Implementation
    protected void setMaxFileSize(long max_filesize_bytes) {
        System.out.println("ShadowMediaRecorder#setMaxFileSize");
        maxFileSize = max_filesize_bytes;
    }

    @Implementation
    protected void setOnErrorListener(MediaRecorder.OnErrorListener l) {
        System.out.println("ShadowMediaRecorder#setOnErrorListener");
        errorListener = l;
    }

    @Implementation
    protected void setOnInfoListener(MediaRecorder.OnInfoListener listener) {
        System.out.println("ShadowMediaRecorder#setOnInfoListener");
        infoListener = listener;
    }

    @Implementation
    protected void setOutputFile(String path) {
        System.out.println("ShadowMediaRecorder#setOutputFile");
        outputPath = path;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setOutputFormat(int output_format) {
        System.out.println("ShadowMediaRecorder#setOutputFormat");
        outputFormat = output_format;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setPreviewDisplay(Surface sv) {
        System.out.println("ShadowMediaRecorder#setPreviewDisplay");
        previewDisplay = sv;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setVideoEncoder(int video_encoder) {
        System.out.println("ShadowMediaRecorder#setVideoEncoder");
        videoEncoder = video_encoder;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setVideoEncodingBitRate(int bitRate) {
        System.out.println("ShadowMediaRecorder#setVideoEncodingBitRate");
        videoBitRate = bitRate;
    }

    @Implementation
    protected void setVideoFrameRate(int rate) {
        System.out.println("ShadowMediaRecorder#setVideoFrameRate");
        videoFrameRate = rate;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setVideoSize(int width, int height) {
        System.out.println("ShadowMediaRecorder#setVideoSize");
        videoWidth = width;
        videoHeight = height;
        state = STATE_DATA_SOURCE_CONFIGURED;
    }

    @Implementation
    protected void setVideoSource(int video_source) {
        System.out.println("ShadowMediaRecorder#setVideoSource");
        videoSource = video_source;
        state = STATE_INITIALIZED;
    }

    @Implementation
    protected void prepare() {
        System.out.println("ShadowMediaRecorder#prepare");
        state = STATE_PREPARED;
    }

    @Implementation
    protected void start() {
        System.out.println("ShadowMediaRecorder#start");
        state = STATE_RECORDING;
    }

    @Implementation
    protected void stop() {
        System.out.println("ShadowMediaRecorder#stop");
        state = STATE_INITIAL;
    }

    @Implementation
    protected void reset() {
        System.out.println("ShadowMediaRecorder#reset");
        state = STATE_INITIAL;
    }

    @Implementation
    protected void release() {
        System.out.println("ShadowMediaRecorder#release");
        state = STATE_RELEASED;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public int getAudioEncoder() {
        return audioEncoder;
    }

    public int getAudioEncodingBitRate() {
        return audioBitRate;
    }

    public int getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public int getOutputFormat() {
        return outputFormat;
    }

    public int getVideoEncoder() {
        return videoEncoder;
    }

    public int getVideoEncodingBitRate() {
        return videoBitRate;
    }

    public int getVideoFrameRate() {
        return videoFrameRate;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public int getVideoSource() {
        return videoSource;
    }

    public Surface getPreviewDisplay() {
        return previewDisplay;
    }

    public MediaRecorder.OnErrorListener getErrorListener() {
        return errorListener;
    }

    public MediaRecorder.OnInfoListener getInfoListener() {
        return infoListener;
    }

    public int getState() {
        return state;
    }
}

