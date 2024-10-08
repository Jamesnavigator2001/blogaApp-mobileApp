package com.example.blogapp;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.blogapp.fragments.HomeFragment;
import com.example.blogapp.fragments.ProfileFragment;
import com.example.blogapp.fragments.SearchFragment;
import com.example.blogapp.utils.StatusNavigationBackground;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);
        StatusNavigationBackground.setBackground(this, R.color.deepFadingSkyBlue, R.color.deepFadingSkyBlue);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(view->{
            Fragment fragment = null;

            int id = view.getItemId();

            if(id == R.id.home_nav){
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            } else if (id==R.id.search_nav) {
                fragment = new SearchFragment();
                loadFragment(fragment);
                return true;
            } else{
                fragment = new ProfileFragment();
                loadFragment(fragment);
                return true;
            }
        });

    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}