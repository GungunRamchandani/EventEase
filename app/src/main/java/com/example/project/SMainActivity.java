package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SMainActivity extends AppCompatActivity {

    // Declare variables for EditText, Button, and DBHandler
    private EditText emailEdt, passwordEdt;
    private Button loginBtn, signupBtn;
    private SDBHandler dbHandler;
    private Spinner professionSpinner;
    private String selectedProfession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_main);

        // Initialize EditText fields, Buttons, and Spinner
        emailEdt = findViewById(R.id.idEdtEmail);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.BtnLogin);
        signupBtn = findViewById(R.id.BtnSignup);
        professionSpinner = findViewById(R.id.SpinnerRole); // Initialize Spinner

        // Create a new DBHandler instance
        dbHandler = new SDBHandler(SMainActivity.this);

        // Set up the spinner
        setupSpinner();

        // Set up OnClickListener for the login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve email and password input
                String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                // Validate email format
                if (!email.endsWith("@gmail.com")) {
                    Toast.makeText(SMainActivity.this, "Login failed. Please enter a valid Gmail address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate input fields
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SMainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call loginUser method in DBHandler to verify credentials
                boolean isAuthenticated = dbHandler.loginUser(email, password);

                // Display success or failure message
                if (isAuthenticated) {
                    Toast.makeText(SMainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Check if "User" profession is selected and navigate to MainActivity
                    if (selectedProfession.equals("User")) {
                        Intent intent = new Intent(SMainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    //else if
                    else if(selectedProfession.equals("Organizer")){
                        Intent intent = new Intent(SMainActivity.this, AKMainActivity.class);
                        startActivity(intent);
                    }
                    else if(selectedProfession.equals("Vendor")){
                        Intent intent = new Intent(SMainActivity.this, SJVMainActivity.class);
                        startActivity(intent);
                    }
                    else if(selectedProfession.equals("Select Profession")){
                        Toast.makeText(SMainActivity.this, "Select atleast one profession", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SMainActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up OnClickListener for the signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignupActivity
                Intent intent = new Intent(SMainActivity.this, SSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to set up the spinner
    private void setupSpinner() {
        // Create a list of options for the spinner
        List<String> professionList = new ArrayList<>();
        professionList.add("Select Profession"); // Default option
        professionList.add("Organizer");
        professionList.add("Vendor");
        professionList.add("Volunteer");
        professionList.add("User");

        // Create an ArrayAdapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                professionList
        );

        // Set the layout for the dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach the adapter to the spinner
        professionSpinner.setAdapter(adapter);

        // Handle spinner selection events
        professionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProfession = parent.getItemAtPosition(position).toString();
                if (!selectedProfession.equals("Select Profession")) {
                    Toast.makeText(SMainActivity.this, "Selected: " + selectedProfession, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
