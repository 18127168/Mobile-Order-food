package com.example.masterchef.ui.staff.tables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.ItemClickListener;
import com.example.masterchef.R;

public class TablesAdapter extends RecyclerView.Adapter<com.example.masterchef.ui.staff.tables.TablesAdapter.ViewHolder> {

    String[] titles;
    int[] images;
    int[] seats;
    View root;
    LayoutInflater inflater;



    public TablesAdapter(Context context, View v, int[] images, String[] titles, int[] seats){
        this.titles = titles;
        this.images = images;
        this.seats = seats;
        this.root = v;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_tables, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.seat.setText("Seats: " + Integer.toString(seats[position]));



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                AppCompatActivity staff_activity = (AppCompatActivity) root.getContext();
                OptionFragment optionFragment = new OptionFragment();
                staff_activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_staff, optionFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView imgFood;
        TextView title, seat;

        private ItemClickListener itemClickListener;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            seat = itemView.findViewById(R.id.menu_seat);



            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

}

