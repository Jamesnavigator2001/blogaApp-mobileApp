package com.example.blogapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.blogapp.adapters.CommentsAdapter;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.CommentsCountCallback;
import com.example.blogapp.httpRequests.RequestHandler;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.models.CommentModel;
import com.example.blogapp.validators.CommentsValidator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment {
    private ApiService apiService;
    private  String token;
    private String registrationNumber;
    private String postId;
    private CommentsAdapter commentsAdapter;
    private Context context;
    public  Comment(){

    }

    public Comment(Context context,ApiService apiService, String token, String registrationNumber , String postId,CommentsAdapter commentsAdapter  ){
        this.context =context;
        this.apiService = apiService;
        this.registrationNumber = registrationNumber;
        this.token = token;
        this.postId = postId;
        this.commentsAdapter = commentsAdapter;

        if(this.commentsAdapter==null){
            this.commentsAdapter = new CommentsAdapter(new ArrayList<>());
        }
    }


    public void postComment(String commentBody) {

        RequestHandler.CommentRequestBody requestBody = new RequestHandler.CommentRequestBody(postId, registrationNumber, commentBody);

        Log.d("Comment", "Request Body: " + requestBody);

        Call<ResponseHandler.CommentResponse> call = apiService.postComment(postId, token, requestBody);

        call.enqueue(new Callback<ResponseHandler.CommentResponse>() {
            @Override
            public void onResponse(Call<ResponseHandler.CommentResponse> call, Response<ResponseHandler.CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Comment", "Comment posted successfully");
                } else {
                    Log.e("Comment", "Failed to post comment: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseHandler.CommentResponse> call, Throwable t) {
                Log.e("Comment", "Error posting comment: " + t.getMessage());
            }
        });
    }

    public void fetchComments(String postId) {
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseHandler.AllCommentsResponse> call = apiService.getComments(postId);
        call.enqueue(new Callback<ResponseHandler.AllCommentsResponse>() {
            @Override
            public void onResponse(Call<ResponseHandler.AllCommentsResponse> call, Response<ResponseHandler.AllCommentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    int commentsCount = response.body().getCount();
                    List<CommentModel> commentModelList = new ArrayList<>();

                    List<ResponseHandler.AllCommentsResponse.AllCommentsList> commentsList = response.body().getComments();
                    if (commentsList != null && !commentsList.isEmpty()) {
                        for (ResponseHandler.AllCommentsResponse.AllCommentsList commentItem : commentsList) {
                            CommentModel comment = new CommentModel(
                                    commentItem.getId(),
                                    commentItem.getBody(),
                                    commentItem.getCreated(),
                                    commentsCount,
                                    commentItem.isActive(),
                                    commentItem.getRegistration_number(),
                                    commentItem.getEmail()
                            );
                            commentModelList.add(comment);
                        }
                        if (commentsAdapter != null) {
                            commentsAdapter.updateComments(commentModelList);
                        } else {
                            Log.e("Comments", "commentsAdapter is null.");
                        }
                    } else {
                        Toast.makeText(context, "No comments found for this post.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Failed to fetch comments", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseHandler.AllCommentsResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchNumOfComments(String postId, final CommentsCountCallback callback){
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseHandler.AllCommentsResponse> call = apiService.getComments(postId);
        call.enqueue(new Callback<ResponseHandler.AllCommentsResponse>() {
            @Override
            public void onResponse(Call<ResponseHandler.AllCommentsResponse> call, Response<ResponseHandler.AllCommentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int commentsCount = response.body().getCount();
                    callback.onCommentsCountFetched(commentsCount);
                } else {
                    callback.onError(new Throwable("Failed to fetch comments count"));
                }
            }

            @Override
            public void onFailure(Call<ResponseHandler.AllCommentsResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }



}
