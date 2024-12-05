package com.example.correctproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrdersPlacedActivity extends AppCompatActivity {

    // UI elements
    private Spinner orderNumberSpinner;
    private EditText orderTotalField;
    private ListView ordersListView;
    private Button cancelOrderButton;
    private Button exportOrdersButton;

    // StoreOrders singleton reference
    private StoreOrders storeOrders = StoreOrders.getInstance();

    // Decimal formatter for currency
    private DecimalFormat df = new DecimalFormat("0.00");

    // Constant for sales tax
    private static final double SALES_TAX_RATE = 0.07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        // Initialize UI elements
        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        orderTotalField = findViewById(R.id.orderTotalField);
        ordersListView = findViewById(R.id.ordersListView);
        cancelOrderButton = findViewById(R.id.cancelOrdersButton);

        // Populate order numbers spinner
        populateOrderNumbers();

        // Set up spinner listener
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                displaySelectedOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Set up button listeners
        cancelOrderButton.setOnClickListener(v -> handleCancelOrder());
    }

    /**
     * Populates the spinner with order numbers from placed orders.
     */
    private void populateOrderNumbers() {
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        for (Order order : storeOrders.getOrders()) {
            if (!order.getPizzas().isEmpty() && storeOrders.isOrderPlaced(order)) {
                orderNumbers.add(order.getNumber());
            }
        }

        // Set up adapter for the spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(adapter);

        // Select first order if available
        if (!orderNumbers.isEmpty()) {
            orderNumberSpinner.setSelection(0);
            displaySelectedOrder();
        }
    }

    /**
     * Displays the details of the selected order in the ListView.
     * Updates the order total field with the price including tax.
     */
    private void displaySelectedOrder() {
        Integer selectedOrderNumber = (Integer) orderNumberSpinner.getSelectedItem();

        if (selectedOrderNumber != null) {
            ordersListView.setAdapter(null);
            for (Order order : storeOrders.getOrders()) {
                if (order.getNumber() == selectedOrderNumber &&
                        !order.getPizzas().isEmpty() &&
                        storeOrders.isOrderPlaced(order)) {

                    // Create the order details string
                    ArrayList<String> orderDetails = new ArrayList<>();
                    orderDetails.add(formatOrderForList(order));

                    // Set the ListView adapter
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderDetails);
                    ordersListView.setAdapter(listAdapter);

                    // Set the order total field
                    double total = order.getTotalPrice() * (1 + SALES_TAX_RATE);
                    orderTotalField.setText(df.format(total));
                    break;
                }
            }
        }
    }

    /**
     * Formats an order's details into a string representation for display.
     * Includes order number and details of each pizza in the order.
     *
     * @param order The order to format
     * @return A formatted string containing the order details
     */
    private String formatOrderForList(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("Order #").append(order.getNumber()).append(":\n");

        for (Pizza pizza : order.getPizzas()) {
            details.append("- ").append(pizza.toString()).append("\n");
        }

        return details.toString();
    }

    /**
     * Handles the cancel order button action.
     * Removes the selected order from the store orders and updates the display.
     */
    private void handleCancelOrder() {
        Integer selectedOrderNumber = (Integer) orderNumberSpinner.getSelectedItem();
        if (selectedOrderNumber != null) {
            Order selectedOrder = storeOrders.getOrders().stream()
                    .filter(order -> order.getNumber() == selectedOrderNumber)
                    .findFirst()
                    .orElse(null);

            if (selectedOrder != null) {
                storeOrders.removeOrder(selectedOrder);
                populateOrderNumbers();
                clearFields();
                showToast("Order cancelled successfully!");
            }
        } else {
            showToast("Please select an order to cancel.");
        }
    }


    /**
     * Clears all fields in the view, including the spinner selection, order total field, and orders list view.
     */
    private void clearFields() {
        orderNumberSpinner.setSelection(0);
        orderTotalField.setText("");
        ordersListView.setAdapter(null);
    }

    /**
     * Displays a Toast message.
     *
     * @param message The content message to display in the Toast
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
