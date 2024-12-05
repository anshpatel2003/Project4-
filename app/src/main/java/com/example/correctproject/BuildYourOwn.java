package com.example.correctproject;

import java.util.ArrayList;
/**
 *  * @author Ansh Patel, Jeet Soni
 * BuildYourOwn class that extends Pizza
 */
public class BuildYourOwn extends Pizza {

    private static final double BASE_PRICE_SMALL = 8.99;
    private static final double BASE_PRICE_MEDIUM = 10.99;
    private static final double BASE_PRICE_LARGE = 12.99;
    private static final double TOPPING_PRICE = 1.69;


    public BuildYourOwn(Crust crust) {
        super();
        this.setCrust(crust);
        ArrayList<Topping> toppings = new ArrayList<>();
        this.setToppings(toppings);

    }

    /**
     * Returns the price of the pizza based on the size
     *
     * @return
     */
    @Override
    public double price() {
        double basePrice;
        switch (this.getSize()) {
            case SMALL:
                basePrice = BASE_PRICE_SMALL;
                break;
            case MEDIUM:
                basePrice = BASE_PRICE_MEDIUM;
                break;
            case LARGE:
                basePrice = BASE_PRICE_LARGE;
                break;
            default:
                return 0.0;
        }
        return basePrice + (this.getToppings().size() * TOPPING_PRICE);
    }


    /**
     * Returns a string representation of the Build Your Own pizza.
     * The string includes the pizza type (Build Your Own), crust type,
     * list of toppings, size, and calculated price formatted to 2 decimal places.
     *
     * @return A formatted string containing the pizza details
     */
    @Override
    public String toString() {
        return "Build Your Own (" + getCrust() + "): " + getToppings() + ", Size: " + getSize() + ", Price: $" + String.format("%.2f", price());
    }
}
