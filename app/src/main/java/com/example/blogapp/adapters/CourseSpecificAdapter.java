package com.example.blogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.MainActivity;
import com.example.blogapp.R;
import com.example.blogapp.models.CourseSpecificModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseSpecificAdapter extends RecyclerView.Adapter<CourseSpecificAdapter.ViewHolder> {
    private  final ArrayList<CourseSpecificModel> courseSpecificModel;
    private LayoutInflater inflater;
    private  Context context;

    public CourseSpecificAdapter(Context context, ArrayList<CourseSpecificModel> courseSpecificModel) {
        this.courseSpecificModel = courseSpecificModel;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CourseSpecificAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_specific_layout_inflater, parent , false);
        return new CourseSpecificAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseSpecificAdapter.ViewHolder holder, int position) {
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
        holder.authorsUserName.setText(courseSpecificModel.get(position).getAuthorUsername());
        holder.createdAt.setText(courseSpecificModel.get(position).getDate());
        holder.bodyContent.setText(courseSpecificModel.get(position).getBody());
        holder.postTitle.setText(courseSpecificModel.get(position).getTitle());
        holder.postImage.setImageResource(0);
        holder.pdfAttachment.setText(courseSpecificModel.get(position).getFileName());
        Picasso.get()
                .load(courseSpecificModel.get(position).getImage())
                .placeholder(R.drawable.college)
                .error(R.drawable.bottom_nav_background)
                .into(holder.postImage);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("postId", courseSpecificModel.get(position).getId());
            intent.putExtra("postTitle", courseSpecificModel.get(position).getTitle());
            intent.putExtra("postContent", courseSpecificModel.get(position).getBody());
            intent.putExtra("createdAt", courseSpecificModel.get(position).getDate());
            intent.putExtra("authorName",courseSpecificModel.get(position).getAuthorUsername());
            intent.putExtra("postImage", courseSpecificModel.get(position).getImage());
            intent.putExtra("pdfUrl" , courseSpecificModel.get(position).getFileUpload());
            intent.putExtra("fileName" , courseSpecificModel.get(position).getFileName());
            context.startActivity(intent);

            Log.d("PostListAdapter", "Pdf Url: " +courseSpecificModel.get(position).getFileUpload());
            Log.d("PostListAdapter", "Post ID: " + courseSpecificModel.get(position).getId());
            Log.d("PostListAdapter", "Post Title: " + courseSpecificModel.get(position).getTitle());
        });

    }

    @Override
    public int getItemCount() {
        return courseSpecificModel.size();
    }


    public static  class ViewHolder extends RecyclerView.ViewHolder {
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
