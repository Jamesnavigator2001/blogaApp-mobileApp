package com.example.blogapp.models;

import java.util.Date;

public class PostsListModal {
    private String id;
    private String title;
    private String body;
    private String fileName;
    private String authorUsername;
    private Date date;
    private  String file_upload_url;
    private String image;
    private String status;

    public String getId() {
        return id;
    }

    public String getFileUploadUrl() {
        return file_upload_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileUploadUrl(String file_upload_url) {
        this.file_upload_url = file_upload_url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
