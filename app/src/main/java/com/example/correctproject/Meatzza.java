package com.example.correctproject;

import java.util.ArrayList;

/**
 * * @author Ansh Patel, Jeet Soni
 * Meatzza class that extends Pizza
 */
public class Meatzza extends Pizza {

    public Meatzza(Crust crust) {
        super();
        this.setCrust(crust);
        this.setToppings(new ArrayList<>()); // Initialize toppings list
        // Add predefined toppings for Meatzza
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.HAM);
        this.getToppings().addAll(toppings);
    }

    /**
     * Returns the price of the pizza based on the size
     * @return
     */
    @Override
    public double price() {
        switch (this.getSize()) {
            case SMALL:
                return 17.99;
            case MEDIUM:
                return 19.99;
            case LARGE:
                return 21.99;
            default:
                return 0.0;
        }
    }

    /**
     * Returns a string representation of the Meatzza pizza object.
     * The string includes the type of crust, the toppings, the size,
     * and the price of the pizza formatted to two decimal places.
     *
     * @return a string
     */
    @Override
    public String toString() {
        return "Meatzza (" + getCrust() + "): " + getToppings() + ", Size: " + getSize() + ", Price: $" + String.format("%.2f", price());
    }
}
