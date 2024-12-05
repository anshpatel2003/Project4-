package com.example.correctproject;
import java.util.ArrayList;

/**
 *  * @author Ansh Patel, Jeet Soni
 * Represents an order of pizzas.
 */
public class Order {
    private static int orderCounter = 1;
    private int number;
    private ArrayList<Pizza> pizzas;

    public Order() {
        this.number = orderCounter++;
        this.pizzas = new ArrayList<>();
    }

    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * Creates a new instance of an order with a list of pizzas.
     * @param pizza the list of pizzas in the order
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Creates a new instance of an order with a list of pizzas.
     * @param pizza the list of pizzas in the order
     */
    // Removes a pizza from the order
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Creates a new instance of an order with a list of pizzas.
     *  the list of pizzas in the order
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Creates a new instance of an order with a list of pizzas.
     * @return the order number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Creates a new instance of an order with a list of pizzas.
     * @return the total price of the order
     */
    public double getTotalPrice() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }


    /**
     * Creates a new instance of an order with a list of pizzas.
     * @return the total price of the order
     */
    // toString method for displaying order details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(number).append(":\n");
        for (Pizza pizza : pizzas) {
            sb.append(pizza.toString()).append("\n");
        }
        sb.append("Total Price: $").append(String.format("%.2f", getTotalPrice()));
        return sb.toString();
    }
}