package com.example.blogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.MainActivity;
import com.example.blogapp.R;
import com.example.blogapp.models.AuthorsPostsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AuthorPostsAdapter extends RecyclerView.Adapter<AuthorPostsAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private  final ArrayList<AuthorsPostsModel> authorsPostsModel;

    public AuthorPostsAdapter(Context context,ArrayList<AuthorsPostsModel> authorsPostsModel) {
        this.authorsPostsModel = authorsPostsModel;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.authors_all_posts_card_item,parent , false);
        return  new AuthorPostsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.postTitle.setText(authorsPostsModel.get(position).getTitle());
        Picasso.get()
                .load(authorsPostsModel.get(position).getImageUrl())
                .placeholder(R.drawable.college)
                .error(R.drawable.bottom_nav_background)
                .into(holder.postImage);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("postId", authorsPostsModel.get(position).getId());
            intent.putExtra("postTitle", authorsPostsModel.get(position).getTitle());
            intent.putExtra("postContent", authorsPostsModel.get(position).getBody());
            intent.putExtra("createdAt", authorsPostsModel.get(position).getPublish());
            intent.putExtra("authorName",authorsPostsModel.get(position).getAuthorsUsername());
            intent.putExtra("postImage", authorsPostsModel.get(position).getImageUrl());
            intent.putExtra("pdfUrl" , authorsPostsModel.get(position).getFileUpload());
            intent.putExtra("fileName" , authorsPostsModel.get(position).getFileName());
            context.startActivity(intent);

            Log.d("PostListAdapter", "Pdf Url: " +authorsPostsModel.get(position).getFileUpload());
            Log.d("PostListAdapter", "Post ID: " + authorsPostsModel.get(position).getId());
            Log.d("PostListAdapter", "Post Title: " + authorsPostsModel.get(position).getTitle());
        });
    }

    @Override
    public int getItemCount() {
        return authorsPostsModel.size();
    }

    public  static  class  ViewHolder extends RecyclerView.ViewHolder{

        public ImageView postImage;
        public TextView postTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
            postTitle = itemView.findViewById(R.id.post_title);
        }
    }

}
