package com.example.recettes_mobile;

import static com.example.recettes_mobile.MyDatabaseHelper.User_NAME;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfilFragement extends Fragment {
    TextView usernameText,modifierButton;
    EditText usernameEditText,emailEditText,passwordEditText,repasswordEditText;
    int connectedUserId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profil_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameText = view.findViewById(R.id.usernameText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        repasswordEditText = view.findViewById(R.id.repasswordEditText);
        modifierButton = view.findViewById(R.id.modifierButton);


        if (getArguments() != null) {
            connectedUserId = getArguments().getInt("userId", -1);
        }

        if (connectedUserId > 0) {
            setUserName(connectedUserId);
        }

        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(v.getContext());
                myDB.UpdateUser(connectedUserId,
                        usernameEditText.getText().toString().trim(),
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        repasswordEditText.getText().toString().trim());
                setUserName(connectedUserId);
            }
        });
    }
    private void setUserName(int id)
    {
        MyDatabaseHelper myDB1 = new MyDatabaseHelper(requireContext());
        Cursor cursor = myDB1.getUserById(id);
        int user_name = cursor.getColumnIndex(User_NAME);
        if (cursor != null && cursor.moveToFirst() && user_name > 0) {
            String name = cursor.getString(user_name);
            usernameText.setText(name);
            cursor.close();
        }
    }
}