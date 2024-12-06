package com.example.correctproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class CurrentOrderActivity extends AppCompatActivity {

    private RecyclerView pizzaRecyclerView;
    private EditText subtotalField;
    private EditText salesTaxField;
    private EditText orderTotalField;

    private StoreOrders storeOrders;
    private Order currentOrder;
    private static final double SALES_TAX_RATE = 0.07;
    private DecimalFormat df = new DecimalFormat("0.00");

    private PizzaAdapter pizzaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        EditText orderNumberField = findViewById(R.id.orderNumberField);
        pizzaRecyclerView = findViewById(R.id.pizzaRecyclerView);
        subtotalField = findViewById(R.id.subtotalField);
        salesTaxField = findViewById(R.id.salesTaxField);
        orderTotalField = findViewById(R.id.orderTotalField);
        Button removePizzaButton = findViewById(R.id.removePizzaButton);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button clearOrderButton = findViewById(R.id.clearOrderButton);

        storeOrders = StoreOrders.getInstance();

        if (storeOrders.getOrders().isEmpty()) {
            return;
        }

        currentOrder = storeOrders.getOrders().get(storeOrders.getOrders().size() - 1);
        if (currentOrder == null) {
            return;
        }

        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));

        pizzaAdapter = new PizzaAdapter(currentOrder.getPizzas(), this::onPizzaRemoved);
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaRecyclerView.setAdapter(pizzaAdapter);

        updateTotals();

        removePizzaButton.setOnClickListener(view -> handleRemovePizza());
        placeOrderButton.setOnClickListener(view -> handlePlaceOrder());
        clearOrderButton.setOnClickListener(view -> handleCancelOrder());
    }

    private void onPizzaRemoved(Pizza pizza) {
        currentOrder.removePizza(pizza);
        pizzaAdapter.notifyDataSetChanged();
        updateTotals();
        showToast("Pizza successfully removed from the order!");
    }

    private void handleRemovePizza() {
        showToast("Select a pizza to remove from the list.");
    }

    private void handlePlaceOrder() {
        storeOrders.updateOrderStatus(currentOrder, true);
        updateTotals();
        pizzaAdapter.notifyDataSetChanged();
        showToast("Order placed successfully.");
    }

    private void handleCancelOrder() {
        currentOrder.getPizzas().clear();
        pizzaAdapter.notifyDataSetChanged();
        updateTotals();
        showToast("The order has been cleared.");
    }

    private void updateTotals() {
        double subtotal = currentOrder.getTotalPrice();
        double salesTax = subtotal * SALES_TAX_RATE;
        double total = subtotal + salesTax;

        subtotalField.setText("$" + df.format(subtotal));
        salesTaxField.setText("$" + df.format(salesTax));
        orderTotalField.setText("$" + df.format(total));
    }

    private void showToast(String message) {
        Toast.makeText(CurrentOrderActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
