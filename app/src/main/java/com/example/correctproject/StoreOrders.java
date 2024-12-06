package com.example.correctproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ansh Patel, Jeet Soni
 * StoreOrders class to store orders.
 */
public class StoreOrders {

    private static StoreOrders instance;
    private List<Order> orders;
    private Map<Order, Boolean> placedStatusMap;
    private int currentOrderNumber;

    private StoreOrders() {
        orders = new ArrayList<>();
        placedStatusMap = new HashMap<>();
        currentOrderNumber = 1; // Initialize with 1
    }

    /**
     * Get the instance of the StoreOrders class.
     * @return the instance of the StoreOrders class
     */
    public static StoreOrders getInstance() {
        if (instance == null) {
            instance = new StoreOrders();
        }
        return instance;
    }

    /**
     * Get the list of orders.
     * @return the list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Add an order to the list of orders with a default placed status of false.
     * @param order the order to add
     */
    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            order.setNumber(currentOrderNumber);
            orders.add(order);
            placedStatusMap.put(order, false);
        }
    }

    /**
     * Update the placed status of an order.
     * @param order the order to update
     * @param isPlaced the new placed status
     * @return true if the order status was updated, false otherwise
     */
    public boolean updateOrderStatus(Order order, boolean isPlaced) {
        if (placedStatusMap.containsKey(order)) {
            placedStatusMap.put(order, isPlaced);
            if (isPlaced) {
                currentOrderNumber++; // Increment order number when order is placed
            }
            return true;
        }
        return false; // Order not found
    }

    /**
     * Check if an order is placed.
     * @param order the order to check
     * @return the placed status of the order, or null if the order is not found
     */
    public Boolean isOrderPlaced(Order order) {
        return placedStatusMap.get(order);
    }

    /**
     * Remove an order from the list.
     * @param order the order to remove
     * @return true if the order was removed, false otherwise
     */
    public boolean removeOrder(Order order) {
        if (orders.remove(order)) {
            placedStatusMap.remove(order);
            return true;
        }
        return false;
    }


}