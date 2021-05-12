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

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
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

import static com.example.masterchef.MainActivity.server;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    Context contexts;
    ArrayList<String> listTitles;
    StorageReference storeImage;
    List<Integer> IDFoodInMenus;
    DatabaseWork databaseWork = new DatabaseWork();
    LayoutInflater inflater;


    public OptionAdapter(Context context, List<Integer> IDFoods){
        this.IDFoodInMenus = IDFoods;
        this.inflater = LayoutInflater.from(context);
        this.contexts = context;
        listTitles = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_table_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Food menuFood = databaseWork.GetFoodWithID(Integer.parseInt(IDFoodInMenus.get(position) + ""));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTitles.add(menuFood.getTenmon());

                storeImage = FirebaseStorage.getInstance()
                        .getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/")
                        .child(menuFood.getFlagName());
                Glide.with(contexts.getApplicationContext())
                        .using(new FirebaseImageLoader())
                        .load(storeImage)
                        .into(holder.imgFood);
                holder.title.setText(menuFood.getTenmon());
                holder.price.setText(menuFood.getGiatien() + " đ");

                holder.add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = holder.item_quantity.getText().toString();
                        int num = Integer.parseInt(s);
                        num++;
                        holder.item_quantity.setText(String.valueOf(num));

                    }
                });

                holder.minus_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = holder.item_quantity.getText().toString();
                        int num = Integer.parseInt(s);
                        if (num > 0) {
                            num--;
                            holder.item_quantity.setText(String.valueOf(num));
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount(){
        return IDFoodInMenus.size();
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