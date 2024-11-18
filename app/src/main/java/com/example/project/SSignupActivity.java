package com.example.project;

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

public class SSignupActivity extends AppCompatActivity {

    private EditText nameEdt, emailEdt, phoneEdt, passwordEdt, confirmPasswordEdt;
    private Spinner professionSpinner;
    private Button submitBtn;
    private SDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_signup);

        // Initialize fields
        nameEdt = findViewById(R.id.idEdtName);
        emailEdt = findViewById(R.id.idEdtEmail);
        phoneEdt = findViewById(R.id.idEdtPhone);
        passwordEdt = findViewById(R.id.idEdtPassword);
        confirmPasswordEdt = findViewById(R.id.idEdtConfirmPassword);
        professionSpinner = findViewById(R.id.idSpinnerProfession);
        submitBtn = findViewById(R.id.idBtnSubmit);

        // Initialize DBHandler
        dbHandler = new SDBHandler(SSignupActivity.this);

        // Set up spinner options
        setupSpinner();

        // Handle Submit Button Click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from fields
                String name = nameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String confirmPassword = confirmPasswordEdt.getText().toString();
                String profession = professionSpinner.getSelectedItem().toString();

                // Validate inputs
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || profession.equals("Select Profession")) {
                    Toast.makeText(SSignupActivity.this, "Please fill in all fields and select a valid profession.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.endsWith("@gmail.com")) {
                    Toast.makeText(SSignupActivity.this, "Invalid email. Please use a Gmail address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SSignupActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call addNewUser method from DBHandler
                boolean isSignupSuccessful = dbHandler.addNewUser(name, email, phone, password, profession);

                if (isSignupSuccessful) {
                    Toast.makeText(SSignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity and return to MainActivity
                } else {
                    Toast.makeText(SSignupActivity.this, "Signup failed. Email already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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

        // Handle spinner selection events (optional)
        professionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProfession = parent.getItemAtPosition(position).toString();
                if (!selectedProfession.equals("Select Profession")) {
                    Toast.makeText(SSignupActivity.this, "Selected: " + selectedProfession, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}

