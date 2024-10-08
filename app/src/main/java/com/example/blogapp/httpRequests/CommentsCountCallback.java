package com.example.blogapp.httpRequests;

public interface CommentsCountCallback {
    void onCommentsCountFetched(int count);
    void onError(Throwable t);
}
