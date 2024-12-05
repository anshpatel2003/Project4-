package com.example.correctproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.text.DecimalFormat;

public class CurrentOrderActivity extends AppCompatActivity {

    private ListView pizzaListView;
    private EditText subtotalField;
    private EditText salesTaxField;
    private EditText orderTotalField;

    private StoreOrders storeOrders;
    private Order currentOrder;
    private static final double SALES_TAX_RATE = 0.07;
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        // Initialize the views
        EditText orderNumberField = findViewById(R.id.orderNumberField);
        pizzaListView = findViewById(R.id.pizzaListView);
        subtotalField = findViewById(R.id.subtotalField);
        salesTaxField = findViewById(R.id.salesTaxField);
        orderTotalField = findViewById(R.id.orderTotalField);
        Button removePizzaButton = findViewById(R.id.removePizzaButton);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button clearOrderButton = findViewById(R.id.clearOrderButton);

        // Initialize StoreOrders and get the current order
        storeOrders = StoreOrders.getInstance();

        if (storeOrders.getOrders().isEmpty()) {
            return;
        }

        currentOrder = storeOrders.getOrders().get(storeOrders.getOrders().size() - 1);
        if (currentOrder == null) {
            return;
        }

        // Set initial values
        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));
        updatePizzaListView();

        // Set up event listeners
        removePizzaButton.setOnClickListener(view -> handleRemovePizza());
        placeOrderButton.setOnClickListener(view -> handlePlaceOrder());
        clearOrderButton.setOnClickListener(view -> handleCancelOrder());

        // Set up ListView click listener for pizza removal
        pizzaListView.setOnItemClickListener((adapterView, view, position, id) -> {
            // Handle pizza selection if necessary
        });
    }

    private void handleRemovePizza() {
        int selectedIndex = pizzaListView.getCheckedItemPosition();
        if (selectedIndex != ListView.INVALID_POSITION) {
            Pizza removedPizza = currentOrder.getPizzas().get(selectedIndex);
            currentOrder.removePizza(removedPizza);
            updatePizzaListView();
            showToast("Pizza successfully removed from the order!");
        } else {
            showToast("No pizza selected to remove.");
        }
    }

    private void handlePlaceOrder() {
        storeOrders.updateOrderStatus(currentOrder, true);
        updatePizzaListView();
        Order newOrder = new Order();
        storeOrders.addOrder(newOrder);
        pizzaListView.setAdapter(null);
        showToast("Order placed successfully.");
    }

    private void handleCancelOrder() {
        currentOrder.getPizzas().clear();
        pizzaListView.setAdapter(null);
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

    private void updatePizzaListView() {
        ArrayAdapter<String> pizzaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Pizza pizza : currentOrder.getPizzas()) {
            pizzaAdapter.add(pizza.toString());
        }
        pizzaListView.setAdapter(pizzaAdapter);
        updateTotals();
    }
}
