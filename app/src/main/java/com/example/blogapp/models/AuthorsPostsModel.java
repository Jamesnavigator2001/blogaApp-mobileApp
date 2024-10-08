package com.example.blogapp.models;

public class AuthorsPostsModel {
    private String id;
    private String title;
    private String body;
    private String fileUpload;
    private String fileName;
    private String imageUrl;
    private int viewsCount;
    private String authorsUsername;
    private String publish;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setAuthorsUsername(String authorsUsername) {
        this.authorsUsername = authorsUsername;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String getAuthorsUsername() {
        return authorsUsername;
    }

    public String getPublish() {
        return publish;
    }
}
