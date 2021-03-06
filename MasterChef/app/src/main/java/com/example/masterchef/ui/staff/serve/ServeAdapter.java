package com.example.masterchef.ui.staff.serve;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.ServeItem;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ServeAdapter extends RecyclerView.Adapter<ServeAdapter.ViewHolder> {

    List<ServeItem> ListServe;
    Context contexts;
    StorageReference storeImage;
    LayoutInflater inflater;


    public ServeAdapter(Context context, List<ServeItem> LS){
        this.ListServe = LS;
        this.contexts = context;
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
        storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child("food" + ListServe.get(position).getFood() + ".jpg");
        Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
        holder.title.setText("Món " + String.valueOf(ListServe.get(position).getFood()));
        holder.table_serve.setText("Table: " + String.valueOf(ListServe.get(position).getTable()));
        holder.bill_number.setText("Bill Number: " + String.valueOf(ListServe.get(position).getBill()));
//        int num = Integer.parseInt(ListServe.get(position).getHoanthanh()) - Integer.parseInt(ListServe.get(position).getPhucVu());
        holder.item_quantity.setText("Quantity: " + String.valueOf(ListServe.get(position).getQuantity()));
        holder.done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.serve_item_layout.removeAllViews();
                Toast.makeText(contexts, "Food has been served to custmers", Toast.LENGTH_SHORT).show();
                String newServe = ListServe.get(position).getComplete();
                ListServe.get(position).getRef().child("PhucVu").setValue(newServe);
            }
        });
    }

    @Override
    public int getItemCount(){
        return ListServe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgFood;
        TextView title, item_quantity, table_serve, bill_number;
        Button done_btn;
        ConstraintLayout serve_item_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            item_quantity = (TextView) itemView.findViewById(R.id.quantity);
            table_serve = itemView.findViewById(R.id.table_serve);
            bill_number = itemView.findViewById(R.id.bill_number);
            done_btn = itemView.findViewById(R.id.done_btn);
            serve_item_layout = (ConstraintLayout) itemView.findViewById(R.id.serve_tab);

        }

        @Override
        public void onClick(View v) {

        }
    }


}