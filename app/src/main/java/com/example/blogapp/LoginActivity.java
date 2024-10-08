package com.example.blogapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blogapp.adapters.SharedPreferenceHelper;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.RequestHandler;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.utils.StatusNavigationBackground;
import com.example.blogapp.validators.FormValidation;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText registrationNumberInput, passwordInput;
    private Button loginButton;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private TextView errorMessage;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        Window window = getWindow();
        StatusNavigationBackground.setBackground(this, R.color.deepFadingSkyBlue, R.color.deepFadingSkyBlue);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        errorMessage = findViewById(R.id.errorMessage);
        registrationNumberInput = findViewById(R.id.registeredNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginBtn);

        FormValidation formValidation;
        FormValidation.setTextWatcher(registrationNumberInput,"Registration number is required");
        FormValidation.setTextWatcher(passwordInput,"Password is required");

        loginButton.setOnClickListener(view -> {
            boolean isRegistrationNumberEmpty = FormValidation.isInputEmpty(registrationNumberInput,"Registration Number is required");
            boolean isPasswordEmpty = FormValidation.isInputEmpty(passwordInput,"Password is required");

            if(!isRegistrationNumberEmpty && !isPasswordEmpty){
                String registrationNumber = registrationNumberInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                loginStudent(registrationNumber, password);
            }
        });
    }

    private void loginStudent(String registrationNumber , String password){
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        RequestHandler.LoginRequest loginRequest = new RequestHandler.LoginRequest(registrationNumber, password);

        Call<ResponseHandler.LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<ResponseHandler.LoginResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseHandler.LoginResponse> call, Response<ResponseHandler.LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LOGIN", "Response: " + response.body().toString());  // Add this to see the full response

                    ResponseHandler.LoginResponse.LoginData loginData = response.body().getData();
                    if (loginData != null) {
                        String token = loginData.getAccessToken();
                        String registrationNumber = loginData.getRegistrationNumber();
                        String userName = loginData.getEmail();
                        String refreshToken = loginData.getRefreshToken();
                        List<ResponseHandler.LoginResponse.Course> courses = loginData.getCourses();
                        long expiryTime = System.currentTimeMillis() + (4 * 60 * 1000);

                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        sharedPreferenceHelper.saveUserData(registrationNumber, userName, token,courses);
                        sharedPreferenceHelper.saveTokenExpiry(expiryTime);
                        Log.d("reg_save", "Saving registration number: " + registrationNumber);

                        navigateToActivity(new LandingActivity());
                    } else {
                        // If loginData is null
                        Log.e("LOGIN", "Login data is null");
                        Toast.makeText(LoginActivity.this, "Login data is missing", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Response was not successful, log the error body
                    try {
                        Log.e("LOGIN", "Response failed: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(LoginActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                    errorMessage.setText("Incorrect Login Credentials");
                    errorMessage.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(() -> {
                        errorMessage.animate()
                                .alpha(0f)
                                .setDuration(3000)
                                .withEndAction(() -> errorMessage.setVisibility(View.GONE));
                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<ResponseHandler.LoginResponse> call, Throwable t) {
                Log.e("LOGIN", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToActivity(Activity activity){
        Intent intent = new Intent(LoginActivity.this,activity.getClass());
        startActivity(intent);
        finish();
    }
}