package com.example.masterchef.ui.manager.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    String[] titles;
    int[] images;
    int[] quantity;
    LayoutInflater inflater;

    public Adapter(Context context, int[] images, String[] titles, int[] quantity){
        this.titles = titles;
        this.images = images;
        this.quantity = quantity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_stock_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.quantity.setText("Số lượng: " + Integer.toString(quantity[position]));
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView title;
        TextView quantity;
        Button choose_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.stock_title);
            imgFood = itemView.findViewById(R.id.stock_image);
            quantity = itemView.findViewById(R.id.stock_quantity);
            choose_btn = itemView.findViewById(R.id.stock_choose_btn);
        }
    }
}