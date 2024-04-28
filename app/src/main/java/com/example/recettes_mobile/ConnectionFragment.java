package com.example.recettes_mobile;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;


public class ConnectionFragment extends Fragment {
    TextView textViewRegistre,textViewUser,textViewPasswd;
    Button btnCnx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewRegistre = view.findViewById(R.id.Register);
        textViewUser = view.findViewById(R.id.Email);
        textViewPasswd = view.findViewById(R.id.Passwd);
        btnCnx = view.findViewById(R.id.login);

        btnCnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(v.getContext());
                if(myDB.ConnectUser(textViewUser.getText().toString().trim(),textViewPasswd.getText().toString().trim())==true){
                    replaceFragement(new ProfileFragment());
                }
                else {
                    Toast.makeText(getContext(),"Authentification Error!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragement(new InscriptionFragment());
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