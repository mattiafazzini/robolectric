package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import android.view.RenderNode;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = RenderNode.class, isInAndroidSdk = false, minSdk = LOLLIPOP)
public class ShadowRenderNode {

    private float alpha = 1f;

    private float cameraDistance;

    private boolean clipToOutline;

    private float elevation;

    private boolean overlappingRendering;

    private boolean pivotExplicitlySet;

    private float pivotX;

    private float pivotY;

    private float rotation;

    private float rotationX;

    private float rotationY;

    private float scaleX = 1f;

    private float scaleY = 1f;

    private float translationX;

    private float translationY;

    private float translationZ;

    @Implementation
    public boolean setAlpha(float alpha) {
        System.out.println("ShadowRenderNode#setAlpha");
        this.alpha = alpha;
        return true;
    }

    @Implementation
    public float getAlpha() {
        System.out.println("ShadowRenderNode#getAlpha");
        return alpha;
    }

    @Implementation
    public boolean setCameraDistance(float cameraDistance) {
        System.out.println("ShadowRenderNode#setCameraDistance");
        this.cameraDistance = cameraDistance;
        return true;
    }

    @Implementation
    public float getCameraDistance() {
        System.out.println("ShadowRenderNode#getCameraDistance");
        return cameraDistance;
    }

    @Implementation
    public boolean setClipToOutline(boolean clipToOutline) {
        System.out.println("ShadowRenderNode#setClipToOutline");
        this.clipToOutline = clipToOutline;
        return true;
    }

    @Implementation
    public boolean getClipToOutline() {
        System.out.println("ShadowRenderNode#getClipToOutline");
        return clipToOutline;
    }

    @Implementation
    public boolean setElevation(float lift) {
        System.out.println("ShadowRenderNode#setElevation");
        elevation = lift;
        return true;
    }

    @Implementation
    public float getElevation() {
        System.out.println("ShadowRenderNode#getElevation");
        return elevation;
    }

    @Implementation
    public boolean setHasOverlappingRendering(boolean overlappingRendering) {
        System.out.println("ShadowRenderNode#setHasOverlappingRendering");
        this.overlappingRendering = overlappingRendering;
        return true;
    }

    @Implementation
    public boolean hasOverlappingRendering() {
        System.out.println("ShadowRenderNode#hasOverlappingRendering");
        return overlappingRendering;
    }

    @Implementation
    public boolean setRotation(float rotation) {
        System.out.println("ShadowRenderNode#setRotation");
        this.rotation = rotation;
        return true;
    }

    @Implementation
    public float getRotation() {
        System.out.println("ShadowRenderNode#getRotation");
        return rotation;
    }

    @Implementation
    public boolean setRotationX(float rotationX) {
        System.out.println("ShadowRenderNode#setRotationX");
        this.rotationX = rotationX;
        return true;
    }

    @Implementation
    public float getRotationX() {
        System.out.println("ShadowRenderNode#getRotationX");
        return rotationX;
    }

    @Implementation
    public boolean setRotationY(float rotationY) {
        System.out.println("ShadowRenderNode#setRotationY");
        this.rotationY = rotationY;
        return true;
    }

    @Implementation
    public float getRotationY() {
        System.out.println("ShadowRenderNode#getRotationY");
        return rotationY;
    }

    @Implementation
    public boolean setScaleX(float scaleX) {
        System.out.println("ShadowRenderNode#setScaleX");
        this.scaleX = scaleX;
        return true;
    }

    @Implementation
    public float getScaleX() {
        System.out.println("ShadowRenderNode#getScaleX");
        return scaleX;
    }

    @Implementation
    public boolean setScaleY(float scaleY) {
        System.out.println("ShadowRenderNode#setScaleY");
        this.scaleY = scaleY;
        return true;
    }

    @Implementation
    public float getScaleY() {
        System.out.println("ShadowRenderNode#getScaleY");
        return scaleY;
    }

    @Implementation
    public boolean setTranslationX(float translationX) {
        System.out.println("ShadowRenderNode#setTranslationX");
        this.translationX = translationX;
        return true;
    }

    @Implementation
    public boolean setTranslationY(float translationY) {
        System.out.println("ShadowRenderNode#setTranslationY");
        this.translationY = translationY;
        return true;
    }

    @Implementation
    public boolean setTranslationZ(float translationZ) {
        System.out.println("ShadowRenderNode#setTranslationZ");
        this.translationZ = translationZ;
        return true;
    }

    @Implementation
    public float getTranslationX() {
        System.out.println("ShadowRenderNode#getTranslationX");
        return translationX;
    }

    @Implementation
    public float getTranslationY() {
        System.out.println("ShadowRenderNode#getTranslationY");
        return translationY;
    }

    @Implementation
    public float getTranslationZ() {
        System.out.println("ShadowRenderNode#getTranslationZ");
        return translationZ;
    }

    @Implementation
    public boolean isPivotExplicitlySet() {
        System.out.println("ShadowRenderNode#isPivotExplicitlySet");
        return pivotExplicitlySet;
    }

    @Implementation
    public boolean setPivotX(float pivotX) {
        System.out.println("ShadowRenderNode#setPivotX");
        this.pivotX = pivotX;
        this.pivotExplicitlySet = true;
        return true;
    }

    @Implementation
    public float getPivotX() {
        System.out.println("ShadowRenderNode#getPivotX");
        return pivotX;
    }

    @Implementation
    public boolean setPivotY(float pivotY) {
        System.out.println("ShadowRenderNode#setPivotY");
        this.pivotY = pivotY;
        this.pivotExplicitlySet = true;
        return true;
    }

    @Implementation
    public float getPivotY() {
        System.out.println("ShadowRenderNode#getPivotY");
        return pivotY;
    }
}

