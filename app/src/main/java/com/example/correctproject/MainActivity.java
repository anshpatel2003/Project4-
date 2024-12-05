package com.example.correctproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.correctproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button for Chicago Style Pizza
        Button btnChicagoStyle = findViewById(R.id.btnChicagoStyle);
        btnChicagoStyle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChicagoStyleActivity.class);   // ChicagoActivity is the next activity to be opened
            startActivity(intent);
        });

        // Button for NY Style Pizza
        Button btnNYStyle = findViewById(R.id.btnNYStyle);
        btnNYStyle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NYActivity.class);
            startActivity(intent);
        });

        // Button for Current Order
        Button btnCurrentOrder = findViewById(R.id.btnCurrentOrder);
        btnCurrentOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
            startActivity(intent);
        });

        // Button for Orders Placed
        Button btnOrdersPlaced = findViewById(R.id.btnOrdersPlaced);
        btnOrdersPlaced.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrdersPlacedActivity.class);
            startActivity(intent);
        });
    }
}
