package org.robolectric.shadows;

import android.widget.Scroller;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Scroller.class)
public class ShadowScroller {

    private int startX;

    private int startY;

    private int finalX;

    private int finalY;

    private long startTime;

    private long duration;

    private boolean started;

    @Implementation
    protected int getStartX() {
        System.out.println("ShadowScroller#getStartX");
        return startX;
    }

    @Implementation
    protected int getStartY() {
        System.out.println("ShadowScroller#getStartY");
        return startY;
    }

    @Implementation
    protected int getCurrX() {
        System.out.println("ShadowScroller#getCurrX");
        long dt = deltaTime();
        return dt >= duration ? finalX : startX + (int) ((deltaX() * dt) / duration);
    }

    @Implementation
    protected int getCurrY() {
        System.out.println("ShadowScroller#getCurrY");
        long dt = deltaTime();
        return dt >= duration ? finalY : startY + (int) ((deltaY() * dt) / duration);
    }

    @Implementation
    protected int getFinalX() {
        System.out.println("ShadowScroller#getFinalX");
        return finalX;
    }

    @Implementation
    protected int getFinalY() {
        System.out.println("ShadowScroller#getFinalY");
        return finalY;
    }

    @Implementation
    protected int getDuration() {
        System.out.println("ShadowScroller#getDuration");
        return (int) duration;
    }

    @Implementation
    protected void startScroll(int startX, int startY, int dx, int dy, int duration) {
        System.out.println("ShadowScroller#startScroll");
        this.startX = startX;
        this.startY = startY;
        finalX = startX + dx;
        finalY = startY + dy;
        startTime = ShadowApplication.getInstance().getForegroundThreadScheduler().getCurrentTime();
        this.duration = duration;
        started = true;
        // enque a dummy task so that the scheduler will actually run
        ShadowApplication.getInstance().getForegroundThreadScheduler().postDelayed(new Runnable() {

            @Override
            public void run() {
            // do nothing
            }
        }, duration);
    }

    @Implementation
    protected boolean computeScrollOffset() {
        System.out.println("ShadowScroller#computeScrollOffset");
        if (!started) {
            return false;
        }
        started &= deltaTime() < duration;
        return true;
    }

    @Implementation
    protected boolean isFinished() {
        System.out.println("ShadowScroller#isFinished");
        return deltaTime() > duration;
    }

    @Implementation
    protected int timePassed() {
        System.out.println("ShadowScroller#timePassed");
        return (int) deltaTime();
    }

    private long deltaTime() {
        return ShadowApplication.getInstance().getForegroundThreadScheduler().getCurrentTime() - startTime;
    }

    private int deltaX() {
        return (finalX - startX);
    }

    private int deltaY() {
        return (finalY - startY);
    }
}

