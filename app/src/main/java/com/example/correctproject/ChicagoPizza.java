package com.example.correctproject;

/**
        * @author Ansh Patel, Jeet Soni
 * ChicagoPizza class that implements PizzaFactory
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Create a new instance of a Deluxe pizza
     * @return a new Deluxe pizza
     */
    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe(Crust.DEEP_DISH);
        pizza.setCrust(Crust.DEEP_DISH);
        return pizza;
    }

    /**
     * Create a new instance of a Meatzza pizza
     * @return a new Meatzza pizza
     */
    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza(Crust.STUFFED);
        pizza.setCrust(Crust.STUFFED);
        return pizza;
    }

    /**
     * Create a new instance of a BBQChicken pizza
     * @return a new BBQChicken pizza
     */
    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken(Crust.PAN);
        pizza.setCrust(Crust.PAN);

        return pizza;
    }

    /**
     * Create a new instance of a buildYourown pizza
     * @return a new buildYourown pizza
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn(Crust.PAN);
        pizza.setCrust(Crust.PAN);
        return pizza;
    }
}

