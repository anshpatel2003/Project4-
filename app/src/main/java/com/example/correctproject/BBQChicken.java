package com.example.correctproject;

import java.util.ArrayList;
/**
 *  * @author Ansh Patel, Jeet Soni
 * BBQChicken class that extends Pizza
 */
public class BBQChicken extends Pizza {

    public BBQChicken(Crust crust) {
        super();
        this.setCrust(crust);
        this.setToppings(new ArrayList<>());
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.BBQ_CHICKEN);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.CHEDDAR);
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
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
            default:
                return 0.0;
        }
    }

    @Override
    public String toString() {
        return "BBQ Chicken (" + getCrust() + "): " + getToppings() + ", Size: " + getSize() + ", Price: $" + String.format("%.2f", price());
    }
}

