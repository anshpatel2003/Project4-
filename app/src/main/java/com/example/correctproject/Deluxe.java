package com.example.correctproject;

import java.util.ArrayList;
/**
 *  * @author Ansh Patel, Jeet Soni
 * Deluxe class that extends Pizza
 */
public class Deluxe extends Pizza {
    public Deluxe(Crust crust) {
        super();
        this.setCrust(crust);
        this.setToppings(new ArrayList<>());
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
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
                return 16.99;
            case MEDIUM:
                return 18.99;
            case LARGE:
                return 20.99;
            default:
                return 0.0;
        }
    }

    /**
     * Returns a string representation of the Deluxe pizza object.
     * The string includes the type of crust, the toppings, the size,
     * and the price of the pizza formatted to two decimal places.
     *
     * @return a string
     */
    @Override
    public String toString() {
        return "Deluxe (" + getCrust() + "): " + getToppings() + ", Size: " + getSize() + ", Price: $" + String.format("%.2f", price());
    }
}
