package com.example.project;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapterVolunteer extends RecyclerView.Adapter<EventAdapterVolunteer.EventViewHolder> {

    private List<Event> events = new ArrayList<>();
    private Context context;

    public EventAdapterVolunteer(Context context) {
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
                .inflate(R.layout.event_card_vol, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.eventName.setText(event.getEventName());
        holder.date.setText(event.getDate());
        holder.venue.setText(event.getVenue());
        holder.highlight.setText(event.getHighlight());

        holder.viewDetailsButton.setOnClickListener(v -> showDetailsDialog(event));

        holder.registerButton.setOnClickListener(v -> registerVolunteer(event, holder));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    private void showDetailsDialog(Event event) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.register_button); // Update with the new layout

        TextView eventName = dialog.findViewById(R.id.dialogEventName);
        TextView highlight = dialog.findViewById(R.id.dialogHighlight);
        TextView date = dialog.findViewById(R.id.dialogDate);
        TextView time = dialog.findViewById(R.id.dialogTime);
        TextView venue = dialog.findViewById(R.id.dialogVenue);
        TextView description = dialog.findViewById(R.id.dialogDescription);
        TextView contact = dialog.findViewById(R.id.dialogContact);
        TextView capacity = dialog.findViewById(R.id.dialogCapacity);
        TextView volunteersRequired = dialog.findViewById(R.id.dialogVolunteersRequired);
        TextView volunteersRegistered = dialog.findViewById(R.id.dialogVolunteersRegistered);
        TextView vendorsRequired = dialog.findViewById(R.id.dialogVendorsRequired);

        eventName.setText("Event: " + event.getEventName());
        highlight.setText("Highlight: " + event.getHighlight());
        date.setText("Date: " + event.getDate());
        time.setText("Time: " + event.getTime());
        venue.setText("Venue: " + event.getVenue());
        description.setText("Description: " + event.getDescription());
        contact.setText("Contact: " + event.getContact());
        capacity.setText("Capacity: " + event.getCapacity());
        volunteersRequired.setText("Volunteers Required: " + event.getVolunteersRequired());
        volunteersRegistered.setText("Volunteers Registered: " + event.getVolunteersRegistered());
        vendorsRequired.setText("Vendors Required: " + event.getVendorsRequired());

        Button register = dialog.findViewById(R.id.dialogRegisterButton);
        register.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialog.show();
    }

    private void registerVolunteer(Event event, EventViewHolder holder) {
        if (Integer.parseInt(event.getVolunteersRegistered()) <Integer.parseInt( event.getVolunteersRequired())) {
            event.incrementVolunteersRegistered(); // Update volunteers registered

            holder.volunteersRegistered.setText("Volunteers Registered: " + event.getVolunteersRegistered());
            notifyDataSetChanged(); // Refresh the adapter

            Toast.makeText(context, "Successfully registered as a volunteer!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Volunteer capacity full!", Toast.LENGTH_SHORT).show();
        }
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, date, venue, highlight, volunteersRegistered;
        Button viewDetailsButton, registerButton;
        CardView cardView;

        EventViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.date);
            venue = itemView.findViewById(R.id.venue);
            highlight = itemView.findViewById(R.id.highlight);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            registerButton = itemView.findViewById(R.id.registerButton);
            volunteersRegistered = itemView.findViewById(R.id.volunteersRegistered);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
