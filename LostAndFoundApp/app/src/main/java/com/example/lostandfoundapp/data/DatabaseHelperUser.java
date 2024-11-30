package com.example.lostandfoundapp.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfoundapp.UserManager;
import com.example.lostandfoundapp.model.User;
import com.example.lostandfoundapp.util.UtilUser;

public class DatabaseHelperUser extends SQLiteOpenHelper {
    public DatabaseHelperUser(@Nullable Context context) {
        super(context, UtilUser.DATABASE_NAME, null, UtilUser.DATABASE_VERSION);
    }

    // create table users
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE =
                "CREATE TABLE "
                        + UtilUser.TABLE_NAME
                        + " ( "
                        + UtilUser.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + UtilUser.USERNAME + " TEXT , "
                        + UtilUser.PASSWORD + " TEXT "
                        + " ) ";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        /*
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";

        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

         */

    }

    // insert new user to the users table
    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UtilUser.USERNAME, user.getUsername());
        contentValues.put(UtilUser.PASSWORD, user.getPassword());

        long newRowID = db.insert(UtilUser.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    }

    // get users from users table and returns true if the user exists
    public boolean fetchUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UtilUser.TABLE_NAME, new String[]{UtilUser.USER_ID}, UtilUser.USERNAME + "=? and " + UtilUser.PASSWORD + "=?", new String[] {username, password}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0) {
            currentUser(username);
            return true;
        }
        else {
            return false;
        }
    }

    // set current user (username and user_is) as an instance
    @SuppressLint("Range")
    public void currentUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {UtilUser.USER_ID, UtilUser.USERNAME};
        String selection = UtilUser.USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(UtilUser.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String user_id = cursor.getString(cursor.getColumnIndex(UtilUser.USER_ID));
            String user_name = cursor.getString(cursor.getColumnIndex(UtilUser.USERNAME));
            UserManager.getInstance().setCurrentUserId(user_id);
            UserManager.getInstance().setCurrentUserName(user_name);
        }

        cursor.close();
        db.close();

    }


}
