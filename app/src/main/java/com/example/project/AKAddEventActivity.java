package com.example.project;



import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AKAddEventActivity extends AppCompatActivity {
    private EditText eventNameInput, highlightInput, dateInput, timeInput,
            venueInput, descriptionInput, contactInput, capacityInput,
            volunteersRequiredInput, vendorsRequiredInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dbHelper = new DatabaseHelper(this);
        initializeViews();
        setupTimePickerDialog();
        setupButtons();
    }

    private void initializeViews() {
        eventNameInput = findViewById(R.id.eventNameInput);
        highlightInput = findViewById(R.id.highlightInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        venueInput = findViewById(R.id.venueInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        contactInput = findViewById(R.id.contactInput);
        capacityInput = findViewById(R.id.capacityInput);
        volunteersRequiredInput = findViewById(R.id.volunteersRequiredInput);
        vendorsRequiredInput = findViewById(R.id.vendorsRequiredInput);
    }

    private void setupTimePickerDialog() {
        timeInput.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddEventActivity.this,
                    (view, hourOfDay, selectedMinute) -> {
                        String amPm;
                        int displayHour = hourOfDay;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                            if (hourOfDay > 12) displayHour -= 12;
                        } else {
                            amPm = "AM";
                            if (displayHour == 0) displayHour = 12;
                        }
                        timeInput.setText(String.format("%d:%02d %s", displayHour, selectedMinute, amPm));
                    },
                    hour,
                    minute,
                    false
            );
            timePickerDialog.show();
        });
    }

    private void setupButtons() {
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(v -> saveEvent());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveEvent() {
        Event event = new Event(
                eventNameInput.getText().toString(),
                highlightInput.getText().toString(),
                dateInput.getText().toString(),
                timeInput.getText().toString(),
                venueInput.getText().toString(),
                descriptionInput.getText().toString(),
                contactInput.getText().toString(),
                Integer.parseInt(capacityInput.getText().toString()),
                Integer.parseInt(volunteersRequiredInput.getText().toString()),
                0, // Initial volunteers registered is 0
                Integer.parseInt(vendorsRequiredInput.getText().toString())
        );

        dbHelper.addEvent(event);
        finish();
    }
}