package com.example.blogapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.blogapp.R;
import com.example.blogapp.models.CommentModel;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private List<CommentModel> commentModel;

    public CommentsAdapter(List<CommentModel> commentsList) {
        this.commentModel = commentsList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateComments(List<CommentModel> newComments) {
        this.commentModel.clear();
        this.commentModel.addAll(newComments);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentsAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CommentViewHolder holder, int position) {
        CommentModel commentModel1 = commentModel.get(position);
        holder.commentBody.setText(commentModel1.getBody());
        holder.commentUser.setText(commentModel1.getEmail());
        holder.time.setText(commentModel1.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return commentModel.size();
    }

    public  static  class CommentViewHolder extends  RecyclerView.ViewHolder{
        TextView commentBody ,commentUser , time;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentBody = itemView.findViewById(R.id.commentBody);
            commentUser = itemView.findViewById(R.id.userName);
            time = itemView.findViewById(R.id.time);
        }
    }
}
