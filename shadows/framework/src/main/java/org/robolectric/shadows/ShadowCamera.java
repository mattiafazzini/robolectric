package org.robolectric.shadows;

import static org.robolectric.shadow.api.Shadow.newInstanceOf;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.ReflectionHelpers;

@Implements(Camera.class)
public class ShadowCamera {

    private static int lastOpenedCameraId;

    private int id;

    private boolean locked;

    private boolean previewing;

    private boolean released;

    private Camera.Parameters parameters;

    private Camera.PreviewCallback previewCallback;

    private List<byte[]> callbackBuffers = new ArrayList<>();

    private SurfaceHolder surfaceHolder;

    private int displayOrientation;

    private Camera.AutoFocusCallback autoFocusCallback;

    private boolean autoFocusing;

    private static Map<Integer, Camera.CameraInfo> cameras = new HashMap<>();

    @RealObject
    private Camera realCamera;

    @Implementation
    protected void __constructor__() {
        System.out.println("ShadowCamera#__constructor__");
        locked = true;
        previewing = false;
        released = false;
    }

    @Implementation
    protected static Camera open() {
        System.out.println("ShadowCamera#open");
        lastOpenedCameraId = 0;
        Camera camera = newInstanceOf(Camera.class);
        ShadowCamera shadowCamera = Shadow.extract(camera);
        shadowCamera.id = 0;
        return camera;
    }

    @Implementation
    protected static Camera open(int cameraId) {
        System.out.println("ShadowCamera#open");
        lastOpenedCameraId = cameraId;
        Camera camera = newInstanceOf(Camera.class);
        ShadowCamera shadowCamera = Shadow.extract(camera);
        shadowCamera.id = cameraId;
        return camera;
    }

    public static int getLastOpenedCameraId() {
        return lastOpenedCameraId;
    }

    @Implementation
    protected void unlock() {
        System.out.println("ShadowCamera#unlock");
        locked = false;
    }

    @Implementation
    protected void reconnect() {
        System.out.println("ShadowCamera#reconnect");
        locked = true;
    }

    @Implementation
    protected Camera.Parameters getParameters() {
        System.out.println("ShadowCamera#getParameters");
        if (null == parameters) {
            parameters = newInstanceOf(Camera.Parameters.class);
        }
        return parameters;
    }

    @Implementation
    protected void setParameters(Camera.Parameters params) {
        System.out.println("ShadowCamera#setParameters");
        parameters = params;
    }

    @Implementation
    protected void setPreviewDisplay(SurfaceHolder holder) {
        System.out.println("ShadowCamera#setPreviewDisplay");
        surfaceHolder = holder;
    }

    @Implementation
    protected void startPreview() {
        System.out.println("ShadowCamera#startPreview");
        previewing = true;
    }

    @Implementation
    protected void stopPreview() {
        System.out.println("ShadowCamera#stopPreview");
        previewing = false;
    }

    @Implementation
    protected void release() {
        System.out.println("ShadowCamera#release");
        released = true;
    }

    @Implementation
    protected void setPreviewCallback(Camera.PreviewCallback cb) {
        System.out.println("ShadowCamera#setPreviewCallback");
        previewCallback = cb;
    }

    @Implementation
    protected void setOneShotPreviewCallback(Camera.PreviewCallback cb) {
        System.out.println("ShadowCamera#setOneShotPreviewCallback");
        previewCallback = cb;
    }

