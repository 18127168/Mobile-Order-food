package com.example.masterchef.ui.staff.serve;

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

public class ServeAdapter extends RecyclerView.Adapter<ServeAdapter.ViewHolder> {

    String[] titles;
    int[] images;
    int[] table_id;
    int[] quantity;
    LayoutInflater inflater;


    public ServeAdapter(Context context, int[] images, String[] titles, int[] table_id, int[] quantity){
        this.titles = titles;
        this.images = images;
        this.table_id = table_id;
        this.quantity = quantity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_serve, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.table_serve.setText("Table: " + String.valueOf(table_id[position]));
        holder.item_quantity.setText("Quantity: " + String.valueOf(quantity[position]));
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView title, item_quantity, table_serve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            item_quantity = (TextView) itemView.findViewById(R.id.quantity);
            table_serve = itemView.findViewById(R.id.table_serve);


        }
    }
}