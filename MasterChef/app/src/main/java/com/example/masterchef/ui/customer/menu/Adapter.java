package com.example.masterchef.ui.customer.menu;

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
    int[] prices;
    LayoutInflater inflater;

    public Adapter(Context context, int[] images, String[] titles, int[] prices){
        this.titles = titles;
        this.images = images;
        this.prices = prices;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.price.setText( "$" + Integer.toString(prices[position]));
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView title;
        TextView price;
        Button choose_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            choose_btn = itemView.findViewById(R.id.menu_choose_btn);
        }
    }
}