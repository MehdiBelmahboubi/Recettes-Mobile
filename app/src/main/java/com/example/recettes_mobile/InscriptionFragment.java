package com.example.recettes_mobile;

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


public class InscriptionFragment extends Fragment {
    TextView textView_back,TextView_UserName,TextView_Email,TextView_Passwd,TextView_RePasswd;
    Button btn_sign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inscription, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_back = view.findViewById(R.id.back);
        TextView_UserName = view.findViewById(R.id.NameText);
        TextView_Email = view.findViewById(R.id.EmailText);
        TextView_Passwd = view.findViewById(R.id.PasswdText);
        TextView_RePasswd = view.findViewById(R.id.RePasswdText);
        btn_sign = view.findViewById(R.id.signupbtn);


        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(v.getContext());
                myDB.AddUser(TextView_UserName.getText().toString().trim(),
                        TextView_Email.getText().toString().trim(),
                        TextView_Passwd.getText().toString().trim(),
                        TextView_RePasswd.getText().toString().trim());
            }
        });

        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragement(new ConnectionFragment());
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