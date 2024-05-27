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

import com.example.recettes_mobile.R;

public class UserRecipesAdapter extends RecyclerView.Adapter<UserRecipesViewHolder> {
    private Context context;
    private Cursor cursor;

    public UserRecipesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("recette_title"));
            @SuppressLint("Range") String servings = cursor.getString(cursor.getColumnIndex("recette_personnes")) + " Personnes";
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("recette_times"));
            @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex("recette_image"));

            holder.title.setText(title);
            holder.servings.setText(servings);
            holder.time.setText(time);

            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imageFood.setImageBitmap(bitmap);
            } else {
                holder.imageFood.setImageResource(R.drawable.baseline_error); // Replace with a placeholder image
            }

            holder.likes.setText("Likes: " + getLikes(title)); // Assuming you have a method to get likes
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    private int getLikes(String title) {
        // This is a placeholder method. Replace with actual logic to get likes.
        return 0;
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