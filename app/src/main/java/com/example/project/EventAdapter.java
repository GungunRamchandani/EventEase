package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private Context context;

    public EventAdapter(Context context) {
        this.context = context;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.eventName.setText(event.getEventName());
        holder.date.setText(event.getDate());
        holder.venue.setText(event.getVenue());
        holder.highlight.setText(event.getHighlight());

        // Set onClickListener for the View Details button
        holder.viewDetailsButton.setOnClickListener(v -> showDetailsDialog(event));


//        New
        // Set onClickListener for the Register button to redirect to GMainActivity
        holder.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, SeatSelection.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    private void showDetailsDialog(Event event) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_event_details);

        TextView eventName = dialog.findViewById(R.id.dialogEventName);
        TextView highlight = dialog.findViewById(R.id.dialogHighlight);
        TextView date = dialog.findViewById(R.id.dialogDate);
        TextView time = dialog.findViewById(R.id.dialogTime);
        TextView venue = dialog.findViewById(R.id.dialogVenue);
        TextView description = dialog.findViewById(R.id.dialogDescription);
        TextView contact = dialog.findViewById(R.id.dialogContact);

        // Set text for fields you want to display
        eventName.setText("Event: " + event.getEventName());
        highlight.setText("Highlight: " + event.getHighlight());
        date.setText("Date: " + event.getDate());
        time.setText("Time: " + event.getTime());
        venue.setText("Venue: " + event.getVenue());
        description.setText("Description: " + event.getDescription());
        contact.setText("Contact: " + event.getContact());

        Button closeButton = dialog.findViewById(R.id.dialogCloseButton);
        closeButton.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialog.show();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, date, venue, highlight;
        Button viewDetailsButton, registerButton;
        CardView cardView;

        EventViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.date);
            venue = itemView.findViewById(R.id.venue);
            highlight = itemView.findViewById(R.id.highlight);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            registerButton = itemView.findViewById(R.id.registerButton); // Added the register button
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
