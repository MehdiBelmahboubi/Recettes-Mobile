package com.example.recettes_mobile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

        int connectedUserId=0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            connectedUserId = extras.getInt("userId");
        }
        int connectUserId = connectedUserId;

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if(id == R.id.recipes)
            {
                replaceFragement(new RecipesFragment());
            } else if (id == R.id.recherche) {
                replaceFragement(new SearchFragment());
            }else if (id == R.id.Recettes_Users) {
                replaceFragement(new RecipesFragment());
            } else if (id==R.id.profile) {
                if (connectUserId > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", connectUserId);
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    replaceFragement(profileFragment);
                } else {
                    replaceFragement(new ConnectionFragment());
                }
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