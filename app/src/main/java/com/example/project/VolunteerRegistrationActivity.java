package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VolunteerRegistrationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventAdapterVolunteer eventAdapter;
    private DatabaseHelper dbHelper;
    private TextView noEventsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        noEventsText = findViewById(R.id.noEventsText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapterVolunteer(this);
        recyclerView.setAdapter(eventAdapter);


        updateEventsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEventsList();
    }

    private void updateEventsList() {
        List<Event> events = dbHelper.getAllEvents();
        eventAdapter.setEvents(events);

        if (events.isEmpty()) {
            noEventsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noEventsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
