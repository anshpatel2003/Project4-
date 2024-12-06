/**
 * Author Jeet Soni, Ansh Patel
 * PizzaAdapter class
 */


package com.example.correctproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * A RecyclerView adapter for displaying a list of pizzas in a list.
 * This adapter binds a list of pizzas to a RecyclerView and allows the removal
 * of a pizza from the list when an item is clicked.
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private final List<Pizza> pizzaList;
    private final OnPizzaRemoveListener removeListener;

    /**
     * Interface for notifying when a pizza is removed from the list.
     */
    public interface OnPizzaRemoveListener {
        /**
         * Called when a pizza is removed from the list.
         *
         * @param pizza The pizza that was removed.
         */
        void onPizzaRemoved(Pizza pizza);
    }

    /**
     * Constructor to create a PizzaAdapter instance.
     *
     * @param pizzaList   The list of pizzas to display.
     * @param removeListener The listener to handle pizza removal.
     */
    public PizzaAdapter(List<Pizza> pizzaList, OnPizzaRemoveListener removeListener) {
        this.pizzaList = pizzaList;
        this.removeListener = removeListener;
    }

    /**
     * Called when a new ViewHolder is created. This method inflates the view for each pizza.
     *
     * @param parent   The parent view group that will contain the new item view.
     * @param viewType The view type of the new view.
     * @return A new PizzaViewHolder instance.
     */
    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new PizzaViewHolder(view);
    }

    /**
     * Called to bind data to the ViewHolder at a specific position in the list.
     * This method sets the pizza name and defines the click action to remove the pizza.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position in the list to bind the data from.
     */
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.pizzaName.setText(pizza.toString());
        holder.itemView.setOnClickListener(v -> removeListener.onPizzaRemoved(pizza));
    }

    /**
     * Returns the total number of items in the pizza list.
     *
     * @return The number of pizzas in the list.
     */
    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    /**
     * ViewHolder class to hold and bind the pizza name view.
     */
    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName;

        /**
         * Constructor for the ViewHolder, which initializes the pizza name text view.
         *
         * @param itemView The view for a single pizza item.
         */
        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(android.R.id.text1);
        }
    }
}
