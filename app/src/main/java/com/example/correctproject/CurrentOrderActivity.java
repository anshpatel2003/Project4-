/**
 * author Jeet Soni, Ansh Patel
 * CurrentOrderActivity class
 */

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

    /**
     * Initializes the activity by setting up UI elements, retrieving the current order,
     * displaying the pizzas in a RecyclerView, and updating order totals. Also, sets up
     * listeners for removing pizzas, placing the order, and clearing the order.
     *
     * @param savedInstanceState If the activity is being re-initialized, this contains
     *                           the most recent data, or null if there's no data.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        // Initialize UI elements
        EditText orderNumberField = findViewById(R.id.orderNumberField);
        pizzaRecyclerView = findViewById(R.id.pizzaRecyclerView);
        subtotalField = findViewById(R.id.subtotalField);
        salesTaxField = findViewById(R.id.salesTaxField);
        orderTotalField = findViewById(R.id.orderTotalField);
        Button removePizzaButton = findViewById(R.id.removePizzaButton);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button clearOrderButton = findViewById(R.id.clearOrderButton);

        storeOrders = StoreOrders.getInstance();

        // Get the current order from the StoreOrders singleton
        if (storeOrders.getOrders().isEmpty()) {
            return;
        }

        currentOrder = storeOrders.getOrders().get(storeOrders.getOrders().size() - 1);
        if (currentOrder == null) {
            return;
        }

        // Set order number in the UI
        orderNumberField.setText(String.valueOf(currentOrder.getNumber()));

        // Set up the pizza list adapter and RecyclerView
        pizzaAdapter = new PizzaAdapter(currentOrder.getPizzas(), this::onPizzaRemoved);
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaRecyclerView.setAdapter(pizzaAdapter);

        // Update the totals
        updateTotals();

        // Set up button click listeners
        removePizzaButton.setOnClickListener(view -> handleRemovePizza());
        placeOrderButton.setOnClickListener(view -> handlePlaceOrder());
        clearOrderButton.setOnClickListener(view -> handleCancelOrder());
    }

    /**
     * Called when a pizza is removed from the current order.
     *
     * @param pizza The pizza to be removed from the order.
     */
    private void onPizzaRemoved(Pizza pizza) {
        currentOrder.removePizza(pizza);
        pizzaAdapter.notifyDataSetChanged();
        updateTotals();
        showToast("Pizza successfully removed from the order!");
    }

    /**
     * Handles the removal of a pizza from the current order.
     * Displays a message prompting the user to select a pizza.
     */
    private void handleRemovePizza() {
        showToast("Select a pizza to remove from the list.");
    }

    /**
     * Handles the action of placing the order.
     * Updates the order status and notifies the user of the successful placement.
     */
    private void handlePlaceOrder() {
        storeOrders.updateOrderStatus(currentOrder, true);
        updateTotals();
        pizzaAdapter.notifyDataSetChanged();
        showToast("Order placed successfully.");
    }

    /**
     * Handles the action of clearing the current order.
     * Removes all pizzas from the order and updates the totals.
     */
    private void handleCancelOrder() {
        currentOrder.getPizzas().clear();
        pizzaAdapter.notifyDataSetChanged();
        updateTotals();
        showToast("The order has been cleared.");
    }

    /**
     * Updates the subtotal, sales tax, and total fields in the UI based on the current order.
     */
    private void updateTotals() {
        double subtotal = currentOrder.getTotalPrice();
        double salesTax = subtotal * SALES_TAX_RATE;
        double total = subtotal + salesTax;

        subtotalField.setText("$" + df.format(subtotal));
        salesTaxField.setText("$" + df.format(salesTax));
        orderTotalField.setText("$" + df.format(total));
    }

    /**
     * Displays a toast message to the user.
     *
     * @param message The message to be displayed in the toast.
     */
    private void showToast(String message) {
        Toast.makeText(CurrentOrderActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
