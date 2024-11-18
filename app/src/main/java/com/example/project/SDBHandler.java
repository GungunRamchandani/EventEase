package com.example.project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SDBHandler extends SQLiteOpenHelper {

    // Database information
    private static final String DB_NAME = "userdb";
    private static final int DB_VERSION = 2; // Updated version for schema changes
    private static final String TABLE_NAME = "accounts";

    // Column names
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String PHONE_COL = "phone";
    private static final String PASSWORD_COL = "password";
    private static final String PROFESSION_COL = "profession";

    // Constructor
    public SDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create accounts table with additional columns
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + EMAIL_COL + " TEXT, "
                + PHONE_COL + " TEXT, "
                + PASSWORD_COL + " TEXT, "
                + PROFESSION_COL + " TEXT)";

        db.execSQL(query);
    }

    // Method to add a new user to the database
    public boolean addNewUser(String name, String email, String phone, String password, String profession) {
        // Validate email
        if (!email.endsWith("@gmail.com")) {
            return false; // Invalid email format
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Check if email already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Email already registered
        }

        // Insert new user data
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        values.put(PASSWORD_COL, password);
        values.put(PROFESSION_COL, profession);

        long result = db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();

        return result != -1;
    }

    // Method for user login
    public boolean loginUser(String email, String password) {
        // Validate email
        if (!email.endsWith("@gmail.com")) {
            return false; // Invalid email format
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "
                + EMAIL_COL + " = ? AND "
                + PASSWORD_COL + " = ?", new String[]{email, password});

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isAuthenticated;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method for user signup
    public boolean signupUser(String name, String email, String phone, String password, String profession) {
        // Validate email
        if (!email.endsWith("@gmail.com")) {
            return false; // Invalid email format
        }

        // Validate non-empty fields
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || profession.isEmpty()) {
            return false; // Missing required fields
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Check if email already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Email already registered
        }

        // Insert new user data
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        values.put(PASSWORD_COL, password);
        values.put(PROFESSION_COL, profession);

        long result = db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();

        return result != -1; // Return true if insertion was successful
    }

}