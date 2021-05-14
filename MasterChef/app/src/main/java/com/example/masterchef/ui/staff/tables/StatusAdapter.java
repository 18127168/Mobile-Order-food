package com.example.masterchef.ui.staff.tables;

import android.content.Context;
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
import com.example.masterchef.Item_Status;
import com.example.masterchef.R;
import com.example.masterchef.ServeItem;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    List<Item_Status> ListServe;
    Context contexts;
    StorageReference storeImage;
    LayoutInflater inflater;


    public StatusAdapter(Context context, List<Item_Status> LS){
        this.ListServe = LS;
        this.contexts = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_table_option_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child("food" + ListServe.get(position).getFood_id() + ".jpg");
        Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
        holder.title.setText("Món: " + String.valueOf(ListServe.get(position).getFood_id()));
        holder.price.setText("Đơn: " + String.valueOf(ListServe.get(position).getBill()));
        holder.status.setText("Tình trạng: " + String.valueOf(ListServe.get(position).getServed()) + "/" + String.valueOf(ListServe.get(position).getQuantity()));

    }

    @Override
    public int getItemCount(){
        return ListServe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgFood;
        TextView title, price, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            status = itemView.findViewById(R.id.item_status);

        }

        @Override
        public void onClick(View v) {

        }
    }


}