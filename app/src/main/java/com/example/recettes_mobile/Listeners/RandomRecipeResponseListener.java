package com.example.recettes_mobile.Listeners;


import com.example.recettes_mobile.Modeles.RandomRecipeApiRespondes;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiRespondes response , String message);
    void dedError(String message);
}
