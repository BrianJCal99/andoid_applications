package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelperPost;
import com.example.lostandfoundapp.model.Post;

public class PostActivity extends AppCompatActivity {

    DatabaseHelperPost db_posts;

    PostListAdapter postListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewType = findViewById(R.id.textViewType);

        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewDate = findViewById(R.id.textViewDate);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewPhone = findViewById(R.id.textViewPhone);

        Button buttonRemovePost = findViewById(R.id.buttonRemovePost);

        // get the post_id passed from the previous activity with the intent
        int post_id  = getIntent().getIntExtra("item_data", 0);
        // get current user from user manager
        String currentUser = UserManager.getInstance().getCurrentUserId();

        db_posts = new DatabaseHelperPost(this);

        // get the post content by it's post_id
        Post post = db_posts.getPostByPostID(String.valueOf(post_id));

        textViewName.setText("Name: " + post.getName() + currentUser + post.getUser_id());
        textViewPhone.setText("Phone: " + post.getPhone());
        textViewDescription.setText("Description: " + post.getDescription());
        textViewType.setText("Item status: " + post.getPost_type());
        textViewLocation.setText("Location: " + post.getLocation());
        textViewDate.setText("Date: " + post.getDate() + "/" + post.getMonth() + "/" + post.getYear());

        // show delete button if the post was posted by the current active user
        if (currentUser.equals(post.getUser_id())){
            buttonRemovePost.setVisibility(View.VISIBLE);
        }

        // delete post on click
        buttonRemovePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = db_posts.deletePost(post_id);
                if (success) {
                    Toast.makeText(PostActivity.this, "Post deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, "Post deletion failed", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}