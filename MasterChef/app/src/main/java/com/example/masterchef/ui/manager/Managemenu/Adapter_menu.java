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
    private List<Menu> mmenu;
    ArrayList<String> listTitles;
    Context contexts;
    DatabaseWork databaseWork=new DatabaseWork();
    List<Integer> IDFoodInMenus;
    StorageReference storeImage;
    public void setData(Context context,List<Integer> IDFoodInMenu){//,List<Integer> IDFoodInMenus,Context context){
        this.IDFoodInMenus= IDFoodInMenus;
        this.contexts=context;
        listTitles = new ArrayList<>();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter_menu.Menuviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_adapter_managemenu,parent,false);
        return new Adapter_menu.Menuviewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_menu.Menuviewholder holder, int position) {
        Menu menu=mmenu.get(position);
        if(menu==null){
            return;
        }
        Food menuFood = databaseWork.GetFoodWithID(IDFoodInMenus.get(position));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTitles.add(menuFood.getTenmon());
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(menuFood.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
                holder.title.setText(menuFood.getTenmon());
                holder.price.setText(menuFood.getGiatien() + " đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //holder.imgFood.setImageResource(menu.getResourceid());
        //holder.tv1.setText(menu.getName());
        /*Food menufood=databaseWork.GetFoodWithID(IDFoodInMenus.get(position));
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userquery=dataref.child("Food").orderByChild("ID");
        userquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(menufood.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        if(mmenu!=null){
            return mmenu.size();
        }
        return 0;
    }

    public class Menuviewholder extends RecyclerView.ViewHolder{
        private ImageView imgFood;
        TextView title,price;
        public Menuviewholder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            //choose_btn = itemView.findViewById(R.id.menu_choose_btn);
            //tv2=itemView.findViewById(R.id.menu_tv2);
        }

    }
}
