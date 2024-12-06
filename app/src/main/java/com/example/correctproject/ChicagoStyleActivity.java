/**
 * author Jeet Soni, Ansh Patel
 * Chicago Style Activity class
 */

package com.example.correctproject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChicagoStyleActivity extends AppCompatActivity {

    private Spinner pizzaTypeSpinner;
    private RadioGroup sizeRadioGroup;
    private RadioButton smallRadioButton, mediumRadioButton, largeRadioButton;
    private TextView crustTextView, priceTextView;
    private LinearLayout toppingsContainer;
    private Button addToOrderButton;
    private ImageView pizzaImageView;

    private List<Topping> availableToppings, selectedToppings;
    private Pizza currentPizza;
    private final PizzaFactory pizzaFactory = new ChicagoPizza();
    private Order order;

    /**
     * Initializes the activity by setting up the UI views, loading data (e.g., pizza types, toppings),
     * and configuring listeners for user interactions.
     *
     * @param savedInstanceState If the activity is being re-initialized, this contains
     *                           the most recent data, or null if there's no data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicago_style);

        // Initialize views
        initializeViews();

        // Initialize data (e.g., toppings, pizza types)
        initializeData();

        // Set up listeners for user interactions
        setupListeners();
    }

    /**
     * Initializes the views by linking them with the layout elements.
     */
    private void initializeViews() {
        pizzaTypeSpinner = findViewById(R.id.spinnerPizzaType);
        sizeRadioGroup = findViewById(R.id.rgSize);
        smallRadioButton = findViewById(R.id.rbSmall);
        mediumRadioButton = findViewById(R.id.rbMedium);
        largeRadioButton = findViewById(R.id.rbLarge);
        crustTextView = findViewById(R.id.crustTextView);
        priceTextView = findViewById(R.id.priceTextView);
        toppingsContainer = findViewById(R.id.toppingsContainer);
        addToOrderButton = findViewById(R.id.btnAddToOrder);
        pizzaImageView = findViewById(R.id.pizzaImageView);

        // Disable text view focus
        priceTextView.setFocusable(false);
        crustTextView.setFocusable(false);
    }

    /**
     * Initializes the data such as available pizza types and toppings.
     */
    private void initializeData() {
        List<String> pizzaTypes = new ArrayList<>(Arrays.asList("Select a Pizza Type", "Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));

        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                pizzaTypes);
        pizzaTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

        // Initialize toppings list
        availableToppings = new ArrayList<>(Arrays.asList(Topping.values()));
        selectedToppings = new ArrayList<>();

        // Create checkboxes for each topping
        for (Topping topping : availableToppings) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(topping.name());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedToppings.add(topping);
                } else {
                    selectedToppings.remove(topping);
                }
                if (currentPizza instanceof BuildYourOwn) {
                    updatePrice();
                }
            });
            toppingsContainer.addView(checkBox);
        }
    }

    /**
     * Sets up the listeners for the UI elements.
     */
    private void setupListeners() {
        // Listener for pizza type selection
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePizzaSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener for pizza size selection
        sizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updatePizzaSize());

        // Listener for adding pizza to the order
        addToOrderButton.setOnClickListener(v -> handleAddToOrder());
    }

    /**
     * Updates the pizza selection based on the selected pizza type.
     */
    private void updatePizzaSelection() {
        String selectedType = (String) pizzaTypeSpinner.getSelectedItem();
        if (selectedType == null || selectedType.equals("Select a Pizza Type")) {
            currentPizza = null;
            crustTextView.setText("");
            pizzaImageView.setImageResource(android.R.color.transparent);
            selectedToppings.clear();
            setToppingsEditable(false);
            for (int i = 0; i < toppingsContainer.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) toppingsContainer.getChildAt(i);
                checkBox.setChecked(false);
            }
            priceTextView.setText("$0.00");
            return;
        }

        selectedToppings.clear();
        for (int i = 0; i < toppingsContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) toppingsContainer.getChildAt(i);
            checkBox.setChecked(false);
        }

        // Create the corresponding pizza based on selection
        switch (selectedType) {
            case "Deluxe":
                currentPizza = pizzaFactory.createDeluxe();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.deulxe);
                setToppingsEditable(false);
                break;

            case "BBQ Chicken":
                currentPizza = pizzaFactory.createBBQChicken();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.bbqchicken);
                setToppingsEditable(false);
                break;

            case "Meatzza":
                currentPizza = pizzaFactory.createMeatzza();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.meatazza);
                setToppingsEditable(false);
                break;

            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn();
                crustTextView.setText(currentPizza.getCrust().toString());
                pizzaImageView.setImageResource(R.drawable.buildyourown);
                setToppingsEditable(true);
                break;
        }

        updatePizzaSize();
        updatePrice();
    }

    /**
     * Updates the list of toppings based on the pizza's toppings.
     *
     * @param toppings The toppings to be applied to the pizza.
     */
    private void updatePizzaToppings(List<Topping> toppings) {
        for (int i = 0; i < toppingsContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) toppingsContainer.getChildAt(i);
            Topping topping = Topping.valueOf(checkBox.getText().toString());
            if (toppings.contains(topping)) {
                checkBox.setChecked(true);
                selectedToppings.add(topping);
            } else {
                checkBox.setChecked(false);
            }
        }
    }

    /**
     * Updates the selected pizza size based on user choice.
     */
    private void updatePizzaSize() {
        if (currentPizza == null) return;

        if (sizeRadioGroup.getCheckedRadioButtonId() == -1) {
            currentPizza.setSize(Size.SMALL);
            smallRadioButton.setSelected(true);
        }
        if (smallRadioButton.isChecked()) {
            currentPizza.setSize(Size.SMALL);
        } else if (mediumRadioButton.isChecked()) {
            currentPizza.setSize(Size.MEDIUM);
        } else if (largeRadioButton.isChecked()) {
            currentPizza.setSize(Size.LARGE);
        }

        updatePrice();
    }

    /**
     * Updates the price of the pizza based on selected size and toppings.
     */
    private void updatePrice() {
        if (currentPizza instanceof BuildYourOwn) {
            BuildYourOwn byoPizza = (BuildYourOwn) currentPizza;
            byoPizza.getToppings().clear();
            byoPizza.getToppings().addAll(selectedToppings);
        }

        if (sizeRadioGroup.getCheckedRadioButtonId() == -1) {
            currentPizza.setSize(Size.SMALL);
        }

        double price = currentPizza.price();
        priceTextView.setText(String.format("$%.2f", price));
    }

    /**
     * Handles adding the pizza to the order, with validation for toppings and size.
     */
    private void handleAddToOrder() {
        if (currentPizza instanceof BuildYourOwn && (selectedToppings.size() < 3 || selectedToppings.size() > 7)) {
            String message = selectedToppings.size() < 3
                    ? "Need at least 3 toppings!"
                    : "Cannot add more than 7 toppings!";
            showAlert("Warning", message);
            return;
        }

        if (sizeRadioGroup.getCheckedRadioButtonId() == -1) {
            showAlert("Warning", "Please select a size!");
            return;
        }

        // Add pizza to the order
        Order order = StoreOrders.getInstance().getOrders().isEmpty() ? null : StoreOrders.getInstance().getOrders().get(StoreOrders.getInstance().getOrders().size() - 1);

        if (StoreOrders.getInstance().getOrders().isEmpty() || StoreOrders.getInstance().isOrderPlaced(order) == Boolean.TRUE) {
            order = new Order();
            StoreOrders.getInstance().addOrder(order);
        }

        order = StoreOrders.getInstance().getOrders().get(StoreOrders.getInstance().getOrders().size() - 1);
        order.addPizza(currentPizza);
        showAlert("Success", "Pizza successfully added to the order!");

        // Reset the UI
        pizzaTypeSpinner.setSelection(0);
        sizeRadioGroup.clearCheck();
        priceTextView.setText("$0.00");
        crustTextView.setText("");
    }

    /**
     * Displays an alert dialog with the provided title and message.
     *
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Enables or disables the checkboxes for topping selection.
     *
     * @param editable Whether the toppings are editable (selectable).
     */
    private void setToppingsEditable(boolean editable) {
        for (int i = 0; i < toppingsContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) toppingsContainer.getChildAt(i);
            checkBox.setEnabled(editable);
        }
    }
}
