package com.example.project;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class AKMainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private DatabaseHelper dbHelper;
    private TextView noEventsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        noEventsText = findViewById(R.id.noEventsText);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(this);
        recyclerView.setAdapter(eventAdapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            startActivity(intent);
        });

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
