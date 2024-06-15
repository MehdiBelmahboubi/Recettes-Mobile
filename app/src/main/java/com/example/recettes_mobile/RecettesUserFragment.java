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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecettesUserFragment extends Fragment {
    private int connectedUserId = -1;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private UserRecipesAdapter adapter;
    private MyDatabaseHelper dbHelper;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recettes_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.recycle_user_random);

        if (getArguments() != null) {
            connectedUserId = getArguments().getInt("userId", -1);
        }
        boolean userConnected = true;

        dbHelper = new MyDatabaseHelper(getContext());
        cursor = dbHelper.getRecettesByUserId(connectedUserId);

        adapter = new UserRecipesAdapter(getContext(), cursor,userConnected);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", connectedUserId);
                Recettes_Add_User recettesAddUser = new Recettes_Add_User();
                recettesAddUser.setArguments(bundle);
                replaceFragement(recettesAddUser);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cursor != null) {
            cursor.close();
        }
    }

    private void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navHostFragement,fragment);
        fragmentTransaction.commit();
    }
}
