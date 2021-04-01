package com.example.masterchef.ui.customer.jointable;

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

public class JoinTableAdapter extends RecyclerView.Adapter<JoinTableAdapter.ViewHolder> {

    int[] images;
    String[] titles;
    int[] seats;
    LayoutInflater inflater;

    public JoinTableAdapter(Context context, int[] images, String[] titles, int[] seats){
        this.titles = titles;
        this.images = images;
        this.seats = seats;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_table_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.seat.setText( "Số chỗ:" + Integer.toString(seats[position]));
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView title, seat;
        Button choose_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jointable_title);
            imgFood = itemView.findViewById(R.id.jointable_image);
            seat = itemView.findViewById(R.id.jointable_seat);
            choose_btn = itemView.findViewById(R.id.jointable_choose_btn);
        }
    }
}