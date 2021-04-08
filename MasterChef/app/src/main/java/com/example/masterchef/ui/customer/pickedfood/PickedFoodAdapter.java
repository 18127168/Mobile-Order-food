package com.example.masterchef.ui.customer.pickedfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;

public class PickedFoodAdapter extends RecyclerView.Adapter<PickedFoodAdapter.ViewHolder> {

    int[] images;
    String[] titles;
    int[] prices;
    int[] numberfoods;
    String[] notes;
    LayoutInflater inflater;

    public PickedFoodAdapter(Context context, int[] images, String[] titles, int[] prices, int[] numberfoods, String[] notes){
        this.images = images;
        this.titles = titles;
        this.prices = prices;
        this.numberfoods = numberfoods;
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_picked_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.price.setText(prices[position] + " đ");
        holder.number.setText(Integer.toString(numberfoods[position]));
        if (!notes[position].equals("")) { holder.note.setText(notes[position]); }
        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberfoods[position] += 1;
                holder.number.setText(Integer.toString(numberfoods[position]));
            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberfoods[position] > 0) { numberfoods[position] -= 1; }
                holder.number.setText(Integer.toString(numberfoods[position]));
            }
        });
    }

    @Override
    public int getItemCount(){
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        EditText number, note;
        ImageView image, minusBtn, plusBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.pickedfood_image);
            title = itemView.findViewById(R.id.pickedfood_title);
            price = itemView.findViewById(R.id.pickedfood_price);
            number = (EditText) itemView.findViewById(R.id.pickedfood_number);
            note = itemView.findViewById(R.id.pickedfood_note);
            minusBtn = itemView.findViewById(R.id.pickedfood_minusBtn);
            plusBtn = itemView.findViewById(R.id.pickedfood_plusBtn);
        }
    }
}