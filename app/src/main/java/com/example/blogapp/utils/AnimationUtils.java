package com.example.blogapp.utils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class AnimationUtils {

    public static boolean isShrunk = false;

    public static void animateCardContainer(ConstraintLayout constraintLayout, Runnable onAnimationEnd) {
        if (!isShrunk) {
            constraintLayout.setPivotX(constraintLayout.getWidth() / 2);
            constraintLayout.setPivotY(0);

            constraintLayout.animate()
                    .scaleX(0.3f)   // Shrink width to 50% of original size
                    .scaleY(0.3f)   // Shrink height to 50% of original size
                    .setDuration(400)   // Duration of animation
                    .setInterpolator(new AccelerateDecelerateInterpolator()) // Smoother animation
                    .withEndAction(() -> {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();
            isShrunk = true;
        } else {
            // Restore the cardContainer to its original size
            constraintLayout.animate()
                    .scaleX(1f)   // Restore width to original size
                    .scaleY(1f)   // Restore height to original size
                    .setDuration(400)   // Duration of animation
                    .setInterpolator(new AccelerateDecelerateInterpolator()) // Smoother animation
                    .withEndAction(() -> {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();
            isShrunk = false;
        }
    }

    public static void resetCardContainer(ConstraintLayout constraintLayout) {
        // Reset the layout size
        constraintLayout.setPivotX(constraintLayout.getWidth() / 2);
        constraintLayout.setPivotY(0);

        // Animate back to original size
        constraintLayout.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .start();
    }


    public static void toggleCommentSectionWithAnimation(ScrollView constraintLayout, Runnable onAnimationEnd) {
        if (constraintLayout.getVisibility() == View.GONE) {
            constraintLayout.setVisibility(View.VISIBLE);
            constraintLayout.animate()
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .withEndAction(() -> {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();
        } else {
            constraintLayout.animate()
                    .translationY(constraintLayout.getHeight())
                    .alpha(0)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .withEndAction(() -> {
                        constraintLayout.setVisibility(View.GONE);
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();
        }
    }


    public static void toggleEditTextAndPostButtonWithAnimation(TextView text, Button button, Runnable onAnimationEnd) {
        if (text.getVisibility() == View.GONE) {
            text.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);

            text.animate()
                    .alpha(1)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .withEndAction(() -> {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();

            button.animate()
                    .alpha(1)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .start();
        } else {
            text.animate()
                    .alpha(0)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .withEndAction(() -> {
                        text.setVisibility(View.GONE);
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    })
                    .start();

            button.animate()
                    .alpha(0)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator()) // Smooth transition
                    .withEndAction(() -> button.setVisibility(View.GONE))
                    .start();
        }
    }

}
