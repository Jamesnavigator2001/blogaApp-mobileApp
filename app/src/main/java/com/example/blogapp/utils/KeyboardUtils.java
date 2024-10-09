package com.example.blogapp.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KeyboardUtils {

    public static void adjustForKeyboard(Activity activity, FrameLayout bottomSheetInternal, View container) {
        bottomSheetInternal.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                bottomSheetInternal.getWindowVisibleDisplayFrame(rect);
                int screenHeight = bottomSheetInternal.getRootView().getHeight();
                int keyboardHeight = screenHeight - rect.bottom;

                if (keyboardHeight > screenHeight * 0.15) {
                    int containerHeight = container.getHeight();
                    int offset = keyboardHeight - containerHeight;
                    container.setTranslationY(-offset);
                } else {
                    container.setTranslationY(0);
                }
            }
        });
    }


    public static void adjustBottomNavigation(Activity activity, BottomNavigationView bottomNavigationView) {
        final View contentView = activity.findViewById(android.R.id.content);

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(contentView);
            if (insets != null) {
                int keyboardHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;

                if (keyboardHeight > 0) {
                    // Keyboard is shown
                    bottomNavigationView.setVisibility(View.GONE);
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                bottomNavigationView.requestLayout(); // Request layout update
            }
        });
    }
}
