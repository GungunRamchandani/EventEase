package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.AKMainActivity;
import com.example.project.MainActivity;
import com.example.project.SJVMainActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfessionManager {

    private Spinner professionSpinner;
    private String selectedProfession;

    public ProfessionManager(Spinner professionSpinner) {
        this.professionSpinner = professionSpinner;
        setupSpinner();
    }

    // Setup Spinner with profession options
    private void setupSpinner() {
        List<String> professionList = new ArrayList<>();
        professionList.add("Select Profession"); // Default option
        professionList.add("Organizer");
        professionList.add("Vendor");
        professionList.add("Volunteer");
        professionList.add("User");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                (Context) professionSpinner.getContext(),
                android.R.layout.simple_spinner_item,
                professionList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        professionSpinner.setAdapter(adapter);

        professionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProfession = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Get the selected profession
    public String getSelectedProfession() {
        return selectedProfession;
    }

    // Method to handle the profession change when the Confirm button is clicked
    public void setConfirmButtonClickListener(final Context context, Button confirmButton) {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedProfession = getSelectedProfession();

                if (selectedProfession.equals("Select Profession")) {
                    Toast.makeText(context, "Select a valid profession", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle navigation based on profession
                Intent intent = null;
                switch (selectedProfession) {
                    case "User":
                        intent = new Intent(context, MainActivity.class);
                        break;
                    case "Organizer":
                        intent = new Intent(context, AKMainActivity.class);
                        break;
                    case "Vendor":
                        intent = new Intent(context, SJVMainActivity.class);
                        break;
                    case "Volunteer":
                        Toast.makeText(context, "Volunteer profession selected", Toast.LENGTH_SHORT).show();
                        return;
                    default:
                        Toast.makeText(context, "Invalid profession", Toast.LENGTH_SHORT).show();
                        return;
                }

                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });
    }
}
