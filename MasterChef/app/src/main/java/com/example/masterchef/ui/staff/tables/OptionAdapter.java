package com.example.masterchef.ui.staff.tables;

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

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    String[] titles;
    int[] images;
    int[] prices;
    LayoutInflater inflater;


    public OptionAdapter(Context context, int[] images, String[] titles, int[] prices){
        this.titles = titles;
        this.images = images;
        this.prices = prices;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_table_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFood.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.price.setText( Integer.toString(prices[position]) + " đ");
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView title, price, item_quantity, table_id;
        Button add_btn, minus_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            item_quantity = (TextView) itemView.findViewById(R.id.item_quantity);
            add_btn = itemView.findViewById(R.id.menu_add_btn);
            minus_btn = itemView.findViewById(R.id.menu_minus_btn);
            table_id = itemView.findViewById(R.id.table_id);

            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = item_quantity.getText().toString();
                    int num = Integer.parseInt(s);
                    num++;
                    item_quantity.setText(String.valueOf(num));
                }
            });

            minus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = item_quantity.getText().toString();
                    int num = Integer.parseInt(s);
                    if (num > 0) {
                        num--;
                        item_quantity.setText(String.valueOf(num));
                    }
                }
            });
        }
    }
}