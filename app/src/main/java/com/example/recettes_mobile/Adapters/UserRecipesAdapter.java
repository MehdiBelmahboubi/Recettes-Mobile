package com.example.recettes_mobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recettes_mobile.MyDatabaseHelper;
import com.example.recettes_mobile.R;

import java.util.Random;

public class UserRecipesAdapter extends RecyclerView.Adapter<UserRecipesViewHolder> {
    private Context context;
    private Cursor cursor;
    private MyDatabaseHelper dbHelper;

    public UserRecipesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public UserRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false);
        return new UserRecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecipesViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            @SuppressLint("Range") int recetteId = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.Recette_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_Title));
            @SuppressLint("Range") String servings = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_PERSONNES)) + " Personnes";
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_TIMES));

            holder.title.setText(title);
            holder.servings.setText(servings);
            holder.time.setText(time);

            new Thread(() -> {
                byte[] imageBytes = dbHelper.getRecetteImage(recetteId);
                if (imageBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    holder.imageFood.post(() -> holder.imageFood.setImageBitmap(bitmap));
                } else {
                    holder.imageFood.post(() -> holder.imageFood.setImageResource(R.drawable.baseline_error)); // Replace with a placeholder image
                }
            }).start();

            holder.likes.setText("Likes: " + getLikes(title));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    private int getLikes(String title) {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }
}
class UserRecipesViewHolder extends RecyclerView.ViewHolder {
    TextView title, servings, likes, time;
    ImageView imageFood;

    public UserRecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.Title);
        imageFood = itemView.findViewById(R.id.image_food);
        servings = itemView.findViewById(R.id.text_serving);
        likes = itemView.findViewById(R.id.text_likes);
        time = itemView.findViewById(R.id.text_time);
    }
}