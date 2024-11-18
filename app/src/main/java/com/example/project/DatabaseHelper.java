package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EventsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_EVENTS = "events";

    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_HIGHLIGHT = "highlight";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_CAPACITY = "capacity";
    private static final String KEY_VOLUNTEERS_REQUIRED = "volunteers_required";
    private static final String KEY_VOLUNTEERS_REGISTERED = "volunteers_registered";
    private static final String KEY_VENDORS_REQUIRED = "vendors_required";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EVENT_NAME + " TEXT,"
                + KEY_HIGHLIGHT + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_VENUE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_CONTACT + " TEXT,"
                + KEY_CAPACITY + " INTEGER,"
                + KEY_VOLUNTEERS_REQUIRED + " INTEGER,"
                + KEY_VOLUNTEERS_REGISTERED + " INTEGER,"
                + KEY_VENDORS_REQUIRED + " INTEGER"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public long addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_EVENT_NAME, event.getEventName());
        values.put(KEY_HIGHLIGHT, event.getHighlight());
        values.put(KEY_DATE, event.getDate());
        values.put(KEY_TIME, event.getTime());
        values.put(KEY_VENUE, event.getVenue());
        values.put(KEY_DESCRIPTION, event.getDescription());
        values.put(KEY_CONTACT, event.getContact());
        values.put(KEY_CAPACITY, event.getCapacity());
        values.put(KEY_VOLUNTEERS_REQUIRED, event.getVolunteersRequired());
        values.put(KEY_VOLUNTEERS_REGISTERED, event.getVolunteersRegistered());
        values.put(KEY_VENDORS_REQUIRED, event.getVendorsRequired());

        long id = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Event> getAllEvents() {
        List<Event> eventsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                event.setEventName(cursor.getString(cursor.getColumnIndex(KEY_EVENT_NAME)));
                event.setHighlight(cursor.getString(cursor.getColumnIndex(KEY_HIGHLIGHT)));
                event.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                event.setVenue(cursor.getString(cursor.getColumnIndex(KEY_VENUE)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                event.setContact(cursor.getString(cursor.getColumnIndex(KEY_CONTACT)));
                event.setCapacity(cursor.getInt(cursor.getColumnIndex(KEY_CAPACITY)));
                event.setVolunteersRequired(cursor.getInt(cursor.getColumnIndex(KEY_VOLUNTEERS_REQUIRED)));
                event.setVolunteersRegistered(cursor.getInt(cursor.getColumnIndex(KEY_VOLUNTEERS_REGISTERED)));
                event.setVendorsRequired(cursor.getInt(cursor.getColumnIndex(KEY_VENDORS_REQUIRED)));

                eventsList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventsList;
    }


    public void deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?", new String[]{String.valueOf(eventId)});
        db.close();
    }

}