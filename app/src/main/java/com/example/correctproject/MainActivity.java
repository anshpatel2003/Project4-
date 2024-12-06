/**
 * Author Jeet Soni, Ansh Patel
 */


package com.example.correctproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * This method sets up the UI and initializes the buttons that navigate to different activities.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this contains the data it most recently supplied
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button for Chicago Style Pizza
        Button btnChicagoStyle = findViewById(R.id.btnChicagoStyle);
        btnChicagoStyle.setOnClickListener(v -> {
            // Start the ChicagoStyleActivity when clicked
            Intent intent = new Intent(MainActivity.this, ChicagoStyleActivity.class);
            startActivity(intent);
        });

        // Button for NY Style Pizza
        Button btnNYStyle = findViewById(R.id.btnNYStyle);
        btnNYStyle.setOnClickListener(v -> {
            // Start the NYActivity when clicked
            Intent intent = new Intent(MainActivity.this, NYActivity.class);
            startActivity(intent);
        });

        // Button for Current Order
        Button btnCurrentOrder = findViewById(R.id.btnCurrentOrder);
        btnCurrentOrder.setOnClickListener(v -> {
            // Start the CurrentOrderActivity when clicked
            Intent intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
            startActivity(intent);
        });

        // Button for Orders Placed
        Button btnOrdersPlaced = findViewById(R.id.btnOrdersPlaced);
        btnOrdersPlaced.setOnClickListener(v -> {
            // Start the OrdersPlacedActivity when clicked
            Intent intent = new Intent(MainActivity.this, OrdersPlacedActivity.class);
            startActivity(intent);
        });
    }
}
