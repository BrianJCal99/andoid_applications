package com.example.lostandfoundapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfoundapp.model.Post;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{

    Context context;
    List<Post> list;

    private OnItemClickListener listener;

    // adapter for post list
    public PostListAdapter(Context context, List<Post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostListAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_recyclerview_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.PostViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // get the post description by the position of the item and set it as the title
        holder.textView.setText(list.get(position).getDescription());

        // returns post id according to the clicking items position
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(list.get(position).getPost_id());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView textView;




        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleTextView);
        }



    }

    public interface OnItemClickListener {
        void onItemClick(int post_id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
