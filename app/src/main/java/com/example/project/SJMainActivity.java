package com.example.project;



import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SJMainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Spinner categorySpinner;
    private TextView totalAmountText;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjactivity_main);

        // Set title to EventCraft
        TextView vendorAppTitle = findViewById(R.id.vendorAppTitle);
        vendorAppTitle.setText("EventCraft");

        // Initialize total amount TextView
        totalAmountText = findViewById(R.id.totalAmountText);
        totalAmountText.setText("Total: $0");

        // Find views
        gridLayout = findViewById(R.id.gridLayout);
        categorySpinner = findViewById(R.id.categorySpinner);

        // Set spinner listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                switch (selectedCategory) {
                    case "Lights":
                        displayLightsItems();
                        break;
                    case "Stages":
                        displayStageItems();
                        break;
                    case "Decoration":
                        displayDecorationItems();
                        break;
                    default:
                        gridLayout.setVisibility(View.GONE); // Hide grid if no valid category is selected
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gridLayout.setVisibility(View.GONE);
            }
        });
    }

    // Method to display light items in the grid layout
    private void displayLightsItems() {
        gridLayout.removeAllViews(); // Clear existing items
        gridLayout.setVisibility(View.VISIBLE);

        // Expanded list of images and prices for Lights category
        int[] imageResources = {
                R.drawable.light1, R.drawable.light2, R.drawable.light3,
                R.drawable.light4, R.drawable.light5, R.drawable.light6,
                R.drawable.light7, R.drawable.light8, R.drawable.light9,
                R.drawable.light10, R.drawable.light11, R.drawable.light12
        };
        String[] names = {
                "Satish", "Vinod", "Pritesh", "Ritesh", "Satish", "Vinod",
                "Pritesh", "Ritesh", "Satish", "Vinod", "Pritesh", "Ritesh"
        };
        String[] prices = {
                "$30", "$45", "$60", "$25", "$40", "$50",
                "$35", "$55", "$70", "$20", "$65", "$80"
        };

        populateGrid(imageResources, names, prices);
    }

    // Method to display stage items in the grid layout
    private void displayStageItems() {
        gridLayout.removeAllViews(); // Clear existing items
        gridLayout.setVisibility(View.VISIBLE);

        // Expanded list of images and prices for Stages category
        int[] imageResources = {
                R.drawable.stage1, R.drawable.stage2, R.drawable.stage3,
                R.drawable.stage4, R.drawable.stage5, R.drawable.stage6,
                R.drawable.stage7, R.drawable.stage8, R.drawable.stage9,
                R.drawable.stage10, R.drawable.stage11, R.drawable.stage12
        };
        String[] names = {
                "Satish", "Vinod", "Pritesh", "Ritesh", "Satish", "Vinod",
                "Pritesh", "Ritesh", "Satish", "Vinod", "Pritesh", "Ritesh"
        };
        String[] prices = {
                "$150", "$200", "$250", "$180", "$220", "$300",
                "$160", "$210", "$260", "$190", "$230", "$310"
        };

        populateGrid(imageResources, names, prices);
    }

    // Method to display decoration items in the grid layout
    private void displayDecorationItems() {
        gridLayout.removeAllViews(); // Clear existing items
        gridLayout.setVisibility(View.VISIBLE);

        // Expanded list of images and prices for Decoration category
        int[] imageResources = {
                R.drawable.decoration1, R.drawable.decoration2, R.drawable.decoration3,
                R.drawable.decoration4, R.drawable.decoration5, R.drawable.decoration6,
                R.drawable.decoration7, R.drawable.decoration8, R.drawable.decoration9,
                R.drawable.decoration10, R.drawable.decoration11, R.drawable.decoration12
        };
        String[] names = {
                "Satish", "Vinod", "Pritesh", "Ritesh", "Satish", "Vinod",
                "Pritesh", "Ritesh", "Satish", "Vinod", "Pritesh", "Ritesh"
        };
        String[] prices = {
                "$50", "$75", "$100", "$65", "$80", "$120",
                "$55", "$85", "$110", "$60", "$95", "$130"
        };

        populateGrid(imageResources, names, prices);
    }

    // Populate the grid with images, names, and prices
    private void populateGrid(int[] images, String[] names, String[] prices) {
        for (int i = 0; i < images.length; i++) {
            View itemLayout = getLayoutInflater().inflate(R.layout.sjgrid_item, gridLayout, false);

            ImageView imageView = itemLayout.findViewById(R.id.itemImage);
            imageView.setImageResource(images[i]);

            TextView nameText = itemLayout.findViewById(R.id.itemName);
            nameText.setText(names[i]);

            TextView priceText = itemLayout.findViewById(R.id.itemPrice);
            priceText.setText(prices[i]);

            // Use a final or effectively final variable to capture 'i' for the click listener
            final int index = i;  // Capture the value of 'i' in a final variable
            final String priceString = prices[i].replace("$", ""); // Save price without $

            // Create a flag to track whether the item has been selected or not
            final boolean[] isSelected = {false}; // Start with the item as unselected

            // Add click listener to display a toast message and update total amount
            imageView.setOnClickListener(v -> {
                int priceValue = Integer.parseInt(priceString);

                if (isSelected[0]) {
                    // Item is currently selected, so deselect it and subtract the price
                    totalAmount -= priceValue;
                    isSelected[0] = false; // Deselect item
                } else {
                    // Item is not selected, so select it and add the price
                    totalAmount += priceValue;
                    isSelected[0] = true; // Select item
                }

                // Display total amount in the TextView
                totalAmountText.setText("Total: $" + totalAmount);

                // Show Toast message
                Toast.makeText(SJMainActivity.this, (isSelected[0] ? "Added " : "Removed ") + names[index] + " for " + prices[index], Toast.LENGTH_SHORT).show();
            });

            gridLayout.addView(itemLayout);
        }
    }
}

