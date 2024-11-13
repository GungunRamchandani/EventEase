package com.example.project;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private int numRows = 5;
    private int numCols = 5;
    private int seatPrice = 12;
    private HashSet<Integer> selectedSeats = new HashSet<>();
    private HashSet<Integer> bookedSeats = new HashSet<>();
    private Button[][] seatButtons;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout seatGridLayout = findViewById(R.id.seatGridLayout);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        Button selectSeatsButton = findViewById(R.id.selectSeatsButton);
        Button bookShowButton = findViewById(R.id.bookShowButton);
        Button payNowButton = findViewById(R.id.payNowButton);

        seatButtons = new Button[numRows][numCols];
        loadBookedSeats();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                final int seatId = row * numCols + col + 1;
                final Button seatButton = new Button(this);
                seatButton.setText("Seat " + seatId);
                seatButton.setWidth(120);
                seatButton.setHeight(120);

                if (bookedSeats.contains(seatId)) {
                    seatButton.setBackgroundColor(Color.parseColor("#FFA500")); // Booked seats in gold orange
                    seatButton.setEnabled(false);
                } else {
                    seatButton.setBackgroundColor(Color.WHITE); // Available seats in white
                    seatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toggleSeatSelection(seatButton, seatId);
                        }
                    });
                }

                seatButtons[row][col] = seatButton;
                seatGridLayout.addView(seatButton);
            }
        }

        selectSeatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAllSeats();
            }
        });

        bookShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeBooking();
            }
        });

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    private void toggleSeatSelection(Button seatButton, int seatId) {
        if (selectedSeats.contains(seatId)) {
            selectedSeats.remove(seatId);
            seatButton.setBackgroundColor(Color.WHITE); // Deselect to available color
        } else {
            selectedSeats.add(seatId);
            seatButton.setBackgroundColor(Color.YELLOW); // Selected seat in yellow
        }
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int totalPrice = selectedSeats.size() * seatPrice;
        totalPriceTextView.setText("Total: $" + totalPrice);
    }

    private void loadBookedSeats() {
        bookedSeats.add(5);
        bookedSeats.add(8);
        bookedSeats.add(12);
        bookedSeats.add(16);
        bookedSeats.add(21);
    }

    private void deselectAllSeats() {
        for (Integer seatId : selectedSeats) {
            int row = (seatId - 1) / numCols;
            int col = (seatId - 1) % numCols;
            seatButtons[row][col].setBackgroundColor(Color.WHITE); // Reset to available color
        }
        selectedSeats.clear();
        updateTotalPrice();
    }

    private void finalizeBooking() {
        if (selectedSeats.isEmpty()) {
            totalPriceTextView.setText("No seats selected.");
        } else {
            int totalBill = selectedSeats.size() * seatPrice;
            bookedSeats.addAll(selectedSeats);
            selectedSeats.clear();
            updateTotalPrice();

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    int seatId = row * numCols + col + 1;
                    Button seatButton = seatButtons[row][col];
                    if (bookedSeats.contains(seatId)) {
                        seatButton.setBackgroundColor(Color.parseColor("#FFA500")); // Booked seats in gold orange
                        seatButton.setEnabled(false);
                    }
                }
            }

            Toast toast = Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            totalPriceTextView.setText("Total Bill: $" + totalBill);
        }
    }

    // Razorpay Payment Method
    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setImage(R.mipmap.ic_launcher); // Set your app logo

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            options.put("description", "Payment for Order #12345");
            options.put("currency", "INR");
            options.put("amount", "10000"); // Amount in paise (e.g., "10000" = ₹100)

            JSONObject prefill = new JSONObject();
            prefill.put("email", " ");
            prefill.put("contact", " ");
            options.put("prefill", prefill);
            options.put("method", "upi");

            checkout.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
    }
}
