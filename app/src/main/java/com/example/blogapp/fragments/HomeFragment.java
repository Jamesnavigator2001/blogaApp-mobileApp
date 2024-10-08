package com.example.blogapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blogapp.LandingActivity;
import com.example.blogapp.LoginActivity;
import com.example.blogapp.R;
import com.example.blogapp.adapters.PostListAdapter;
import com.example.blogapp.adapters.SharedPreferenceHelper;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.models.PostsListModal;
import com.example.blogapp.utils.PostView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<PostsListModal> postsListModal;
    private PostListAdapter postsListAdapter;
    private ApiService apiService;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private final HashSet<Integer> viewedPosts = new HashSet<>();
    private String registrationNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);  // <-- This line is important!
        Log.d("TAG", "In Home Fragment");

        registrationNumber = sharedPreferenceHelper.getUserRegistrationNumber();// Get registration number
        loadViewedPosts();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext());
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.postRecycler);
        TextView forYouPage = view.findViewById(R.id.forYouPage);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postsListModal = new ArrayList<>();
        postsListAdapter = new PostListAdapter(requireContext(), postsListModal);

        recyclerView.setAdapter(postsListAdapter);
        // Fetch posts after delay
        new Handler(Looper.getMainLooper()).postDelayed(this::fetchPostListData, 1000);

        forYouPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForYouFragment forYouFragment = new ForYouFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, forYouFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int position = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));

//                if (position != RecyclerView.NO_POSITION) {
//                    PostsListModal post = postsListModal.get(position);
//
//                    String postId = post.getId();
//                    if (!viewedPosts.contains(postId)) {
//                        Log.d("Message" , "Post" + post.getId());
//                        PostView postView = new PostView();
//                        postView.recordPostView(postId, sharedPreferenceHelper.getUserRegistrationNumber());
//                        viewedPosts.add(postId);
//                    }
//                }
            }
        });

        return view;
    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        saveViewedPosts();
    }

    private  void loadViewedPosts(){
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String viewedPostsString = prefs.getString("viewed_posts_" + registrationNumber, "");

        if (!viewedPostsString.isEmpty()) {
            String[] ids = viewedPostsString.split(",");
            for (String id : ids) {
                viewedPosts.add(Integer.parseInt(id));
            }
        }
    }

    private void saveViewedPosts() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        StringBuilder sb = new StringBuilder();
        for (Integer id : viewedPosts) {
            sb.append(id).append(",");
        }
        editor.putString("viewed_posts_" + registrationNumber, sb.toString());
        editor.apply();
    }


    private void fetchPostListData() {
        fetchPostsFromServer();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchPostsFromServer() {

//        courses name and id's logger
        List<ResponseHandler.LoginResponse.Course> courses = sharedPreferenceHelper.getCourses();
        if (courses != null) {
            Log.d("Courses", "Courses:");
            for (ResponseHandler.LoginResponse.Course course : courses) {
                Log.d("Courses", "ID: " + course.getId() + ", Name: " + course.getName() + ", Code: " + course.getCode());
            }
        } else {
            Log.d("Courses", "No courses found");
        }


        String token ="Bearer " + sharedPreferenceHelper.getAccessToken();
        Call<ResponseHandler.PostsListResponse> call = apiService.getAllPosts(token);
        call.enqueue(new Callback<ResponseHandler.PostsListResponse>() {
            @Override
            public void onResponse(Call<ResponseHandler.PostsListResponse> call, Response<ResponseHandler.PostsListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseHandler.PostsListResponse postListData = response.body();

                    // Clear the existing list to avoid duplicates
                    postsListModal.clear();

                    // Loop through the list of PostData
                    for (ResponseHandler.PostsListResponse.PostData postData : postListData.getData()) {
                        PostsListModal post = new PostsListModal();
                        post.setId(postData.getPostId());
                        post.setTitle(postData.getTitle());
                        post.setBody(postData.getBody());
                        post.setAuthorUsername(postData.getAuthorUsername());
                        post.setImage(postData.getImage());
                        post.setDate(postData.getPublish());
                        post.setFileUploadUrl(postData.getFile_upload());
                        post.setFileName(postData.getFile_name());

                        // Add the post to the list
                        postsListModal.add(post);
                    }
                    // Notify the adapter of the data change
                    postsListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseHandler.PostsListResponse> call, Throwable t) {
                Log.e("API Error", "Error fetching posts", t);
            }
        });
    }

}