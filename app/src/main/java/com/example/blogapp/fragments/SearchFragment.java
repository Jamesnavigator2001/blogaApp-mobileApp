package com.example.blogapp.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.blogapp.R;
import com.example.blogapp.adapters.SearchRecyclerAdapter;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.ResponseHandler;
import com.example.blogapp.models.PostsListModal;
import com.example.blogapp.utils.KeyboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final int LOADER_TIME = 2000;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ApiService apiService;
    private SearchView searchEditText;
    private ArrayList<PostsListModal> postsListModal;
    private String searchType;
    private SearchRecyclerAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        postsListModal = new ArrayList<>();
        searchAdapter = new SearchRecyclerAdapter(requireContext(), postsListModal);

        searchEditText = view.findViewById(R.id.searchTxt);
        Button sendBtn = view.findViewById(R.id.searchBtn);
        RecyclerView searchResultsRecyclerView = view.findViewById(R.id.searchItemsRecycler);

        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchResultsRecyclerView.setAdapter(searchAdapter);

        int searchPlateId = searchEditText.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchEditText.findViewById(searchPlateId);
        searchPlate.setBackground(null);
        int searchTextViewId = searchEditText.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchTextView = searchEditText.findViewById(searchTextViewId);
        searchTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        searchTextView.setHintTextColor(Color.parseColor("#80FFFFFF"));

        TextView postQuery = view.findViewById(R.id.postQuery);
        TextView authorQuery = view.findViewById(R.id.authorQuery);
        searchType = "post";

        postQuery.setOnClickListener(view1 -> {
            searchType = "post";
            String query = searchEditText.getQuery().toString();
            new Handler().postDelayed(() -> searchTeachersOrPosts(query), LOADER_TIME);
        });

        authorQuery.setOnClickListener(view12 -> {
            searchType = "teacher";
            String query = searchEditText.getQuery().toString();
            new Handler().postDelayed(() -> searchTeachersOrPosts(query), LOADER_TIME);
        });

        sendBtn.setOnClickListener(view13 -> {
            String query = searchEditText.getQuery().toString();
            new Handler().postDelayed(() -> searchTeachersOrPosts(query), LOADER_TIME);
        });

        return view;
    }

    private void searchTeachersOrPosts(String query) {
        Call<ResponseHandler.SearchResponse> call = apiService.searchTeachersAndPosts(query, searchType);
        call.enqueue(new Callback<ResponseHandler.SearchResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ResponseHandler.SearchResponse> call, @NonNull Response<ResponseHandler.SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseHandler.SearchResponse posts = response.body();
                    postsListModal.clear();
                    for (ResponseHandler.SearchResponse.SearchData data : posts.getData()) {
                        PostsListModal post = new PostsListModal();
                        post.setId(data.getId());
                        post.setTitle(data.getTitle());
                        post.setBody(data.getBody());
                        post.setAuthorUsername(data.getAuthor_username());
                        post.setImage(data.getImage());
                        post.setDate2(data.getPublish());
                        post.setFileUploadUrl(data.getFile_upload());
                        post.setFileName(data.getFile_name());
                        postsListModal.add(post);
                        Log.d("SearchFragment", "Post added: " + data.getAuthor_username());
                    }
                    searchAdapter.notifyDataSetChanged();
                    Log.d("SearchFragment", "Data fetched: " + response.body());
                } else {
                    Log.e("SearchFragment", "Error searching: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHandler.SearchResponse> call, @NonNull Throwable t) {
                Log.e("SearchFragment", "Error searching: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation);
        KeyboardUtils.adjustBottomNavigation(requireActivity(), bottomNavigationView);
    }

}
