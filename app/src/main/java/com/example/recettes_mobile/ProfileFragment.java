package com.example.recettes_mobile;

import static com.example.recettes_mobile.MyDatabaseHelper.User_Email;
import static com.example.recettes_mobile.MyDatabaseHelper.User_NAME;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    TextView editProfile,RecettesText,userTitle,userName,userEmail;

    int connectedUserId = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editProfile = view.findViewById(R.id.editText);
        RecettesText = view.findViewById(R.id.RecettesText);
        userTitle = view.findViewById(R.id.userTitle);
        userName = view.findViewById(R.id.usernameText);
        userEmail = view.findViewById(R.id.UserEmail);

        if (getArguments() != null) {
            connectedUserId = getArguments().getInt("userId", -1);
        }

        if (connectedUserId > 0) {
            displayUserInfo();
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", connectedUserId);
                EditProfilFragement editProfilFragement = new EditProfilFragement();
                editProfilFragement.setArguments(bundle);
                replaceFragement(editProfilFragement);
            }
        });

        RecettesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", connectedUserId);
                RecettesUserFragment recettesUserFragment = new RecettesUserFragment();
                recettesUserFragment.setArguments(bundle);
                replaceFragement(recettesUserFragment);
            }
        });
    }

    private void displayUserInfo() {
        MyDatabaseHelper myDB = new MyDatabaseHelper(requireContext());
        Cursor cursor = myDB.getUserById(connectedUserId);
        int user_name = cursor.getColumnIndex(User_NAME);
        int user_email = cursor.getColumnIndex(User_Email);
        if (cursor != null && cursor.moveToFirst() && user_name > 0 && user_email > 0 ) {
            String name = cursor.getString(user_name);
            String email = cursor.getString(user_email);


            userTitle.setText(name);
            userName.setText(name);
            userEmail.setText(email);

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