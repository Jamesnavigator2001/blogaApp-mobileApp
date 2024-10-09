package com.example.blogapp.httpRequests;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ResponseHandler {
    public static class LoginResponse{
        private boolean error;
        private LoginData data;
        private String message;

        public LoginData getData(){return  data;}

        public String getMessage(){return message;}

        public static class LoginData{
            private String email;
            private String registrationNumber;
            private String accessToken;
            private String refreshToken;
            private List<Course> courses;

            public List<Course> getCourses() {
                return courses;
            }

            public String getRefreshToken() {
                return refreshToken;
            }

            public String getEmail() {
                return email;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public String getAccessToken() {
                return accessToken;
            }
        }

        public  static  class Course{
            private  String id;
            private  String name;
            private String code;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getCode() {
                return code;
            }
        }
    }



        // Class to handle the response for posts list
    public static class PostsListResponse {
        private boolean error;
        private String message;
        private List<PostData> data;
        private Pagination pagination;

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public List<PostData> getData() {  // Return list of posts
            return data;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public static class PostData {
            private String id;
            private String title;
            private String body;
            private String author_username;  // Matches JSON field exactly
            private Date publish;
            private String file_name;
            private String file_upload;
            private String image;
            private String status;

            public String getAuthor_username() {
                return author_username;
            }

            public String getFile_name() {
                return file_name;
            }

            public String getFile_upload() {
                return file_upload;
            }

            public String getPostId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getAuthorUsername() {
                return author_username;
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
        }

        // Pagination class remains unchanged
        public static class Pagination {
            private int current_page;
            private int total_pages;
            private int total_posts;
            private boolean has_next;
            private boolean has_previous;
            private String next;  // Changed to String since URL may not always be needed
            private String previous;

            public int getCurrentPage() {
                return current_page;
            }

            public int getTotalPages() {
                return total_pages;
            }

            public int getTotalPosts() {
                return total_posts;
            }

            public boolean isHasNext() {
                return has_next;
            }

            public boolean isHasPrevious() {
                return has_previous;
            }

            public String getNext() {
                return next;
            }

            public String getPrevious() {
                return previous;
            }
        }
    }

    public static class  PostDetailResponse{
        private boolean error;
        private String message;
        private PostDetailData postData;

        public PostDetailData getPostData() {
            return postData;
        }

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }


        public static class PostDetailData{
            private String id;
            private String title;
            private String body;
            private String author_username;  // Matches JSON field exactly
            private Date publish;  // Assuming the "publish" field is a Date
            private String image;
            private String status;

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getAuthor_username() {
                return author_username;
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
        }
    }

    public static  class CommentResponse{
        private boolean error;
        private int status;
        private String message;
        private CommentResponseData data;

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setData(CommentResponseData data) {
            this.data = data;
        }

        public CommentResponseData getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }


        public static class CommentResponseData{
            private String id;
            private int post;
            private String registration_number;
            private String body;
            private boolean active;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getPost() {
                return post;
            }

            public void setPost(int post) {
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

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }
        }

    }

    public static class AllCommentsResponse{
        private  boolean error;
        private  int count;
        private  List<AllCommentsList> comments;


        public List<AllCommentsList> getComments() {
            return comments;
        }

        public void setComments(List<AllCommentsList> comments) {
            this.comments = comments;
            this.count = (comments != null) ? comments.size() : 0;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public  static class AllCommentsList{
            private  String id;
            private String post;
            private String created;
            private String body;
            private boolean active;
            private String registration_number;
            private String email;

            public String getRegistration_number() {
                return registration_number;
            }

            public String getEmail() {
                return email;
            }

            public String getId() {
                return id;
            }

            public String getPost() {
                return post;
            }

            public String getBody() {
                return body;
            }

            public boolean isActive() {
                return active;
            }

            public String getCreated() {
                return formatDateTime(created);
            }

            // Method to format the date and time from the ISO 8601 string
            private String formatDateTime(String dateTime) {
                try {
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                    return zonedDateTime.format(formatter);

                } catch (Exception e) {
                    e.printStackTrace();
                    return dateTime;
                }
            }
        }
    }

    public static  class  ViewResponse{
        private String message;
        private ViewData data;

        public String getMessage() {
            return message;
        }

        public ViewData getData() {
            return data;
        }

        public  static  class ViewData{
            private int post;
            private String user;
        }
    }

    public static class CourseSpecificPosts{
        private boolean error;
        private  String message;
        private  List<CourseSpecificData> data;

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public List<CourseSpecificData> getData() {
            return data;
        }

        public  static class CourseSpecificData{
            private String id;
            private String title;
            private String body;
            private String author_username;
            private String publish;
            private String image;
            private String status;
            private String file_upload;
            private String file_name;
            private int views_count;

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getAuthor_username() {
                return author_username;
            }

            public String getPublish() {
                return publish;
            }

            public String getImage() {
                return image;
            }

            public String getStatus() {
                return status;
            }

            public String getFile_upload() {
                return file_upload;
            }

            public String getFile_name() {
                return file_name;
            }

            public int getViews_count() {
                return views_count;
            }
        }
    }

    public static  class  AuthorPostsResponse{
        private boolean error;
        private String message;
        private int count;
        private List<AuthorPostsData> data;

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public int getCount() {
            return count;
        }

        public List<AuthorPostsData> getData() {
            return data;
        }

        public  static  class AuthorPostsData{
            private  String  id;
            private String title;
            private String body;
            private String post_type;
            private String course;
            private String file_upload;
            private String file_name;
            private String image;
            private String status;
            private int views_count;
            private String authors_username;
            private String publish;

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getPost_type() {
                return post_type;
            }

            public String getCourse() {
                return course;
            }

            public String getFile_upload() {
                return file_upload;
            }

            public String getFile_name() {
                return file_name;
            }

            public String getImage() {
                return image;
            }

            public String getStatus() {
                return status;
            }

            public int getViews_count() {
                return views_count;
            }

            public String getAuthors_username() {
                return authors_username;
            }

            public String getPublish() {
                return publish;
            }
        }
    }

    public static class SearchResponse {
        private  boolean error;
        private String message;
        private int count;
        private List<SearchData> data;

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public int getCount() {
            return count;
        }

        public List<SearchData> getData() {
            return data;
        }

        public static  class SearchData{
            private  String  id;
            private String title;
            private String body;
            private String post_type;
            private String course;
            private String file_upload;
            private String file_name;
            private String image;
            private String status;
            private int views_count;
            private String author_username;
            private String publish;

            private String email;
            private String staff_number;
            private String registration_number;
            private boolean is_teacher;
            private boolean is_student;
            private  List<LoginResponse.Course> courses;

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getPost_type() {
                return post_type;
            }

            public String getCourse() {
                return course;
            }

            public String getFile_upload() {
                return file_upload;
            }

            public String getFile_name() {
                return file_name;
            }

            public String getImage() {
                return image;
            }

            public String getStatus() {
                return status;
            }

            public int getViews_count() {
                return views_count;
            }

            public String getAuthor_username() {
                return author_username;
            }

            public String getPublish() {
                return publish;
            }

            public String getEmail() {
                return email;
            }

            public String getStaff_number() {
                return staff_number;
            }

            public String getRegistration_number() {
                return registration_number;
            }

            public boolean isIs_teacher() {
                return is_teacher;
            }

            public boolean isIs_student() {
                return is_student;
            }

            public List<LoginResponse.Course> getCourses() {
                return courses;
            }
        }
    }
}
