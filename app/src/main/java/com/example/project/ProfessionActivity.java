package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ProfessionActivity extends AppCompatActivity {

    private Spinner professionSpinner;
    private Button confirmButton;
    private ProfessionManager professionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);  // Use the layout you just created

        professionSpinner = findViewById(R.id.professionSpinner);
        confirmButton = findViewById(R.id.confirmButton);

        // Create an instance of the ProfessionManager to handle the Spinner logic
        professionManager = new ProfessionManager(professionSpinner);

        // Set up the button click listener
        professionManager.setConfirmButtonClickListener(this, confirmButton);
    }
}
