package com.example.blogapp.utils;

import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
public class StatusNavigationBackground {

    public static void setBackground(Activity activity, int statusBarColor, int navigationBarColor) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(statusBarColor));
        window.setNavigationBarColor(activity.getResources().getColor(navigationBarColor));


    }
}
