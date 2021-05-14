package com.example.masterchef.ui.staff.tables;

import android.content.Context;
import android.os.Bundle;
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
import com.example.masterchef.Tables;

import java.util.ArrayList;
import java.util.List;

public class TablesAdapter extends RecyclerView.Adapter<com.example.masterchef.ui.staff.tables.TablesAdapter.ViewHolder> {

    List<Tables> tables = new ArrayList<>();
    View root;
    LayoutInflater inflater;

    public TablesAdapter(Context context, View v, List<Tables> tables){
        this.tables = tables;
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
        holder.id.setText("TABLE " + tables.get(position).getId());
        holder.seat.setText("Seats: " + (tables.get(position).getSeats()));



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                AppCompatActivity staff_activity = (AppCompatActivity) root.getContext();
                Bundle bundle = new Bundle();
                bundle.putInt("table_id", tables.get(position).getId());
                OptionFragment optionFragment = new OptionFragment();
                optionFragment.setArguments(bundle);
                staff_activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_staff, optionFragment).addToBackStack(TablesFragment.class.getName()).commit();
            }
        });
    }

    @Override
    public int getItemCount(){
        return tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView id, seat;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.menu_title);
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

