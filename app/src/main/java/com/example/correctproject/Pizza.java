package com.example.correctproject;

import java.util.ArrayList;

/**
 * Abstract class representing a Pizza.
 * This class serves as a base for different types of pizzas and
 * provides common properties and methods for managing pizza attributes
 * such as toppings, crust, and size.
 *
 * <p>Subclasses should implement the {@link #price()} method to
 * provide the specific pricing logic for different pizza types.</p>
 *
 * @author Ansh Patel, Jeet Soni
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * Calculates the price of the pizza.
     *
     * @return the price of the pizza as a double.
     */
    public abstract double price();

    /**
     * Gets the list of toppings for this pizza.
     *
     * @return an ArrayList of Topping objects representing the toppings.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Gets the crust type of this pizza.
     *
     * @return the Crust object representing the type of crust.
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Gets the size of this pizza.
     *
     * @return the Size object representing the size of the pizza.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of this pizza.
     *
     * @param size the Size object to set as the new size of the pizza.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Sets the crust type of this pizza.
     *
     * @param crust the Crust object to set as the new crust type of the pizza.
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Sets the toppings for this pizza.
     *
     * @param toppings an ArrayList of Topping objects to set as the toppings.
     */
    protected void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }
}