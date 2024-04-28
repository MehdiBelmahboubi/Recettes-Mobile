package com.example.recettes_mobile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recettes_mobile.Adapters.RandomRecipeAdapter;
import com.example.recettes_mobile.Listeners.RandomRecipeResponseListener;
import com.example.recettes_mobile.Modeles.RandomRecipeApiRespondes;
import com.example.recettes_mobile.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragement(new RecipesFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if(id == R.id.recipes)
            {
                replaceFragement(new RecipesFragment());
            } else if (id == R.id.recherche) {
                replaceFragement(new SearchFragment());
            }else if (id == R.id.favorite) {
                replaceFragement(new FavoritesFragment());
            } else if (id==R.id.profile) {
                replaceFragement(new ConnectionFragment());
            }
            return true;
        });
    }

    private void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navHostFragement,fragment);
        fragmentTransaction.commit();
    }
}