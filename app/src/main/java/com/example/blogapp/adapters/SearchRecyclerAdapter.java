package com.example.blogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.MainActivity;
import com.example.blogapp.R;
import com.example.blogapp.models.PostsListModal;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private final List<PostsListModal> posts;
    private final Context context;

    public SearchRecyclerAdapter(Context context, List<PostsListModal> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_results_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostsListModal post = posts.get(position);
        holder.postTitle.setText(post.getTitle());
        holder.authorName.setText(post.getAuthorUsername());
        holder.postDate.setText(post.getDate2());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("postId", post.getId());
            intent.putExtra("postTitle", post.getTitle());
            intent.putExtra("postContent", post.getBody());
//            intent.putExtra("createdAt", post.getDate().toString());
            intent.putExtra("authorName",post.getAuthorUsername());
            intent.putExtra("postImage", post.getImage());
            intent.putExtra("pdfUrl" , post.getFileUploadUrl());
            intent.putExtra("fileName" , post.getFileName());
            context.startActivity(intent);

            Log.d("PostListAdapter", "Pdf Url: " + post.getFileUploadUrl());
            Log.d("PostListAdapter", "Post ID: " + post.getId());
            Log.d("PostListAdapter", "Post Title: " + post.getTitle());
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle, authorName, postDate;

        public ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            authorName = itemView.findViewById(R.id.authorUsername);
            postDate = itemView.findViewById(R.id.postedAt);
        }
    }
}
