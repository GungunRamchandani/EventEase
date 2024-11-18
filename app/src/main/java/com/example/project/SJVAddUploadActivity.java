package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class SJVAddUploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewSelected;
    private EditText editTextPrice;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjvactivity_add_upload);

        imageViewSelected = findViewById(R.id.imageViewSelected);
        editTextPrice = findViewById(R.id.editTextPrice);
        Button buttonChooseImage = findViewById(R.id.buttonChooseImage);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // Open file picker when "Choose Image" button is clicked
        buttonChooseImage.setOnClickListener(view -> openFileChooser());

        // Handle the "Submit" button click
        buttonSubmit.setOnClickListener(view -> submitData());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Retrieve the image URI
            try {
                // Convert the URI to a Bitmap and display it in the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewSelected.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load the image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitData() {
        String price = editTextPrice.getText().toString().trim();

        if (imageUri == null) {
            Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (price.isEmpty()) {
            Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display a confirmation message (or save the data in a database, etc.)
        Toast.makeText(this, "Image and Price saved!", Toast.LENGTH_SHORT).show();

        // Reset fields after submission
        editTextPrice.setText("");
        imageViewSelected.setImageResource(android.R.color.transparent);
    }
}