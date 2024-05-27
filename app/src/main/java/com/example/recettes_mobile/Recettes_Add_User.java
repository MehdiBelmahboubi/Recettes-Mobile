package com.example.recettes_mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;

public class Recettes_Add_User extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextTitreRecette, editTextDescriptionRecette,editTextTemps,editTextPersonnes, editTextIngredientsRecette, editTextEtapeRecette;
    private ImageView imageRecette;
    private Button buttonAjouterRecette;
    private MyDatabaseHelper myDB;
    int connectedUserId = -1;
    private byte[] recetteImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recettes__add__user, container, false);

        editTextTitreRecette = view.findViewById(R.id.editTextTitreRecette);
        editTextDescriptionRecette = view.findViewById(R.id.editTextDescriptionRecette);
        editTextTemps = view.findViewById(R.id.editTextTimes);
        editTextPersonnes = view.findViewById(R.id.editTextPersonne);
        editTextIngredientsRecette = view.findViewById(R.id.editTextIngredientsRecette);
        editTextEtapeRecette = view.findViewById(R.id.editTextEtapeRecette);
        imageRecette = view.findViewById(R.id.imageRecette);
        buttonAjouterRecette = view.findViewById(R.id.buttonAjouterRecette);

        myDB = new MyDatabaseHelper(getActivity());

        if (getArguments() != null) {
            connectedUserId = getArguments().getInt("userId", -1);
            System.out.println(connectedUserId);
        }

        imageRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        buttonAjouterRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecette();
            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                imageRecette.setImageBitmap(bitmap);
                recetteImage = getBytesFromBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void addRecette() {
        String title = editTextTitreRecette.getText().toString().trim();
        String description = editTextDescriptionRecette.getText().toString().trim();
        int personnes = Integer.parseInt(editTextPersonnes.getText().toString().trim());
        String times = editTextTemps.getText().toString().trim();
        String ingredients = editTextIngredientsRecette.getText().toString().trim();
        String etapes = editTextEtapeRecette.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || times.isEmpty() || personnes <= 0) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Recettes Add", Toast.LENGTH_SHORT).show();
            replaceFragement(new ProfileFragment());
            myDB.AddRecette(title, description, personnes, times,ingredients,etapes, connectedUserId, recetteImage);
        }
    }
    private void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navHostFragement,fragment);
        fragmentTransaction.commit();
    }
}
