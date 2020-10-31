package org.robolectric.shadows;

import android.os.Looper;
import android.widget.OverScroller;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.Scheduler;

@Implements(OverScroller.class)
public class ShadowOverScroller {

    private int startX;

    private int startY;

    private int finalX;

    private int finalY;

    private long startTime;

    private long duration;

    private boolean started;

    @Implementation
    protected int getStartX() {
        System.out.println("ShadowOverScroller#getStartX");
        return startX;
    }

    @Implementation
    protected int getStartY() {
        System.out.println("ShadowOverScroller#getStartY");
        return startY;
    }

    @Implementation
    protected int getCurrX() {
        System.out.println("ShadowOverScroller#getCurrX");
        long dt = deltaTime();
        return dt >= duration ? finalX : startX + (int) ((deltaX() * dt) / duration);
    }

    @Implementation
    protected int getCurrY() {
        System.out.println("ShadowOverScroller#getCurrY");
        long dt = deltaTime();
        return dt >= duration ? finalY : startY + (int) ((deltaY() * dt) / duration);
    }

    @Implementation
    protected int getFinalX() {
        System.out.println("ShadowOverScroller#getFinalX");
        return finalX;
    }

    @Implementation
    protected int getFinalY() {
        System.out.println("ShadowOverScroller#getFinalY");
        return finalY;
    }

    @Implementation
    protected int getDuration() {
        System.out.println("ShadowOverScroller#getDuration");
        return (int) duration;
    }

    @Implementation
    protected void startScroll(int startX, int startY, int dx, int dy, int duration) {
        System.out.println("ShadowOverScroller#startScroll");
        this.startX = startX;
        this.startY = startY;
        finalX = startX + dx;
        finalY = startY + dy;
        startTime = getScheduler().getCurrentTime();
        this.duration = duration;
        started = true;
        // post a task so that the scheduler will actually run
        getScheduler().postDelayed(new Runnable() {

            @Override
            public void run() {
            // do nothing
            }
        }, duration);
    }

    @Implementation
    protected void abortAnimation() {
        System.out.println("ShadowOverScroller#abortAnimation");
        duration = deltaTime() - 1;
    }

    @Implementation
    protected void forceFinished(boolean finished) {
        System.out.println("ShadowOverScroller#forceFinished");
        if (!finished) {
            throw new RuntimeException("Not implemented.");
        }
        finalX = getCurrX();
        finalY = getCurrY();
        duration = deltaTime() - 1;
    }

    @Implementation
    protected boolean computeScrollOffset() {
        System.out.println("ShadowOverScroller#computeScrollOffset");
        if (!started) {
            return false;
        }
        started &= deltaTime() < duration;
        return true;
    }

    @Implementation
    protected boolean isFinished() {
        System.out.println("ShadowOverScroller#isFinished");
        return deltaTime() > duration;
    }

    @Implementation
    protected int timePassed() {
        System.out.println("ShadowOverScroller#timePassed");
        return (int) deltaTime();
    }

    @Implementation
    protected boolean isScrollingInDirection(float xvel, float yvel) {
        System.out.println("ShadowOverScroller#isScrollingInDirection");
        final int dx = finalX - startX;
        final int dy = finalY - startY;
        return !isFinished() && Math.signum(xvel) == Math.signum(dx) && Math.signum(yvel) == Math.signum(dy);
    }

    private long deltaTime() {
        return getScheduler().getCurrentTime() - startTime;
    }

    private Scheduler getScheduler() {
        ShadowLooper shadowLooper = Shadow.extract(Looper.getMainLooper());
        return shadowLooper.getScheduler();
    }

    private int deltaX() {
        return (finalX - startX);
    }

    private int deltaY() {
        return (finalY - startY);
    }
}

