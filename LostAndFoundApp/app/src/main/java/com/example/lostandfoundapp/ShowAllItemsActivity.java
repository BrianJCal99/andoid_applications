package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lostandfoundapp.data.DatabaseHelperPost;
import com.example.lostandfoundapp.data.DatabaseHelperUser;
import com.example.lostandfoundapp.model.Post;

import java.util.List;

public class ShowAllItemsActivity extends AppCompatActivity {

    DatabaseHelperPost db_posts;
    DatabaseHelperUser db_users;

    RecyclerView postListRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    PostListAdapter postListAdapter;

    TextView emptyTextView;
    private List<Post> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_items);

        db_posts = new DatabaseHelperPost(this);
        db_users = new DatabaseHelperUser(this);

        itemList = db_posts.fetchPosts();

        postListRecyclerView = findViewById(R.id.recyclerView);

        postListAdapter = new PostListAdapter(this, itemList);

        layoutManager = new LinearLayoutManager(this);

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);

        // show empty if theres no posts to display
        if (itemList.isEmpty()) {
            postListRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }

        postListRecyclerView.setAdapter(postListAdapter);
        postListRecyclerView.setLayoutManager(layoutManager);

        // when user clicks post
        postListAdapter.setOnItemClickListener(new PostListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int post_id) {
                // Launch post activity with data from the clicked item (post_id)
                Intent intent = new Intent(ShowAllItemsActivity.this, PostActivity.class);
                intent.putExtra("item_data", post_id);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Reload the data from the database and update the adapter
        itemList.clear();
        itemList.addAll(db_posts.fetchPosts());
        postListAdapter.notifyDataSetChanged();

        if (itemList.isEmpty()) {
            postListRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }
}