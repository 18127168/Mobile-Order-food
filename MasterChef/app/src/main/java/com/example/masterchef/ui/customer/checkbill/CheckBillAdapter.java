package com.example.masterchef.ui.customer.checkbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.Food;
import com.example.masterchef.R;

import java.util.ArrayList;
import java.util.List;

public class CheckBillAdapter extends RecyclerView.Adapter<CheckBillAdapter.ViewHolder> {
    List<Food> listFoods;
    List<Integer> listNumberFoods;
    LayoutInflater inflater;

    public CheckBillAdapter(Context context, List<Food> listFoods, List<Integer> listNumberFoods){
        this.listFoods = listFoods;
        this.listNumberFoods = listNumberFoods;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_checkbill_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listFoods.get(position).getTenmon());
        holder.price.setText(Integer.toString(listFoods.get(position).getGiatien()) + "đ");
        holder.numberfood.setText(Integer.toString(listNumberFoods.get(position)));
        holder.sumprice.setText(Integer.toString(listFoods.get(position).getGiatien()*listNumberFoods.get(position)) + "đ");
    }

    @Override
    public int getItemCount(){
        return listFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, numberfood, sumprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.checkbill_title);
            price = itemView.findViewById(R.id.checkbill_price);
            numberfood = itemView.findViewById(R.id.checkbill_number);
            sumprice = itemView.findViewById(R.id.checkbill_sumprice);
        }
    }

    public void setEmptyListFoods(){ this.listFoods = new ArrayList<>(); }
    public void setFoods(List<Food> listFoods, List<Integer> listNumberFoods){
        this.listFoods = listFoods;
        this.listNumberFoods = listNumberFoods;
    }
}