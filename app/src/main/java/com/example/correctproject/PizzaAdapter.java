package com.example.correctproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private final List<Pizza> pizzaList;
    private final OnPizzaRemoveListener removeListener;

    public interface OnPizzaRemoveListener {
        void onPizzaRemoved(Pizza pizza);
    }

    public PizzaAdapter(List<Pizza> pizzaList, OnPizzaRemoveListener removeListener) {
        this.pizzaList = pizzaList;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.pizzaName.setText(pizza.toString());
        holder.itemView.setOnClickListener(v -> removeListener.onPizzaRemoved(pizza));
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(android.R.id.text1);
        }
    }
}
