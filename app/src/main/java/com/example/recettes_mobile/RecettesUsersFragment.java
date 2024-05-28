package com.example.recettes_mobile;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recettes_mobile.Adapters.UserRecipesAdapter;
import com.example.recettes_mobile.R;


public class RecettesUsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyDatabaseHelper dbHelper;
    private Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recettes_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_users_random);

        boolean userConnected = false;

        dbHelper = new MyDatabaseHelper(getContext());
        cursor = dbHelper.getAllRecettes();
        UserRecipesAdapter adapter = new UserRecipesAdapter(getContext(), cursor, userConnected);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cursor != null) {
            cursor.close();
        }
    }
}