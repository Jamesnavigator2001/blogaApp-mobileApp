package com.example.blogapp.validators;

public class CommentsValidator {
    public boolean isCommentNotEmpty(String commentBody){
        return  commentBody != null && !commentBody.trim().isEmpty();
    }

    public boolean isRegistrationNumberValid(String registrationNumber){
        return  registrationNumber != null && !registrationNumber.trim().isEmpty();
    }

    public boolean isTokenValid(String token){
        return token != null && token.startsWith("Bearer ");
    }

    public boolean validateComment(String commentBody, String registrationNumber, String token) {
        return isCommentNotEmpty(commentBody) && isRegistrationNumberValid(registrationNumber) && isTokenValid(token);
    }
}
