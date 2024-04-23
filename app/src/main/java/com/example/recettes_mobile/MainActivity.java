package com.example.recettes_mobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recettes_mobile.Adapters.RandomRecipeAdapter;
import com.example.recettes_mobile.Listeners.RandomRecipeResponseListener;
import com.example.recettes_mobile.Modeles.RandomRecipeApiRespondes;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponseListener);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiRespondes response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycle_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            randomRecipeAdapter =new RandomRecipeAdapter(MainActivity.this,response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void dedError(String message) {
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT);
        }
    };
}