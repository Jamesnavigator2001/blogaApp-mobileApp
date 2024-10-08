package com.example.blogapp.models;


public class CommentModel {
    private  String id;
    private String createdAt;
    private String body;
    private int commentsCount;
    private  boolean active;
    private String registrationNumber;
    private String email;

    public CommentModel(){

    }

    public CommentModel(String id, String body, String createdAt, int commentsCount, boolean active, String registrationNumber, String email){
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.commentsCount = commentsCount;
        this.active = active;
        this.registrationNumber = registrationNumber;
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return commentsCount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
