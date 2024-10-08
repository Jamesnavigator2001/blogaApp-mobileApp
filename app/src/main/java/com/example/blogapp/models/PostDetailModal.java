package com.example.blogapp.models;

import java.util.Date;

public class PostDetailModal {
    private int id;
    private String title;
    private String body;
    private String authorUsername;
    private Date date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    private String image;
    private String status;
}
