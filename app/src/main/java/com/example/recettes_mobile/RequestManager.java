package com.example.recettes_mobile;

import android.content.Context;


import com.example.recettes_mobile.Listeners.RandomRecipeResponseListener;
import com.example.recettes_mobile.Modeles.RandomRecipeApiRespondes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener,List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiRespondes> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"10",tags);
        call.enqueue(new Callback<RandomRecipeApiRespondes>() {
            @Override
            public void onResponse(Call<RandomRecipeApiRespondes> call, Response<RandomRecipeApiRespondes> response) {
                if(!response.isSuccessful()){
                    listener.dedError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiRespondes> call, Throwable throwable) {
                listener.dedError(throwable.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiRespondes> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
}
