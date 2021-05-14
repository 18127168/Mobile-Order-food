package com.example.masterchef.ui.manager.Managemenu;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.Ingredient;
import com.example.masterchef.ItemClickListener;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.example.masterchef.MainActivity.server;

public class Adapter_Food extends RecyclerView.Adapter<Adapter_Food.ViewHolder> {
    LayoutInflater inflater;
    Context contexts;
    StorageReference storeImage;
    List<Food> Foods;
    int thu;

    public Adapter_Food(Context context, List<Food> Foods, int thu){
        this.inflater = LayoutInflater.from(context);
        this.Foods = Foods;
        this.contexts = context;
        this.thu = thu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.manager_employ_add_menu_inthisday_exist_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataRef = database.getReference(server.getText().toString());
                Query userQuery = dataRef.child("Menu").child(thu + "");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int id = 0;
                        if (snapshot.exists()) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) { id++; }
                        }
                        DatabaseReference dtReferenceef = FirebaseDatabase.getInstance().getReference().child(server.getText().toString()).child("Menu").child(thu + "");
                        dtReferenceef.child(id + "").setValue(Foods.get(position).getID());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(Foods.get(position).getFlagName());
        Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
        holder.title.setText(Foods.get(position).getTenmon());
    }

    @Override
    public int getItemCount(){
        return Foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView imgFood;
        TextView title;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
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