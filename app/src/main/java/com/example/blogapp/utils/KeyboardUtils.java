package com.example.blogapp.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class KeyboardUtils {

    public static void adjustForKeyboard(Activity activity, FrameLayout bottomSheetInternal, View container) {
        bottomSheetInternal.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                bottomSheetInternal.getWindowVisibleDisplayFrame(rect);
                int screenHeight = bottomSheetInternal.getRootView().getHeight();
                int keyboardHeight = screenHeight - rect.bottom;

                if (keyboardHeight > screenHeight * 0.15) { // Keyboard is visible
                    int containerHeight = container.getHeight();
                    int offset = keyboardHeight - containerHeight; // Adjust for container height
                    container.setTranslationY(-offset);  // Move container above the keyboard with offset
                } else {
                    container.setTranslationY(0); // Reset translation when keyboard is hidden
                }
            }
        });
    }
}
