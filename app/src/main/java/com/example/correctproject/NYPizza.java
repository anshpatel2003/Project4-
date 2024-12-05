package com.example.correctproject;
/**
 *   * @author Ansh Patel, Jeet Soni
 * A class that represents a New York pizza factory.
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a new instance of a Deluxe pizza with a Brooklyn crust.
     * @return a new Deluxe pizza
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN);
    }

    /**
     * Creates a new instance of a crust.
     * @return a new Meatazza pizza
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HAND_TOSSED);
    }

    /**
     * Creates a new instance of a BBQ Chicken pizza with a thin crust.
     * @return a new BBQ Chicken pizza
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN);
    }

    /**
     * Creates a new instance of a Build Your Own pizza with a hand tossed crust.
     * @return a new Build Your Own pizza
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}


