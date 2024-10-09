package com.example.blogapp.httpRequests;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/students/login/")
    Call<ResponseHandler.LoginResponse> login(@Body RequestHandler.LoginRequest loginRequest);

    @GET("/api/posts/")
    Call<ResponseHandler.PostsListResponse> getAllPosts(@Header("Authorization") String token);

    @GET("api/post/{id}")
    Call<ResponseHandler.PostDetailResponse> getPostById(@Path("id") String postId, @Header("Authorization") String token);

    @POST("/api/post/{id}/comment/")
    Call<ResponseHandler.CommentResponse> postComment(@Path("id") String postId, @Header("Authorization") String token, @Body RequestHandler.CommentRequestBody commentRequestBody);

    @GET("/api/comments/{post_id}")
    Call<ResponseHandler.AllCommentsResponse> getComments(@Path("post_id") String PostId);

    @POST("api/post/{postId}/view/{registration_number}/")
    Call<ResponseHandler.ViewResponse> recordPostView(@Path("postId") String postId, @Path("registration_number") String registrationNumber);

    @GET("api/posts/course-specific-posts/{course_id}/")
    Call<ResponseHandler.CourseSpecificPosts> courseSpecificPosts( @Header("Authorization") String token , @Path("course_id") String courseId);

    @GET("api/posts/{author_email}/")
    Call<ResponseHandler.AuthorPostsResponse> authorsPost(@Header("Authorization") String token , @Path("author_email") String authorEmail);

    @GET("/api/search/")
    Call<ResponseHandler.SearchResponse> searchTeachersAndPosts(@Query("q") String query , @Query("search_type") String searchType);
}
