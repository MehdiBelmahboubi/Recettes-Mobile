package com.example.recettes_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_Name = "Recettes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_Name = "User";
    private static final String User_ID ="_id";
    private static final String User_NAME="user_name";
    private static final String User_Email="user_email";
    private static final String USER_PASSWD ="user_passwd";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_Name, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_Name+
                "("+User_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                User_NAME+" Text, "+
                User_Email+" Text, "+
                USER_PASSWD+" Text); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_Name);
        onCreate(db);
    }

    void AddUser(String name,String email,String passwd,String repasswd){
        if(Objects.equals(passwd, repasswd))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(User_NAME,name);
            cv.put(User_Email,email);
            cv.put(USER_PASSWD,passwd);
            long result = db.insert(TABLE_Name,null,cv);
            if(result == -1){
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Added Successfuly",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"RePassword Not Correct",Toast.LENGTH_SHORT).show();
        }
    }

    boolean ConnectUser(String email,String passwd){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {User_Email,USER_PASSWD};

        String selection = User_Email +" like ? and "+USER_PASSWD+" like ?";
        String[] selectionArgs = {email,passwd};

        Cursor cursor = db.query(
                TABLE_Name,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



        boolean isConnected = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        return isConnected;
    }
}
