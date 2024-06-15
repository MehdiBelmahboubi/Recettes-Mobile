package com.example.recettes_mobile;

import android.annotation.SuppressLint;
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
    private static final String DATABASE_NAME = "Recettes.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_User = "User";
    public static final String User_ID = "_id";
    public static final String User_NAME = "user_name";
    public static final String User_Email = "user_email";
    public static final String USER_PASSWD = "user_passwd";

    private static final String TABLE_Recettes = "Recettes";
    public static final String Recette_ID = "_recette_id";
    public static final String Recette_Title = "recette_title";
    public static final String Recette_Description = "recette_description";
    public static final String Recette_PERSONNES = "recette_personnes";
    public static final String Recette_TIMES = "recette_times";
    public static final String Recette_Image = "recette_image";
    public static final String Recette_Ingrediants = "recette_ingrediants";
    public static final String Recette_Etape = "recette_etape";
    public static final String Recette_User_ID = "user_id";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_User +
                " (" + User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                User_NAME + " TEXT, " +
                User_Email + " TEXT, " +
                USER_PASSWD + " TEXT);";

        String createRecettesTable = "CREATE TABLE " + TABLE_Recettes +
                " (" + Recette_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Recette_Title + " TEXT, " +
                Recette_Description + " TEXT, " +
                Recette_PERSONNES + " INTEGER, " +
                Recette_TIMES + " TEXT, " +
                Recette_Image + " BLOB, " +
                Recette_Ingrediants + " TEXT, " +
                Recette_Etape + " TEXT, " +
                Recette_User_ID + " INTEGER, " +
                "FOREIGN KEY(" + Recette_User_ID + ") REFERENCES " + TABLE_User + "(" + User_ID + "));";

        db.execSQL(createUserTable);
        db.execSQL(createRecettesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_User + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Recettes + ";");
        onCreate(db);
    }

    void AddUser(String name, String email, String passwd, String repasswd) {
        if (Objects.equals(passwd, repasswd)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(User_NAME, name);
            cv.put(User_Email, email);
            cv.put(USER_PASSWD, passwd);
            long result = db.insert(TABLE_User, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "RePassword Not Correct", Toast.LENGTH_SHORT).show();
        }
    }

    void UpdateUser(int userId, String name, String email, String passwd, String repasswd) {
        if (Objects.equals(passwd, repasswd)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(User_NAME, name);
            values.put(User_Email, email);
            values.put(USER_PASSWD, passwd);
            int result = db.update(TABLE_User, values, User_ID + " = ?", new String[]{String.valueOf(userId)});
            db.close();
            if (result > 0) {
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "RePassword Not Correct", Toast.LENGTH_SHORT).show();
        }
    }

    public int ConnectUser(String email, String passwd) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {User_ID, User_Email, USER_PASSWD};

        String selection = User_Email + " = ? AND " + USER_PASSWD + " = ?";
        String[] selectionArgs = {email, passwd};

        Cursor cursor = db.query(
                TABLE_User,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int userId = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int user_id = cursor.getColumnIndex(User_ID);
                if (user_id != -1) {
                    userId = cursor.getInt(user_id);
                }
            }
            cursor.close();
        }

        return userId;
    }

    public Cursor getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {User_ID, User_NAME, User_Email};

        String selection = User_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        return db.query(
                TABLE_User,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    void AddRecette(String title, String description, int personnes, String times,String ingrediants,String etape, int userId, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Recette_Title, title);
        cv.put(Recette_Description, description);
        cv.put(Recette_PERSONNES, personnes);
        cv.put(Recette_TIMES, times);
        cv.put(Recette_Image, image);
        cv.put(Recette_Ingrediants, ingrediants);
        cv.put(Recette_Etape, etape);
        cv.put(Recette_User_ID, userId);
        long result = db.insert(TABLE_Recettes, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to Add Recette", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Recette Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void UpdateRecette(int recetteId, String title, String description, int personnes,String ingrediants,String etape, String times, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Recette_Title, title);
        values.put(Recette_Description, description);
        values.put(Recette_PERSONNES, personnes);
        values.put(Recette_TIMES, times);
        values.put(Recette_Image, image);
        values.put(Recette_Ingrediants, ingrediants);
        values.put(Recette_Etape, etape);
        int result = db.update(TABLE_Recettes, values, Recette_ID + " = ?", new String[]{String.valueOf(recetteId)});
        db.close();
        if (result > 0) {
            Toast.makeText(context, "Recette Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to Update Recette", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRecette(int recetteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Recettes, Recette_ID + " = ?", new String[]{String.valueOf(recetteId)});
        Toast.makeText(context, "Recettes Deleted", Toast.LENGTH_SHORT).show();
        db.close();
    }


    public Cursor getRecettesByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Recette_ID,Recette_Title,Recette_Description, Recette_PERSONNES, Recette_TIMES,Recette_Ingrediants,Recette_Etape,Recette_User_ID};

        String selection = Recette_User_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        return db.query(
                TABLE_Recettes,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Cursor getAllRecettes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Recette_ID,Recette_Title,Recette_Description, Recette_PERSONNES, Recette_TIMES,Recette_Ingrediants,Recette_Etape,Recette_User_ID};


        return db.query(
                TABLE_Recettes,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    public byte[] getRecetteImage(int recetteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Recette_Image};

        String selection = Recette_ID + " = ?";
        String[] selectionArgs = {String.valueOf(recetteId)};

        Cursor cursor = db.query(
                TABLE_Recettes,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(Recette_Image));
            cursor.close();
            return image;
        } else {
            return null; // or a default image
        }
    }
}
