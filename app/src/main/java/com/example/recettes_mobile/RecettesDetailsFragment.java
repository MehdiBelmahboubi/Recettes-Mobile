package com.example.recettes_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class RecettesDetailsFragment extends Fragment {
    private TextView title, description, personnes, times,likes, ingredients, etapes;
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_recettes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.recette_title);
        description = view.findViewById(R.id.recette_description);
        personnes = view.findViewById(R.id.recette_personnes);
        times = view.findViewById(R.id.recette_times);
        likes = view.findViewById(R.id.recettes_likes);
        ingredients = view.findViewById(R.id.recette_ingredients);
        etapes = view.findViewById(R.id.recette_etapes);
        imageView = view.findViewById(R.id.recette_image);


        if (getArguments() != null) {
            title.setText(getArguments().getString("title"));
            description.setText(getArguments().getString("description"));
            personnes.setText(String.valueOf(getArguments().getInt("personnes") + " Personnes"));
            times.setText(getArguments().getString("times"));
            likes.setText(String.valueOf(getArguments().getInt("likes") + " Likes"));
            ingredients.setText(getArguments().getString("ingredients"));
            etapes.setText(getArguments().getString("etapes"));

            byte[] imageBytes = getArguments().getByteArray("image");
            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.baseline_error);
            }
        }
    }
}