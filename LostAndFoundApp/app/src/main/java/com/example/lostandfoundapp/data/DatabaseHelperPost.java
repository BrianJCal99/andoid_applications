package com.example.lostandfoundapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfoundapp.model.Post;
import com.example.lostandfoundapp.util.UtilPost;
import com.example.lostandfoundapp.model.User;
import com.example.lostandfoundapp.util.UtilUser;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperPost extends SQLiteOpenHelper {

    public DatabaseHelperPost(@Nullable Context context) {
        super(context, UtilPost.DATABASE_NAME, null, UtilPost.DATABASE_VERSION);
    }

    // create table posts
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_POST_TABLE =
                "CREATE TABLE "
                        + UtilPost.TABLE_NAME
                        + " ( "
                        + UtilPost.POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + UtilPost.POST_TYPE + " TEXT, "
                        + UtilUser.USER_ID + " TEXT, "
                        + UtilPost.NAME + " TEXT, "
                        + UtilPost.PHONE + " TEXT, "
                        + UtilPost.DESCRIPTION + " TEXT, "
                        + UtilPost.DATE + " TEXT, "
                        + UtilPost.MONTH + " TEXT, "
                        + UtilPost.YEAR + " TEXT, "
                        + UtilPost.LOCATION + " TEXT "
                        + " ) ";

        sqLiteDatabase.execSQL(CREATE_POST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        String DROP_POST_TABLE = "DROP TABLE IF EXISTS";

        sqLiteDatabase.execSQL(DROP_POST_TABLE, new String[]{UtilPost.TABLE_NAME});

        onCreate(sqLiteDatabase);

         */
    }

    // insert new post
    public long insertPost(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UtilPost.POST_TYPE, post.getPost_type());
        contentValues.put(UtilPost.USER_ID, post.getUser_id());
        contentValues.put(UtilPost.NAME, post.getName());
        contentValues.put(UtilPost.PHONE, post.getPhone());
        contentValues.put(UtilPost.DESCRIPTION, post.getDescription());
        contentValues.put(UtilPost.DATE, post.getDate().toString());
        contentValues.put(UtilPost.MONTH, post.getMonth().toString());
        contentValues.put(UtilPost.YEAR, post.getYear().toString());
        contentValues.put(UtilPost.LOCATION, post.getLocation());

        long newRowID = db.insert(UtilPost.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    }

    // get all posts from posts table
    public List<Post> fetchPosts(){
        List<Post> postList = new ArrayList<>();

        String query = "SELECT * FROM posts";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // loop through all the rows and create Post objects
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setPost_id(cursor.getInt(0));
                post.setPost_type(cursor.getString(1));
                post.setUser_id(cursor.getString(2));
                post.setName(cursor.getString(3));
                post.setPhone(cursor.getString(4));
                post.setDescription(cursor.getString(5));
                post.setDate(cursor.getString(6));
                post.setMonth(cursor.getString(7));
                post.setYear(cursor.getString(8));
                post.setLocation(cursor.getString(9));

                postList.add(post);
            } while (cursor.moveToNext());
        }

        // close the cursor and database
        cursor.close();
        db.close();

        // return the list of posts
        return postList;
    }

    // delete post by post_id
    public boolean deletePost(int post_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(post_id)};
        int rowsAffected = db.delete(UtilPost.TABLE_NAME, UtilPost.POST_ID + "=?", args);
        db.close();
        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    // return post by post_id
    public Post getPostByPostID(String postID){
        Post post = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UtilPost.TABLE_NAME, new String[]{UtilPost.POST_ID, UtilPost.POST_TYPE, UtilPost.USER_ID, UtilPost.NAME, UtilPost.PHONE, UtilPost.DESCRIPTION, UtilPost.DATE, UtilPost.MONTH, UtilPost.YEAR, UtilPost.LOCATION},
                UtilPost.POST_ID + "=?", new String[]{postID}, null, null, null, null);
        if (cursor.moveToFirst()) {
            String post_type = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.POST_TYPE));
            String user_id = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.USER_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.PHONE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.DATE));
            String month = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.MONTH));
            String year = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.YEAR));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(UtilPost.LOCATION));

            post = new Post(post_type, user_id, name, phone, description, date, month, year, location);
        }

        cursor.close();
        db.close();

        return post;
    }

    // return readable database
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
}
