package com.example.project;

public class Event {
    private int id;
    private String eventName;
    private String highlight;
    private String date;
    private String time;
    private String venue;
    private String description;
    private String contact;
    private int capacity;
    private int volunteersRequired;
    private int volunteersRegistered;
    private int vendorsRequired;

    // Default constructor
    public Event() {
    }

    // Constructor with parameters
    public Event(String eventName, String highlight, String date, String time,
                 String venue, String description, String contact, int capacity,
                 int volunteersRequired, int volunteersRegistered, int vendorsRequired) {
        this.eventName = eventName;
        this.highlight = highlight;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.contact = contact;
        this.capacity = capacity;
        this.volunteersRequired = volunteersRequired;
        this.volunteersRegistered = volunteersRegistered;
        this.vendorsRequired = vendorsRequired;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getHighlight() { return highlight; }
    public void setHighlight(String highlight) { this.highlight = highlight; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getVolunteersRequired() { return volunteersRequired; }
    public void setVolunteersRequired(int volunteersRequired) {
        this.volunteersRequired = volunteersRequired;
    }

    public int getVolunteersRegistered() { return volunteersRegistered; }
    public void setVolunteersRegistered(int volunteersRegistered) {
        this.volunteersRegistered = volunteersRegistered;
    }

    public int getVendorsRequired() { return vendorsRequired; }
    public void setVendorsRequired(int vendorsRequired) {
        this.vendorsRequired = vendorsRequired;
    }
}