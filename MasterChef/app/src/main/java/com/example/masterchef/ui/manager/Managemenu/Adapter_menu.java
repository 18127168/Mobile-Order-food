package com.example.masterchef.ui.manager.Managemenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
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

import java.util.IdentityHashMap;
import java.util.List;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.Menuviewholder> {

    LayoutInflater inflater;
    Context contexts;
    List<Integer> listIDFoodInMenu;
    DatabaseWork databaseWork=new DatabaseWork();
    StorageReference storeImage;

   public Adapter_menu(Context context,  List<Integer> listIDFoodInMenu){
       this.inflater = LayoutInflater.from(context);
       this.contexts = context;
       this.listIDFoodInMenu = listIDFoodInMenu;
   }

    @NonNull
    @Override
    public Adapter_menu.Menuviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.manager_adapter_managemenu,parent,false);
        return new Adapter_menu.Menuviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_menu.Menuviewholder holder, int position) {
        Food food = databaseWork.GetFoodWithID(Integer.parseInt(listIDFoodInMenu.get(position) + ""));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(food.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
                holder.title.setText(food.getTenmon());
                holder.price.setText(food.getGiatien() + " đ");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return listIDFoodInMenu.size();
    }

    public class Menuviewholder extends RecyclerView.ViewHolder{
        private ImageView imgFood;
        TextView title,price;
        public Menuviewholder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
        }

    }
}