    @Implementation
    protected void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb) {
        System.out.println("ShadowCamera#setPreviewCallbackWithBuffer");
        previewCallback = cb;
    }

    /**
     * Allows test cases to invoke the preview callback, to simulate a frame of camera data.
     *
     * @param data byte buffer of simulated camera data
     */
    public void invokePreviewCallback(byte[] data) {
        if (previewCallback != null) {
            previewCallback.onPreviewFrame(data, realCamera);
        }
    }

    @Implementation
    protected void addCallbackBuffer(byte[] callbackBuffer) {
        System.out.println("ShadowCamera#addCallbackBuffer");
        callbackBuffers.add(callbackBuffer);
    }

    public List<byte[]> getAddedCallbackBuffers() {
        return Collections.unmodifiableList(callbackBuffers);
    }

    @Implementation
    protected void setDisplayOrientation(int degrees) {
        System.out.println("ShadowCamera#setDisplayOrientation");
        displayOrientation = degrees;
        if (cameras.containsKey(id)) {
            cameras.get(id).orientation = degrees;
        }
    }

    public int getDisplayOrientation() {
        return displayOrientation;
    }

    @Implementation
    protected void autoFocus(Camera.AutoFocusCallback callback) {
        System.out.println("ShadowCamera#autoFocus");
        autoFocusCallback = callback;
        autoFocusing = true;
    }

    @Implementation
    protected void cancelAutoFocus() {
        System.out.println("ShadowCamera#cancelAutoFocus");
        autoFocusCallback = null;
        autoFocusing = false;
    }

    public boolean hasRequestedAutoFocus() {
        return autoFocusing;
    }

    public void invokeAutoFocusCallback(boolean success, Camera camera) {
        if (autoFocusCallback == null) {
            throw new IllegalStateException("cannot invoke AutoFocusCallback before autoFocus() has been called " + "or after cancelAutoFocus() has been called " + "or after the callback has been invoked.");
        }
        autoFocusCallback.onAutoFocus(success, camera);
        autoFocusCallback = null;
        autoFocusing = false;
    }

    @Implementation
    protected static void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo) {
        System.out.println("ShadowCamera#getCameraInfo");
        Camera.CameraInfo foundCam = cameras.get(cameraId);
        cameraInfo.facing = foundCam.facing;
        cameraInfo.orientation = foundCam.orientation;
    }

    @Implementation
    protected static int getNumberOfCameras() {
        System.out.println("ShadowCamera#getNumberOfCameras");
        return cameras.size();
    }

    @Implementation
    protected void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg) {
        System.out.println("ShadowCamera#takePicture");
        if (shutter != null) {
            shutter.onShutter();
        }
        if (raw != null) {
            raw.onPictureTaken(new byte[0], realCamera);
        }
        if (jpeg != null) {
            jpeg.onPictureTaken(new byte[0], realCamera);
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isPreviewing() {
        return previewing;
    }

    public boolean isReleased() {
        return released;
    }

    public SurfaceHolder getPreviewDisplay() {
        return surfaceHolder;
    }

    /**
     * Add a mock {@code Camera.CameraInfo} object to simulate the existence of one or more cameras.
     * By default, no cameras are defined.
     *
     * @param id The camera id
     * @param camInfo The CameraInfo
     */
    public static void addCameraInfo(int id, Camera.CameraInfo camInfo) {
        cameras.put(id, camInfo);
    }

    public static void clearCameraInfo() {
        cameras.clear();
    }

    /**
     * Shadows the Android {@code Camera.Parameters} class.
     */
    @Implements(Camera.Parameters.class)
    public static class ShadowParameters {

        private int pictureWidth = 1280;

        private int pictureHeight = 960;

        private int previewWidth = 640;

        private int previewHeight = 480;

        private int previewFormat = ImageFormat.NV21;

        private int previewFpsMin = 10;

        private int previewFpsMax = 30;

        private int previewFps = 30;

        private int exposureCompensation = 0;

        private String flashMode;

        private String focusMode;

        private List<String> supportedFlashModes = new ArrayList<>();

        private List<String> supportedFocusModes = new ArrayList<>();

        private static List<Camera.Size> supportedPreviewSizes;

        /**
         * Explicitly initialize custom preview sizes array, to switch from default values to
         * individually added.
         */
        public void initSupportedPreviewSizes() {
            supportedPreviewSizes = new ArrayList<>();
        }

        /**
         * Add custom preview sizes to supportedPreviewSizes.
         */
        public void addSupportedPreviewSize(int width, int height) {
            Camera.Size newSize = ReflectionHelpers.newInstance(Camera.class).new Size(width, height);
            supportedPreviewSizes.add(newSize);
        }

        @Implementation
        protected Camera.Size getPictureSize() {
            System.out.println("ShadowParameters#getPictureSize");
            Camera.Size pictureSize = newInstanceOf(Camera.class).new Size(0, 0);
            pictureSize.width = pictureWidth;
            pictureSize.height = pictureHeight;
            return pictureSize;
        }

        @Implementation
        protected int getPreviewFormat() {
            System.out.println("ShadowParameters#getPreviewFormat");
            return previewFormat;
        }

        @Implementation
        protected void getPreviewFpsRange(int[] range) {
            System.out.println("ShadowParameters#getPreviewFpsRange");
            range[0] = previewFpsMin;
            range[1] = previewFpsMax;
        }

        @Implementation
        protected int getPreviewFrameRate() {
            System.out.println("ShadowParameters#getPreviewFrameRate");
            return previewFps;
        }

        @Implementation
        protected Camera.Size getPreviewSize() {
            System.out.println("ShadowParameters#getPreviewSize");
            Camera.Size previewSize = newInstanceOf(Camera.class).new Size(0, 0);
            previewSize.width = previewWidth;
            previewSize.height = previewHeight;
            return previewSize;
        }

        @Implementation
        protected List<Camera.Size> getSupportedPictureSizes() {
            System.out.println("ShadowParameters#getSupportedPictureSizes");
            List<Camera.Size> supportedSizes = new ArrayList<>();
            addSize(supportedSizes, 320, 240);
            addSize(supportedSizes, 640, 480);
            addSize(supportedSizes, 800, 600);
            return supportedSizes;
        }

        @Implementation
        protected List<Integer> getSupportedPictureFormats() {
            System.out.println("ShadowParameters#getSupportedPictureFormats");
            List<Integer> formats = new ArrayList<>();
            formats.add(ImageFormat.NV21);
            formats.add(ImageFormat.JPEG);
            return formats;
        }

        @Implementation
        protected List<Integer> getSupportedPreviewFormats() {
            System.out.println("ShadowParameters#getSupportedPreviewFormats");
            List<Integer> formats = new ArrayList<>();
            formats.add(ImageFormat.NV21);
            formats.add(ImageFormat.JPEG);
            return formats;
        }

        @Implementation
        protected List<int[]> getSupportedPreviewFpsRange() {
            System.out.println("ShadowParameters#getSupportedPreviewFpsRange");
            List<int[]> supportedRanges = new ArrayList<>();
            addRange(supportedRanges, 15000, 15000);
            addRange(supportedRanges, 10000, 30000);
            return supportedRanges;
        }

        @Implementation
        protected List<Integer> getSupportedPreviewFrameRates() {
            System.out.println("ShadowParameters#getSupportedPreviewFrameRates");
            List<Integer> supportedRates = new ArrayList<>();
            supportedRates.add(10);
            supportedRates.add(15);
            supportedRates.add(30);
            return supportedRates;
        }

        @Implementation
        protected List<Camera.Size> getSupportedPreviewSizes() {
            System.out.println("ShadowParameters#getSupportedPreviewSizes");
            if (supportedPreviewSizes == null) {
                initSupportedPreviewSizes();
                addSupportedPreviewSize(320, 240);
                addSupportedPreviewSize(640, 480);
            }
            return supportedPreviewSizes;
        }

        public void setSupportedFocusModes(String... focusModes) {
            supportedFocusModes = Arrays.asList(focusModes);
        }

        @Implementation
        protected List<String> getSupportedFocusModes() {
            System.out.println("ShadowParameters#getSupportedFocusModes");
            return supportedFocusModes;
        }

        @Implementation
        protected String getFocusMode() {
            System.out.println("ShadowParameters#getFocusMode");
            return focusMode;
        }

        @Implementation
        protected void setFocusMode(String focusMode) {
            System.out.println("ShadowParameters#setFocusMode");
            this.focusMode = focusMode;
        }

        @Implementation
        protected void setPictureSize(int width, int height) {
            System.out.println("ShadowParameters#setPictureSize");
            pictureWidth = width;
            pictureHeight = height;
        }

        @Implementation
        protected void setPreviewFormat(int pixel_format) {
            System.out.println("ShadowParameters#setPreviewFormat");
            previewFormat = pixel_format;
        }

        @Implementation
        protected void setPreviewFpsRange(int min, int max) {
            System.out.println("ShadowParameters#setPreviewFpsRange");
            previewFpsMin = min;
            previewFpsMax = max;
        }

        @Implementation
        protected void setPreviewFrameRate(int fps) {
            System.out.println("ShadowParameters#setPreviewFrameRate");
            previewFps = fps;
        }

        @Implementation
        protected void setPreviewSize(int width, int height) {
            System.out.println("ShadowParameters#setPreviewSize");
            previewWidth = width;
            previewHeight = height;
        }

        @Implementation
        protected void setRecordingHint(boolean recordingHint) {
        }

        @Implementation
        protected void setRotation(int rotation) {
        }

        @Implementation
        protected int getMinExposureCompensation() {
            System.out.println("ShadowParameters#getMinExposureCompensation");
            return -6;
        }

        @Implementation
        protected int getMaxExposureCompensation() {
            System.out.println("ShadowParameters#getMaxExposureCompensation");
            return 6;
        }

        @Implementation
        protected float getExposureCompensationStep() {
            System.out.println("ShadowParameters#getExposureCompensationStep");
            return 0.5f;
        }

        @Implementation
        protected int getExposureCompensation() {
            System.out.println("ShadowParameters#getExposureCompensation");
            return exposureCompensation;
        }

        @Implementation
        protected void setExposureCompensation(int compensation) {
            System.out.println("ShadowParameters#setExposureCompensation");
            exposureCompensation = compensation;
        }

        public void setSupportedFlashModes(String... flashModes) {
            supportedFlashModes = Arrays.asList(flashModes);
        }

        @Implementation
        protected List<String> getSupportedFlashModes() {
            System.out.println("ShadowParameters#getSupportedFlashModes");
            return supportedFlashModes;
        }

        @Implementation
        protected String getFlashMode() {
            System.out.println("ShadowParameters#getFlashMode");
            return flashMode;
        }

        @Implementation
        protected void setFlashMode(String flashMode) {
            System.out.println("ShadowParameters#setFlashMode");
            this.flashMode = flashMode;
        }

        public int getPreviewWidth() {
            return previewWidth;
        }

        public int getPreviewHeight() {
            return previewHeight;
        }

        public int getPictureWidth() {
            return pictureWidth;
        }

        public int getPictureHeight() {
            return pictureHeight;
        }

        private void addSize(List<Camera.Size> sizes, int width, int height) {
            Camera.Size newSize = newInstanceOf(Camera.class).new Size(0, 0);
            newSize.width = width;
            newSize.height = height;
            sizes.add(newSize);
        }

        private void addRange(List<int[]> ranges, int min, int max) {
            int[] range = new int[2];
            range[0] = min;
            range[1] = max;
            ranges.add(range);
        }
    }

    @Implements(Camera.Size.class)
    public static class ShadowSize {

        @RealObject
        private Camera.Size realCameraSize;

        @Implementation
        protected void __constructor__(Camera camera, int width, int height) {
            System.out.println("ShadowSize#__constructor__");
            realCameraSize.width = width;
            realCameraSize.height = height;
        }
    }
}

