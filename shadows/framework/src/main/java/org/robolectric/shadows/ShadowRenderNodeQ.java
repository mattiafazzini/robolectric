package org.robolectric.shadows;

import android.graphics.RenderNode;
import android.os.Build;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = RenderNode.class, isInAndroidSdk = false, minSdk = Build.VERSION_CODES.Q)
public class ShadowRenderNodeQ {

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
    protected boolean setAlpha(float alpha) {
        System.out.println("ShadowRenderNodeQ#setAlpha");
        this.alpha = alpha;
        return true;
    }

    @Implementation
    protected float getAlpha() {
        System.out.println("ShadowRenderNodeQ#getAlpha");
        return alpha;
    }

    @Implementation
    protected boolean setCameraDistance(float cameraDistance) {
        System.out.println("ShadowRenderNodeQ#setCameraDistance");
        this.cameraDistance = cameraDistance;
        return true;
    }

    @Implementation
    protected float getCameraDistance() {
        System.out.println("ShadowRenderNodeQ#getCameraDistance");
        return cameraDistance;
    }

    @Implementation
    protected boolean setClipToOutline(boolean clipToOutline) {
        System.out.println("ShadowRenderNodeQ#setClipToOutline");
        this.clipToOutline = clipToOutline;
        return true;
    }

    @Implementation
    protected boolean getClipToOutline() {
        System.out.println("ShadowRenderNodeQ#getClipToOutline");
        return clipToOutline;
    }

    @Implementation
    protected boolean setElevation(float lift) {
        System.out.println("ShadowRenderNodeQ#setElevation");
        elevation = lift;
        return true;
    }

    @Implementation
    protected float getElevation() {
        System.out.println("ShadowRenderNodeQ#getElevation");
        return elevation;
    }

    @Implementation
    protected boolean setHasOverlappingRendering(boolean overlappingRendering) {
        System.out.println("ShadowRenderNodeQ#setHasOverlappingRendering");
        this.overlappingRendering = overlappingRendering;
        return true;
    }

    @Implementation
    protected boolean hasOverlappingRendering() {
        System.out.println("ShadowRenderNodeQ#hasOverlappingRendering");
        return overlappingRendering;
    }

    @Implementation
    protected boolean setRotationZ(float rotation) {
        System.out.println("ShadowRenderNodeQ#setRotationZ");
        this.rotation = rotation;
        return true;
    }

    @Implementation
    protected float getRotationZ() {
        System.out.println("ShadowRenderNodeQ#getRotationZ");
        return rotation;
    }

    @Implementation
    protected boolean setRotationX(float rotationX) {
        System.out.println("ShadowRenderNodeQ#setRotationX");
        this.rotationX = rotationX;
        return true;
    }

    @Implementation
    protected float getRotationX() {
        System.out.println("ShadowRenderNodeQ#getRotationX");
        return rotationX;
    }

    @Implementation
    protected boolean setRotationY(float rotationY) {
        System.out.println("ShadowRenderNodeQ#setRotationY");
        this.rotationY = rotationY;
        return true;
    }

    @Implementation
    protected float getRotationY() {
        System.out.println("ShadowRenderNodeQ#getRotationY");
        return rotationY;
    }

    @Implementation
    protected boolean setScaleX(float scaleX) {
        System.out.println("ShadowRenderNodeQ#setScaleX");
        this.scaleX = scaleX;
        return true;
    }

    @Implementation
    protected float getScaleX() {
        System.out.println("ShadowRenderNodeQ#getScaleX");
        return scaleX;
    }

    @Implementation
    protected boolean setScaleY(float scaleY) {
        System.out.println("ShadowRenderNodeQ#setScaleY");
        this.scaleY = scaleY;
        return true;
    }

    @Implementation
    protected float getScaleY() {
        System.out.println("ShadowRenderNodeQ#getScaleY");
        return scaleY;
    }

    @Implementation
    protected boolean setTranslationX(float translationX) {
        System.out.println("ShadowRenderNodeQ#setTranslationX");
        this.translationX = translationX;
        return true;
    }

    @Implementation
    protected boolean setTranslationY(float translationY) {
        System.out.println("ShadowRenderNodeQ#setTranslationY");
        this.translationY = translationY;
        return true;
    }

    @Implementation
    protected boolean setTranslationZ(float translationZ) {
        System.out.println("ShadowRenderNodeQ#setTranslationZ");
        this.translationZ = translationZ;
        return true;
    }

    @Implementation
    protected float getTranslationX() {
        System.out.println("ShadowRenderNodeQ#getTranslationX");
        return translationX;
    }

    @Implementation
    protected float getTranslationY() {
        System.out.println("ShadowRenderNodeQ#getTranslationY");
        return translationY;
    }

    @Implementation
    protected float getTranslationZ() {
        System.out.println("ShadowRenderNodeQ#getTranslationZ");
        return translationZ;
    }

    @Implementation
    protected boolean isPivotExplicitlySet() {
        System.out.println("ShadowRenderNodeQ#isPivotExplicitlySet");
        return pivotExplicitlySet;
    }

    @Implementation
    protected boolean setPivotX(float pivotX) {
        System.out.println("ShadowRenderNodeQ#setPivotX");
        this.pivotX = pivotX;
        this.pivotExplicitlySet = true;
        return true;
    }

    @Implementation
    protected float getPivotX() {
        System.out.println("ShadowRenderNodeQ#getPivotX");
        return pivotX;
    }

    @Implementation
    protected boolean setPivotY(float pivotY) {
        System.out.println("ShadowRenderNodeQ#setPivotY");
        this.pivotY = pivotY;
        this.pivotExplicitlySet = true;
        return true;
    }

    @Implementation
    protected float getPivotY() {
        System.out.println("ShadowRenderNodeQ#getPivotY");
        return pivotY;
    }

    @Implementation
    protected static boolean nIsValid(long n) {
        System.out.println("ShadowRenderNodeQ#nIsValid");
        return true;
    }
}

