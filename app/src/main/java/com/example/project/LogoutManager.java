package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogoutManager {

    public static void logout(Context context) {
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to Login Activity
        Intent intent = new Intent(context,SMainActivity.class);
        context.startActivity(intent);

        // Optionally finish the current activity to prevent going back
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).finish();
        }
    }
}
