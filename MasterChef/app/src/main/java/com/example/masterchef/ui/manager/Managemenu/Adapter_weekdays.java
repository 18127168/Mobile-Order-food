package com.example.masterchef.ui.manager.Managemenu;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.ui.customer.menu.MenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.masterchef.MainActivity.server;

public class Adapter_weekdays extends RecyclerView.Adapter<Adapter_weekdays.Menu_categoricalViewholder> {
    private Context mcontext;
    public Adapter_weekdays(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Adapter_weekdays.Menu_categoricalViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_adapter_managemenu1,parent,false);

        return new Adapter_weekdays.Menu_categoricalViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_weekdays.Menu_categoricalViewholder holder, int position) {
        if (position == 6) {
            holder.textView.setText("Thực đơn chu nhat");
        }
        else {
            holder.textView.setText("Thực đơn Thứ " + (position + 2));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userQuery = dataref.child("Menu").orderByKey();
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Integer> listIDFoodInMenu = new ArrayList<>();
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            if (postSnapshot.getKey().equals("" + (position + 2))) {
                                List<Integer> menuofthisday = (List<Integer>) postSnapshot.getValue();
                                for (int i = 0; i < menuofthisday.size(); i++)
                                    listIDFoodInMenu.add(menuofthisday.get(i));
                                break;
                            }
                    }

                    Query userQuery = dataref.child("Menu").orderByKey();
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(mcontext,2);

                            Adapter_menu adaptermenu =new Adapter_menu(mcontext, listIDFoodInMenu, (position+2));
                            holder.recyclerView.setLayoutManager(gridLayoutManager);
                            holder.recyclerView.setAdapter(adaptermenu);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(mcontext);
                dialog.setContentView(R.layout.manager_employ_add_menu_inthisday);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button addExist =(Button)dialog.findViewById(R.id.btn_manager_add_exist_food);
                Button addNew =(Button)dialog.findViewById(R.id.btn_manager_add_new_food);
                Button close = dialog.findViewById(R.id.btn_manager_closeDialog);

                addExist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Dialog dialog2 =new Dialog(mcontext);
                        dialog2.setContentView(R.layout.manager_employ_add_menu_inthisday_exist);
                        dialog2.setCanceledOnTouchOutside(false);
                        Button close = dialog2.findViewById(R.id.btn_manager_closeDialog_exist);
                        dialog2.show();

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataRef = database.getReference(server.getText().toString());
                        Query userQuery = dataRef.child("Food");
                        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    List<Food> listFoods = new ArrayList<>();

                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        listFoods.add(postSnapshot.getValue(Food.class));
                                    }

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference dataRef = database.getReference(server.getText().toString());
                                    Query userQuery = dataRef.child("Menu").child((position + 2) + "");
                                    userQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                    for (int i = 0; i < listFoods.size(); i++){
                                                        if (listFoods.get(i).getID() == postSnapshot.getValue(Integer.class)){
                                                            listFoods.remove(i);
                                                        }
                                                    }
                                                }

                                                Adapter_Food adapter = new Adapter_Food(mcontext, listFoods, position + 2);

                                                GridLayoutManager gridLayoutManager = new GridLayoutManager(mcontext, 2, GridLayoutManager.VERTICAL, false);

                                                RecyclerView dataList = dialog2.findViewById(R.id.menu_recyclerCategory);
                                                dataList.setLayoutManager(gridLayoutManager);
                                                dataList.setAdapter(adapter);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
                addNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class Menu_categoricalViewholder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;
        ImageView btn_add;

        public Menu_categoricalViewholder(@NonNull View itemView) {
            super(itemView);
            recyclerView= itemView.findViewById(R.id.rev_menu);
            textView=itemView.findViewById(R.id.name_category_tv);
            btn_add=itemView.findViewById(R.id.btn_add_menu_inthisday);
        }
    }
}
