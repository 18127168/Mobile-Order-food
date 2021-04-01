package com.example.masterchef.ui.customer.checkbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;

public class CheckBillAdapter extends RecyclerView.Adapter<CheckBillAdapter.ViewHolder> {

    String[] titles;
    int[] prices;
    int[] numberfoods;
    LayoutInflater inflater;

    public CheckBillAdapter(Context context, String[] titles, int[] prices, int[] numberfoods){
        this.titles = titles;
        this.prices = prices;
        this.numberfoods = numberfoods;
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
        holder.title.setText(titles[position]);
        holder.price.setText(Integer.toString(prices[position]));
        holder.numberfood.setText(Integer.toString(numberfoods[position]));
        holder.sumprice.setText(Integer.toString(prices[position]*numberfoods[position]));
    }

    @Override
    public int getItemCount(){
        return titles.length;
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
}