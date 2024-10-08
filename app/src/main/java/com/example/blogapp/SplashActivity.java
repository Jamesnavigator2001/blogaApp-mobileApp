package com.example.blogapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blogapp.utils.StatusNavigationBackground;

import org.w3c.dom.Text;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static  final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        StatusNavigationBackground.setBackground(this, R.color.deepFadingSkyBlue, R.color.deepFadingSkyBlue);

        ImageView logo = findViewById(R.id.logo);
        TextView logoText = findViewById(R.id.logoText);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        logo.startAnimation(splashAnimation);
        logoText.startAnimation(splashAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_DURATION);
    }
}