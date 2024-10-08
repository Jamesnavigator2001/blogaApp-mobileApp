package com.example.blogapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.adapters.AuthorPostsAdapter;
import com.example.blogapp.adapters.SharedPreferenceHelper;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.models.AuthorsPostsModel;
import com.example.blogapp.utils.StatusNavigationBackground;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorsPostsActivity extends AppCompatActivity {

    private SharedPreferenceHelper sharedPreferenceHelper;
    private ApiService apiService;
    private TextView userName;
    private TextView postsCount;
    private ArrayList<AuthorsPostsModel> authorsPostsModal;
    private AuthorPostsAdapter authorPostsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors_posts);
        StatusNavigationBackground.setBackground(this, R.color.deepFadingSkyBlue, R.color.deepFadingSkyBlue);


        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        authorsPostsModal = new ArrayList<>();
        authorPostsAdapter  = new AuthorPostsAdapter(this, authorsPostsModal);
        Button backBtn = findViewById(R.id.backBtn);
        userName = findViewById(R.id.userName);
        postsCount = findViewById(R.id.postsCount);

        Intent intent = getIntent();
        userName.setText(intent.getStringExtra("authorName"));

        RecyclerView recyclerView = findViewById(R.id.authorPostsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(authorPostsAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.d("AuthorsName: ", userName.getText().toString());
        fetchAuthorsPosts();
    }

    private void fetchAuthorsPosts(){
        String token = "Bearer " + sharedPreferenceHelper.getAccessToken();
        String authorsEmail = userName.getText().toString();
        Call<ResponseHandler.AuthorPostsResponse> call = apiService.authorsPost(token, authorsEmail);
        call.enqueue(new Callback<ResponseHandler.AuthorPostsResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseHandler.AuthorPostsResponse> call, Response<ResponseHandler.AuthorPostsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    postsCount.setText(String.valueOf(response.body().getCount()));
                    Log.d("Count: ", String.valueOf(response.body().getCount()));

                    authorsPostsModal.clear();
                    for(ResponseHandler.AuthorPostsResponse.AuthorPostsData data : response.body().getData()){
                        AuthorsPostsModel model = new AuthorsPostsModel();
                        model.setId(data.getId());
                        model.setAuthorsUsername(data.getAuthors_username());
                        model.setBody(data.getBody());
                        model.setTitle(data.getTitle());
                        model.setPublish(data.getPublish());
                        model.setImageUrl(data.getImage());
                        model.setViewsCount(data.getViews_count());
                        model.setFileName(data.getFile_name());
                        model.setFileUpload(data.getFile_upload());
                        authorsPostsModal.add(model);
                    }
                    authorPostsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseHandler.AuthorPostsResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
