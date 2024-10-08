package com.example.blogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.MainActivity;
import com.example.blogapp.R;
import com.example.blogapp.models.PostsListModal;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private final ArrayList<PostsListModal> postsListModal;
    private LayoutInflater inflater;
    private Context context;

    public PostListAdapter(Context context, ArrayList<PostsListModal> postsListModal) {
        this.postsListModal = postsListModal;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout here
        View view = inflater.inflate(R.layout.post_layout_inflater, parent, false);  // No more NullPointerException
        return new PostListAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.ViewHolder holder, int position) {

        holder.itemView.clearAnimation();
        holder.itemView.setAlpha(0f);
        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);

        holder.itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        holder.authorsUserName.setText(postsListModal.get(position).getAuthorUsername());
        holder.createdAt.setText(postsListModal.get(position).getDate().toString());
        holder.bodyContent.setText(postsListModal.get(position).getBody());
        holder.postTitle.setText(postsListModal.get(position).getTitle());
        holder.postImage.setImageResource(0);
        holder.pdfAttachment.setText(postsListModal.get(position).getFileName());


        Picasso.get()
                .load(postsListModal.get(position).getImage())
                .placeholder(R.drawable.college)
                .error(R.drawable.bottom_nav_background)
                .into(holder.postImage);


//        if (pdfUrl != null && !pdfUrl.isEmpty()){
//            holder.pdfAttachment.setVisibility(View.GONE);
//        } else {
//            holder.pdfAttachment.setVisibility(View.GONE);
//        }
//
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("postId", postsListModal.get(position).getId());
            intent.putExtra("postTitle", postsListModal.get(position).getTitle());
            intent.putExtra("postContent", postsListModal.get(position).getBody());
            intent.putExtra("createdAt", postsListModal.get(position).getDate().toString());
            intent.putExtra("authorName",postsListModal.get(position).getAuthorUsername());
            intent.putExtra("postImage", postsListModal.get(position).getImage());
            intent.putExtra("pdfUrl" , postsListModal.get(position).getFileUploadUrl());
            intent.putExtra("fileName" , postsListModal.get(position).getFileName());
            context.startActivity(intent);

            Log.d("PostListAdapter", "Pdf Url: " + postsListModal.get(position).getFileUploadUrl());
            Log.d("PostListAdapter", "Post ID: " + postsListModal.get(position).getId());
            Log.d("PostListAdapter", "Post Title: " + postsListModal.get(position).getTitle());
        });
    }


    @Override
    public int getItemCount() {
        return postsListModal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button pdfAttachment;
        public TextView postTitle;
        public TextView bodyContent;
        public TextView createdAt;
        public TextView authorsUserName;
        ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfAttachment = itemView.findViewById(R.id.pdfAttachment);
            postTitle = itemView.findViewById(R.id.postTitle);
            bodyContent = itemView.findViewById(R.id.cardContent);
            createdAt = itemView.findViewById(R.id.date);
            authorsUserName = itemView.findViewById(R.id.authorName);
            postImage = itemView.findViewById(R.id.cardImageLayout);
        }
    }
}
