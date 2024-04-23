package com.example.recettes_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recettes_mobile.Modeles.Recipe;
import com.example.recettes_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<Recipe> liste;

    public RandomRecipeAdapter(Context context, List<Recipe> liste) {
        this.context = context;
        this.liste = liste;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.Title.setText(liste.get(position).title);
        holder.Title.setSelected(true);
        holder.text_likes.setText(liste.get(position).aggregateLikes+" Likes");
        holder.text_serving.setText(liste.get(position).servings+" Servings");
        holder.text_time.setText(liste.get(position).readyInMinutes+" Minutes");
        Picasso.get().load(liste.get(position).image).into(holder.image_food);
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder{
    CardView random_list_container;
    TextView Title,text_serving,text_likes,text_time;
    ImageView image_food;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        Title = itemView.findViewById(R.id.Title);
        text_serving = itemView.findViewById(R.id.text_serving);
        text_likes = itemView.findViewById(R.id.text_likes);
        text_time = itemView.findViewById(R.id.text_time);
        image_food = itemView.findViewById(R.id.image_food);

    }
}
