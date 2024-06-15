package com.example.recettes_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recettes_mobile.R;

public class RecettesPageFragment extends Fragment {
    private int recetteId,user_id;
    private TextView title, description, personnes, times,likes, ingredients, etapes;
    private ImageView imageView;
    private Button button_modifier,button_supprimer;
    private MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recettes_page, container, false);
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
        button_modifier = view.findViewById(R.id.Modifier);
        button_supprimer = view.findViewById(R.id.Supprimer);

        myDB = new MyDatabaseHelper(getActivity());

        if (getArguments() != null) {
            recetteId = getArguments().getInt("id");
            user_id = getArguments().getInt("userId");
            title.setText(getArguments().getString("title"));
            description.setText(getArguments().getString("description"));
            personnes.setText(String.valueOf(getArguments().getInt("personnes")+" Personnes"));
            times.setText(getArguments().getString("times"));
            likes.setText(String.valueOf(getArguments().getInt("likes")+" Likes"));
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

        button_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", user_id);
                RecettesUserFragment recettesUserFragment = new RecettesUserFragment();
                recettesUserFragment.setArguments(bundle);
                replaceFragement(recettesUserFragment);
                myDB.deleteRecette(recetteId);
            }
        });
    }

    private void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navHostFragement,fragment);
        fragmentTransaction.commit();
    }
}