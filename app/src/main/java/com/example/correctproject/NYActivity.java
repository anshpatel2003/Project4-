package com.example.correctproject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NYActivity extends AppCompatActivity {

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
        toppingsContainer = findViewById(R.id.toppingsContainer);
        addToOrderButton = findViewById(R.id.btnAddToOrder);
        pizzaImageView = findViewById(R.id.pizzaImageView);

        priceTextView.setFocusable(false);
        crustTextView.setFocusable(false);

    }


    private void initializeData() {
        // Add a placeholder as the first item
        List<String> pizzaTypes = new ArrayList<>(Arrays.asList("Select a Pizza Type", "Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));

        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                pizzaTypes);
        pizzaTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

        // Dynamically add CheckBox for each topping
        availableToppings = new ArrayList<>(Arrays.asList(Topping.values()));
        selectedToppings = new ArrayList<>();

        for (Topping topping : availableToppings) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(topping.name());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedToppings.add(topping);
                } else {
                    selectedToppings.remove(topping);
                }
                updatePrice();
            });
            toppingsContainer.addView(checkBox);
        }
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

        // Add to Order Button Listener
        addToOrderButton.setOnClickListener(v -> handleAddToOrder());
    }

    private void updatePizzaSelection() {
        String selectedType = (String) pizzaTypeSpinner.getSelectedItem();
        if (selectedType == null || selectedType.equals("Select a Pizza Type")) {
            // Reset pizza details if placeholder is selected
            currentPizza = null;
            crustTextView.setText("");
            pizzaImageView.setImageResource(android.R.color.transparent); // Clear the image
            selectedToppings.clear();
            setToppingsEditable(false); // Disable toppings by default
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
            checkBox.setChecked(false); // Clear all selections
        }

        switch (selectedType) {
            case "Deluxe":
                currentPizza = pizzaFactory.createDeluxe();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.deulxe);
                setToppingsEditable(false); // Disable toppings
                break;

            case "BBQ Chicken":
                currentPizza = pizzaFactory.createBBQChicken();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.bbqchicken);
                setToppingsEditable(false); // Disable toppings
                break;

            case "Meatzza":
                currentPizza = pizzaFactory.createMeatzza();
                crustTextView.setText(currentPizza.getCrust().toString());
                updatePizzaToppings(currentPizza.getToppings());
                pizzaImageView.setImageResource(R.drawable.meatazza);
                setToppingsEditable(false); // Disable toppings
                break;

            case "Build Your Own":
                currentPizza = pizzaFactory.createBuildYourOwn();
                crustTextView.setText(currentPizza.getCrust().toString());
                pizzaImageView.setImageResource(R.drawable.buildyourown);
                setToppingsEditable(true); // Enable toppings
                break;
        }

        updatePizzaSize();
        updatePrice();
    }


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


    private void updatePizzaSize() {
        if (currentPizza == null) return;

        if(sizeRadioGroup.getCheckedRadioButtonId() == -1) {
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

    private void updatePrice() {
        if (currentPizza instanceof BuildYourOwn) {
            BuildYourOwn byoPizza = (BuildYourOwn) currentPizza;
            byoPizza.getToppings().clear();
            byoPizza.getToppings().addAll(selectedToppings);
        }
        if(sizeRadioGroup.getCheckedRadioButtonId() == -1) {
            currentPizza.setSize(Size.SMALL);
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

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void setToppingsEditable(boolean editable) {
        for (int i = 0; i < toppingsContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) toppingsContainer.getChildAt(i);
            checkBox.setEnabled(editable);
        }
    }

}
