package com.example.recettes_mobile.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recettes_mobile.RecettesDetailsFragment;
import com.example.recettes_mobile.RecettesPageFragment;
import com.example.recettes_mobile.MyDatabaseHelper;
import com.example.recettes_mobile.R;

import java.util.Random;

public class UserRecipesAdapter extends RecyclerView.Adapter<UserRecipesViewHolder> {
    private Context context;
    private Cursor cursor;
    private MyDatabaseHelper dbHelper;
    private boolean userConnected;

    public UserRecipesAdapter(Context context, Cursor cursor,boolean userConnected) {
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = new MyDatabaseHelper(context);
        this.userConnected = userConnected;
    }

    @NonNull
    @Override
    public UserRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false);
        return new UserRecipesViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull UserRecipesViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            @SuppressLint("Range") int recetteId = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.Recette_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_Title));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_Description));
            @SuppressLint("Range") String servings = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_PERSONNES)) + " Personnes";
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_TIMES));
            @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_Ingrediants));
            @SuppressLint("Range") String etapes = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.Recette_Etape));
            @SuppressLint("Range") int recettesUserId = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.Recette_User_ID));

            holder.title.setText(title);
            holder.servings.setText(servings);
            holder.time.setText(time);
            int likes = getLikes();
            holder.likes.setText("Likes: " + likes);

            new Thread(() -> {
                byte[] imageBytes = dbHelper.getRecetteImage(recetteId);
                if (imageBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    holder.imageFood.post(() -> holder.imageFood.setImageBitmap(bitmap));
                } else {
                    holder.imageFood.post(() -> holder.imageFood.setImageResource(R.drawable.baseline_error)); // Replace with a placeholder image
                }
            }).start();

            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("id", recetteId);
                bundle.putString("title", title);
                bundle.putString("description", description);
                bundle.putInt("personnes", cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.Recette_PERSONNES)));
                bundle.putString("times", time);
                bundle.putInt("likes",likes);
                bundle.putString("ingredients", ingredients);
                bundle.putString("etapes", etapes);
                bundle.putByteArray("image", dbHelper.getRecetteImage(recetteId));
                bundle.putInt("userId", recettesUserId);
                if(userConnected==true){
                    RecettesPageFragment detailsFragment = new RecettesPageFragment();
                    detailsFragment.setArguments(bundle);

                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.navHostFragement, detailsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    RecettesDetailsFragment detailsFragment = new RecettesDetailsFragment();
                    detailsFragment.setArguments(bundle);

                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.navHostFragement, detailsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    private int getLikes() {
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