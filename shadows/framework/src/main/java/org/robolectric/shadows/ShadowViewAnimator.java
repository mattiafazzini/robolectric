package org.robolectric.shadows;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ViewAnimator.class)
public class ShadowViewAnimator extends ShadowViewGroup {

    private int currentChild = 0;

    @Implementation
    protected int getDisplayedChild() {
        System.out.println("ShadowViewAnimator#getDisplayedChild");
        return currentChild;
    }

    @Implementation
    protected void setDisplayedChild(int whichChild) {
        System.out.println("ShadowViewAnimator#setDisplayedChild");
        currentChild = whichChild;
        for (int i = ((ViewGroup) realView).getChildCount() - 1; i >= 0; i--) {
            View child = ((ViewGroup) realView).getChildAt(i);
            child.setVisibility(i == whichChild ? View.VISIBLE : View.GONE);
        }
    }

    @Implementation
    protected View getCurrentView() {
        System.out.println("ShadowViewAnimator#getCurrentView");
        return ((ViewGroup) realView).getChildAt(getDisplayedChild());
    }

    @Implementation
    protected void showNext() {
        System.out.println("ShadowViewAnimator#showNext");
        setDisplayedChild((getDisplayedChild() + 1) % ((ViewGroup) realView).getChildCount());
    }

    @Implementation
    protected void showPrevious() {
        System.out.println("ShadowViewAnimator#showPrevious");
        setDisplayedChild(getDisplayedChild() == 0 ? ((ViewGroup) realView).getChildCount() - 1 : getDisplayedChild() - 1);
    }
}

