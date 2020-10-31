package org.robolectric.shadows;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(AnimationUtils.class)
public class ShadowAnimationUtils {

    @Implementation
    protected static Interpolator loadInterpolator(Context context, int id) {
        System.out.println("ShadowAnimationUtils#loadInterpolator");
        return new LinearInterpolator();
    }

    @Implementation
    protected static LayoutAnimationController loadLayoutAnimation(Context context, int id) {
        System.out.println("ShadowAnimationUtils#loadLayoutAnimation");
        Animation anim = new TranslateAnimation(0, 0, 30, 0);
        LayoutAnimationController layoutAnim = new LayoutAnimationController(anim);
        ShadowLayoutAnimationController shadowLayoutAnimationController = Shadow.extract(layoutAnim);
        shadowLayoutAnimationController.setLoadedFromResourceId(id);
        return layoutAnim;
    }
}

