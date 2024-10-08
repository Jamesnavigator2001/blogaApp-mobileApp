package com.example.blogapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

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

import com.example.blogapp.R;
import com.example.blogapp.adapters.CourseSpecificAdapter;
import com.example.blogapp.adapters.SharedPreferenceHelper;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.models.CourseSpecificModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForYouFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForYouFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ApiService apiService;
    private ArrayList<CourseSpecificModel> courseSpecificModel;
    private CourseSpecificAdapter courseSpecificAdapter;

    public ForYouFragment() {
        // Required empty public constructor
    }

    public static ForYouFragment newInstance(String param1, String param2) {
        ForYouFragment fragment = new ForYouFragment();
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
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_for_you,container,false);
        TextView generalPage = view.findViewById(R.id.generalPage);

        RecyclerView recyclerView = view.findViewById(R.id.postRecyclerForYou);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        courseSpecificModel = new ArrayList<>();
        courseSpecificAdapter = new CourseSpecificAdapter(requireContext() , courseSpecificModel);
        recyclerView.setAdapter(courseSpecificAdapter);
        generalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(this::fetchCoursePosts, 1000);

        return  view;
    }

    private void fetchCoursePosts() {
        fetchPostsFromServer();
    }

    private void fetchPostsFromServer() {
        String token = "Bearer" + sharedPreferenceHelper.getAccessToken();
        List<ResponseHandler.LoginResponse.Course> courses = sharedPreferenceHelper.getCourses();

        if(courses != null){
            for(ResponseHandler.LoginResponse.Course course : courses){
                String courseId = course.getId();
                Call<ResponseHandler.CourseSpecificPosts> call = apiService.courseSpecificPosts(token, courseId);
                call.enqueue(new Callback<ResponseHandler.CourseSpecificPosts>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseHandler.CourseSpecificPosts> call, Response<ResponseHandler.CourseSpecificPosts> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            ResponseHandler.CourseSpecificPosts postsData = response.body();
                            courseSpecificModel.clear();

                            for(ResponseHandler.CourseSpecificPosts.CourseSpecificData courseData : postsData.getData()){
                                CourseSpecificModel post = new CourseSpecificModel();
                                post.setId(courseData.getId());
                                post.setTitle(courseData.getTitle());
                                post.setBody(courseData.getBody());
                                post.setAuthorUsername(courseData.getAuthor_username());
                                post.setImage(courseData.getImage());
                                post.setDate(courseData.getPublish());
                                post.setFileUpload(courseData.getFile_upload());
                                post.setFileName(courseData.getFile_name());

                                courseSpecificModel.add(post);
                            }
                            courseSpecificAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseHandler.CourseSpecificPosts> call, Throwable t) {
                        Log.e("API Error" , "Error Fetching course posts");
                    }
                });
            }
        }
    }
}