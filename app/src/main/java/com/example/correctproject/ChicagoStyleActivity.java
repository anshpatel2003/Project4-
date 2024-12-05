package com.example.correctproject;

import com.example.correctproject.R;

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
    private ListView availableToppingsListView, selectedToppingsListView;
    private Button addToppingButton, removeToppingButton, addToOrderButton;
    private ImageView pizzaImageView;

    private List<Topping> availableToppings, selectedToppings;
    private ArrayAdapter<Topping> availableAdapter, selectedAdapter;

    private Pizza currentPizza;
    private final PizzaFactory pizzaFactory = new ChicagoPizza();
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicago_style);

        initializeViews();
        initializeData();
        setupListeners();
    }

    private void initializeViews() {
        pizzaTypeSpinner = findViewById(R.id.spinnerPizzaType);
        sizeRadioGroup = findViewById(R.id.rgSize);
        smallRadioButton = findViewById(R.id.rbSmall);
        mediumRadioButton = findViewById(R.id.rbMedium);
        largeRadioButton = findViewById(R.id.rbLarge);
        crustTextView = findViewById(R.id.crustTextView);
        priceTextView = findViewById(R.id.priceTextView);
//        availableToppingsListView = findViewById(R.id.lvAvailableToppings);
//        selectedToppingsListView = findViewById(R.id.lvSelectedToppings);
        addToppingButton = findViewById(R.id.btnAddTopping);
        removeToppingButton = findViewById(R.id.btnRemoveTopping);
        addToOrderButton = findViewById(R.id.btnAddToOrder);
        pizzaImageView = findViewById(R.id.pizzaImageView);

        priceTextView.setFocusable(false);
        crustTextView.setFocusable(false);
    }

    private void initializeData() {
        // Populate Spinner with pizza types
        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));
        pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

        // Populate topping lists
        availableToppings = new ArrayList<>(Arrays.asList(Topping.values()));
        selectedToppings = new ArrayList<>();
        availableAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availableToppings);
        selectedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppings);

        availableToppingsListView.setAdapter(availableAdapter);
        selectedToppingsListView.setAdapter(selectedAdapter);
    }

    private void setupListeners() {
        // Listener for Pizza Type Spinner
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePizzaSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener for Size RadioGroup
        sizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updatePizzaSize());

        // Add Topping Button Listener
        addToppingButton.setOnClickListener(v -> handleAddTopping());

        // Remove Topping Button Listener
        removeToppingButton.setOnClickListener(v -> handleRemoveTopping());

        // Add to Order Button Listener
        addToOrderButton.setOnClickListener(v -> handleAddToOrder());
    }

    private void updatePizzaSelection() {
        String selectedType = (String) pizzaTypeSpinner.getSelectedItem();
        if (selectedType == null) return;

        selectedToppings.clear();
        availableToppings.clear();
        availableToppings.addAll(Arrays.asList(Topping.values()));

        switch (selectedType) {
            case "Deluxe":
                currentPizza = pizzaFactory.createDeluxe();
                crustTextView.setText(currentPizza.getCrust().toString());
                selectedToppings.addAll(currentPizza.getToppings());
                availableToppings.removeAll(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.deulxe);
                break;

            case "BBQ Chicken":
                currentPizza = pizzaFactory.createBBQChicken();
                crustTextView.setText(currentPizza.getCrust().toString());
                selectedToppings.addAll(currentPizza.getToppings());
                availableToppings.removeAll(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.bbqchicken);
                break;

            case "Meatzza":
                currentPizza = pizzaFactory.createMeatzza();
                crustTextView.setText(currentPizza.getCrust().toString());
                selectedToppings.addAll(currentPizza.getToppings());
                availableToppings.removeAll(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.meatazza);
                break;

            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn();
                crustTextView.setText(currentPizza.getCrust().toString());
                pizzaImageView.setImageResource(R.drawable.buildyourown);
                break;
        }

        availableAdapter.notifyDataSetChanged();
        selectedAdapter.notifyDataSetChanged();
        updatePizzaSize();
        updatePrice();
    }

    private void handleAddTopping() {
        Topping selected = (Topping) availableToppingsListView.getSelectedItem();
        if (selected != null && selectedToppings.size() < 7) {
            selectedToppings.add(selected);
            availableToppings.remove(selected);
            updateAdapters();
            updatePrice();
        }
    }

    private void handleRemoveTopping() {
        Topping selected = (Topping) selectedToppingsListView.getSelectedItem();
        if (selected != null) {
            selectedToppings.remove(selected);
            availableToppings.add(selected);
            updateAdapters();
            updatePrice();
        }
    }

    private void updatePizzaSize() {
        if (currentPizza == null) return;

        if (smallRadioButton.isChecked()) {
            currentPizza.setSize(Size.SMALL);
        } else if (mediumRadioButton.isChecked()) {
            currentPizza.setSize(Size.MEDIUM);
        } else if (largeRadioButton.isChecked()) {
            currentPizza.setSize(Size.LARGE);
        }

        updatePrice();
    }

    private void updatePrice() {
        if (currentPizza instanceof BuildYourOwn) {
            BuildYourOwn byoPizza = (BuildYourOwn) currentPizza;
            byoPizza.getToppings().clear();
            byoPizza.getToppings().addAll(selectedToppings);
        }

        double price = currentPizza.price();
        priceTextView.setText(String.format("$%.2f", price));
    }

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

        if (currentOrder == null) {
            currentOrder = new Order();
        }

        currentOrder.addPizza(currentPizza);
        showAlert("Success", "Pizza successfully added to the order!");
    }

    private void updateAdapters() {
        availableAdapter.notifyDataSetChanged();
        selectedAdapter.notifyDataSetChanged();
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
