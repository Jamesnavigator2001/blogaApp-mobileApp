package com.example.blogapp.httpRequests;

public class RequestHandler {
    public static class LoginRequest{
        private String registration_number;
        private String password;

        public LoginRequest(String registrationNumber, String password){
            this.registration_number = registrationNumber;
            this.password = password;
        }
    }

    public static class PostDetailRequest{
        private int postId;
        private String token;

        public PostDetailRequest(int postId , String token){
            this.postId = postId;
            this.token = token;
        }
    }

    public static class CommentRequestBody {
        private String post;
        private String registration_number;
        private String body;

        // Constructor
        public CommentRequestBody(String post, String registration_number, String body) {
            this.post = post;
            this.registration_number = registration_number;
            this.body = body;
        }

        // Getters and setters
        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getRegistration_number() {
            return registration_number;
        }

        public void setRegistration_number(String registration_number) {
            this.registration_number = registration_number;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
