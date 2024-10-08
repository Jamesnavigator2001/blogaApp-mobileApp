package com.example.blogapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.adapters.CommentsAdapter;
import com.example.blogapp.adapters.SharedPreferenceHelper;
import com.example.blogapp.httpRequests.ApiClient;
import com.example.blogapp.httpRequests.ApiService;
import com.example.blogapp.httpRequests.CommentsCountCallback;
import com.example.blogapp.models.CommentModel;
import com.example.blogapp.utils.AnimationUtils;
import com.example.blogapp.utils.AttachmentUtils;
import com.example.blogapp.utils.Comment;
import com.example.blogapp.utils.KeyboardUtils;
import com.example.blogapp.utils.StatusNavigationBackground;
import com.example.blogapp.validators.CommentsValidator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<CommentModel> commentModelList = new ArrayList<>();
    private  CommentsAdapter commentsAdapter;
    private  String postId;
    private String authorName;
    private String pdfUrl;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ApiService apiService;
    ConstraintLayout postLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusNavigationBackground.setBackground(this, R.color.deepFadingSkyBlue, R.color.deepFadingSkyBlue);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        Button backBtn = findViewById(R.id.backBtn);
        TextView numOfComments = findViewById(R.id.numOfComments);
        TextView authorUserName = findViewById(R.id.authorName);
        TextView title = findViewById(R.id.postTitle);
        TextView date = findViewById(R.id.date);
        TextView pdfAttachmentView = findViewById(R.id.pdfAttachmentText);
        TextView bodyContent = findViewById(R.id.cardContent);
        ImageView cardImage = findViewById(R.id.cardImageLayout);
        ImageView pdfDownloadBtn = findViewById(R.id.pdfDownloadBtn);
        postLayout = findViewById(R.id.postLayout);

        Intent intent = getIntent();
        if (intent != null) {
            postId = intent.getStringExtra("postId");
            String postTitle = intent.getStringExtra("postTitle");
            String postContent = intent.getStringExtra("postContent");
            String createdAt = intent.getStringExtra("createdAt");
            authorName = intent.getStringExtra("authorName");
            String postImage = intent.getStringExtra("postImage");
            pdfUrl = intent.getStringExtra("pdfUrl");
            String fileName = intent.getStringExtra("fileName");

            // Fetch comments count and set up UI
            Comment comment = new Comment();
            comment.fetchNumOfComments(postId, new CommentsCountCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCommentsCountFetched(int count) {
                    if (count > 1) {
                        numOfComments.setText(count + " Comments");
                    } else {
                        numOfComments.setText(count + " Comment");
                    }
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onError(Throwable t) {
                    numOfComments.setText("Failed to fetch comments");
                    Log.e("CommentsCount", "Error fetching comments", t);
                }
            });

            authorUserName.setText(authorName);
            title.setText(postTitle);
            bodyContent.setText(postContent);
            date.setText(createdAt);
            pdfAttachmentView.setText(fileName);

            if (postImage != null) {
                Picasso.get().load(postImage).into(cardImage);
            } else {
                cardImage.setImageResource(R.drawable.college);
            }
        }
        authorUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AuthorsPostsActivity.class);
                intent.putExtra("authorName", authorName);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
            }
        });

        numOfComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationUtils.animateCardContainer(postLayout, new Runnable() {
                    @Override
                    public void run() {
                        Log.d("AnimationUtils", "Animation has ended");
                    }
                });
                showCommentsBottomSheet();
            }
        });

        pdfDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttachmentUtils.downloadAndOpenDocument(MainActivity.this, pdfUrl);
            }
        });
    }


    private void showCommentsBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_comments, null);

        TextView editTextComment = bottomSheetView.findViewById(R.id.editTextComment);
        Button sendBtn = bottomSheetView.findViewById(R.id.btnSendComment);

        RecyclerView commentRecycleView = bottomSheetView.findViewById(R.id.commentsRecyclerView);
        commentRecycleView.setLayoutManager(new LinearLayoutManager(this));

        commentsAdapter = new CommentsAdapter(commentModelList);
        commentRecycleView.setAdapter(commentsAdapter);
        bottomSheetDialog.setContentView(bottomSheetView);

        FrameLayout bottomSheetInternal = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        LinearLayout commentInputContainer = bottomSheetView.findViewById(R.id.commentInputContainer); // Container for EditText and Button

        limitBottomSheetDialogHeight(bottomSheetDialog);

        // Adjust comment input container above keyboard
        if (bottomSheetInternal != null) {
            KeyboardUtils.adjustForKeyboard(this, bottomSheetInternal, commentInputContainer);
        }


        bottomSheetDialog.setContentView(bottomSheetView);

        limitBottomSheetDialogHeight(bottomSheetDialog);

        // Adjust the container above the keyboard
        if (bottomSheetInternal != null) {
            KeyboardUtils.adjustForKeyboard(this, bottomSheetInternal, commentInputContainer);
        }


        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                AnimationUtils.resetCardContainer(postLayout);
                AnimationUtils.isShrunk = false;
            }

        });

        // Apply the height limit before the dialog is actually shown
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                limitBottomSheetDialogHeight((BottomSheetDialog) dialogInterface);
            }
        });

        bottomSheetDialog.show();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentBody = editTextComment.getText().toString().trim();
                CommentsValidator validator = new CommentsValidator();

                if (!validator.isCommentNotEmpty(commentBody)) {
                    Toast.makeText(bottomSheetView.getContext(), "Please type in a valid comment", Toast.LENGTH_SHORT).show();
                    return;
                }

                String token = "Bearer " + sharedPreferenceHelper.getAccessToken();
                String registrationNumber = sharedPreferenceHelper.getUserRegistrationNumber();

                if (!validator.validateComment(commentBody, registrationNumber, token)) {
                    Log.e("Comment", "Validation failed: Invalid registration number or token");
                    return;
                }

                Comment comment = new Comment(MainActivity.this, apiService, token, registrationNumber, postId, commentsAdapter);
                comment.postComment(commentBody);
                editTextComment.setText("");
                Toast.makeText(bottomSheetView.getContext(), "Comment posted", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comment.fetchComments(postId);
                    }
                }, 500);
            }
        });

        String token = "Bearer " + sharedPreferenceHelper.getAccessToken();
        String registrationNumber = sharedPreferenceHelper.getUserRegistrationNumber();
        Comment comment = new Comment(MainActivity.this, apiService, token, registrationNumber, postId, commentsAdapter);
        comment.fetchComments(postId);

    }



    private void limitBottomSheetDialogHeight(BottomSheetDialog dialog) {
        FrameLayout bottomSheetInternal = (FrameLayout) dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheetInternal != null) {
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheetInternal);

            // Set initial height before the dialog is shown to prevent the full-height blink
            ViewGroup.LayoutParams layoutParams = bottomSheetInternal.getLayoutParams();
            int maxHeight = 1500;
            if (layoutParams != null) {
                layoutParams.height = maxHeight;  // Set height before layout is drawn
                bottomSheetInternal.setLayoutParams(layoutParams);
            }

            // Optional: Still add the global layout listener in case further adjustments are needed
            bottomSheetInternal.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    bottomSheetInternal.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }



}