package com.example.blogapp.httpRequests;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://172.17.17.169:8000/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);  // Logs request and response bodies


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)  // Add the logging interceptor
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)  // Set the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())  // Converts JSON into Java objects
                    .build();
        }
        return retrofit;
    }
}
