package com.example.blogapp.models;

import java.util.Date;

public class CourseSpecificModel {
    private String id;
    private String title;
    private String body;
    private String authorUsername;
    private Date publish;
    private String image;
    private String status;
    private String fileUpload;
    private String fileName;
    private int viewsCount;
    private  String date;

    public String getDate() {
        return date;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Date getPublish() {
        return publish;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public String getFileName() {
        return fileName;
    }

    public int getViewsCount() {
        return viewsCount;
    }
}
