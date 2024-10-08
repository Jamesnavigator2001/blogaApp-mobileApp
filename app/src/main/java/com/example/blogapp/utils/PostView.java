package com.example.blogapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.ResponseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostView {

    public void recordPostView(String postId , String registrationNumber){
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseHandler.ViewResponse> call = apiService.recordPostView(postId , registrationNumber);

        call.enqueue(new Callback<ResponseHandler.ViewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResponseHandler.ViewResponse> call, @NonNull Response<ResponseHandler.ViewResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("PostView" , "View recorded successfully for post: " + postId);
                } else {
                    Log.e("PostView" , "Failed to record view for post: " + postId );
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHandler.ViewResponse> call, @NonNull Throwable t) {
                Log.e("PostView" , "Error recording post View" ,t);
            }
        });
    }
}
