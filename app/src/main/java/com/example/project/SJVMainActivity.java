package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SJVMainActivity extends AppCompatActivity {

    private LinearLayout uploadsLayout;
    private static final int ADD_UPLOAD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjvactivity_main);

        // Set vendor name title
        TextView vendorName = findViewById(R.id.vendorName);
        vendorName.setText("Vendor's Name");

        // Initialize uploads layout
        uploadsLayout = findViewById(R.id.uploadsLayout);

        // Floating action button to add new upload
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(SJVMainActivity.this, SJVAddUploadActivity.class);
            startActivityForResult(intent, ADD_UPLOAD_REQUEST);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_UPLOAD_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get data from AddUploadActivity
            String imageUrl = data.getStringExtra("imageUrl");
            String price = data.getStringExtra("price");
            String description = data.getStringExtra("description");

            // Add to uploads layout
            addUploadToLayout(imageUrl, price, description);
        }
    }

    // Method to dynamically add new upload views
    private void addUploadToLayout(String imageUrl, String price, String description) {
        LinearLayout uploadLayout = new LinearLayout(this);
        uploadLayout.setOrientation(LinearLayout.VERTICAL);
        uploadLayout.setPadding(8, 8, 8, 8);

        // Placeholder ImageView
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                300
        ));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.placeholder_img); // Placeholder image

        // TextView for the price
        TextView priceView = new TextView(this);
        priceView.setText("Price: " + price);
        priceView.setTextSize(16);

        // Add views to uploadLayout
        uploadLayout.addView(imageView);
        uploadLayout.addView(priceView);

        uploadsLayout.addView(uploadLayout);
    }
}
