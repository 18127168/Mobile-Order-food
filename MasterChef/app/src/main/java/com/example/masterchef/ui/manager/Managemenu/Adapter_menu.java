package com.example.masterchef.ui.manager.Managemenu;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.example.masterchef.ui.manager.Managestaff.Adapter;
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

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.Menuviewholder> {

    LayoutInflater inflater;
    Context contexts;
    List<Integer> listIDFoodInMenu;
    DatabaseWork databaseWork=new DatabaseWork();
    StorageReference storeImage;
    int thu;

    public Adapter_menu(Context context,  List<Integer> listIDFoodInMenu,int a){
        this.inflater = LayoutInflater.from(context);
        this.contexts = context;
        this.listIDFoodInMenu = listIDFoodInMenu;
        this.thu=a;
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
                storeImage = FirebaseStorage.getInstance()
                        .getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/")
                        .child(food.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
                holder.title.setText(food.getTenmon());
                holder.price.setText(food.getGiatien() + " đ");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = databaseWork.GetFoodWithID(Integer.parseInt(listIDFoodInMenu.get(position) + ""));
                Dialog dialog=new Dialog(contexts);
                dialog.setContentView(R.layout.manager_employ_edit_menu);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                EditText flag=(EditText)dialog.findViewById(R.id.flag_name);
                //EditText ID=(EditText)dialog.findViewById(R.id.id_menu_manager);
                EditText Idnguyenlieu=(EditText)dialog.findViewById(R.id.ID_nguyen_lieu);
                EditText tenmon=(EditText)dialog.findViewById(R.id.tenmon);
                EditText timetofinish=(EditText)dialog.findViewById(R.id.time_to_finish);
                EditText giatien=(EditText)dialog.findViewById(R.id.giatien);
                EditText soluongnguyenlieu=(EditText)dialog.findViewById(R.id.sl_nguyen_lieu);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataref=database.getReference("User");
                Query userQuery = dataref.child("Food").orderByChild("ID");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String flagname=food.getFlagName().toString();
                        String IdNguyenlieu=food.getIdnguyenlieu().toString();
                        String Name=food.getTenmon().toString();
                        int timefinish=food.getTimeToFinish();
                        int price=food.getGiatien();
                        int iD=food.getID();
                         String slnl=food.getsoluongnguyenlieu().toString();
                        flag.setText(flagname);
                        Idnguyenlieu.setText(IdNguyenlieu);
                        tenmon.setText(Name);
                        timetofinish.setText(String.valueOf(timefinish));
                        giatien.setText(String.valueOf(price));
                        soluongnguyenlieu.setText(slnl);

                        Button btnhuy=(Button) dialog.findViewById(R.id.btnh);
                        btnhuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        Button btndongy=(Button)dialog.findViewById(R.id.btnd);
                        btndongy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference dataref=database.getReference("User");
                                Query userQuery = dataref.child("Food").orderByChild("ID");
                                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String flagname=flag.getText().toString().trim();
                                        String idnguyenlieu=Idnguyenlieu.getText().toString().trim();
                                        String name=tenmon.getText().toString().trim();
                                        String time=timetofinish.getText().toString().trim();
                                        String prices=giatien.getText().toString().trim();
                                        String slnguyenlieu=soluongnguyenlieu.getText().toString().trim();
                                        HashMap hashMap=new HashMap();
                                        hashMap.put("FlagName",flagname);
                                        hashMap.put("Idnguyenlieu",IdNguyenlieu);
                                        hashMap.put("Tenmon",Name);
                                        hashMap.put("TimeToFinish",timefinish);
                                        hashMap.put("giatien",price);
                                        Query query=FirebaseDatabase.getInstance().getReference("User").child("Food").orderByChild("FlagName").equalTo(food.getFlagName().toString());
                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                                                    dataSnapshot1.getRef().updateChildren(hashMap);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Adapter_menu.this.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = databaseWork.GetFoodWithID(Integer.parseInt(listIDFoodInMenu.get(position) + ""));
                Dialog dialog=new Dialog(contexts);
                dialog.setContentView(R.layout.manager_employ_discount_delete);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btndongy=(Button)dialog.findViewById(R.id.btn_manager_dongy_delte_discount);
                Button btnhuy=(Button)dialog.findViewById(R.id.btn_manager_huy_delte_discount);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataref=database.getReference("User");
                Query userQuery = dataref.child("Menu").orderByKey();
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        btndongy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Query query=FirebaseDatabase.getInstance().getReference("User").child("Menu").child(""+thu);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int i = 0;
                                        List<Integer> listtem=new ArrayList<>();
                                        boolean check=true;
                                        for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){

                                            if(!check){
                                                listtem.add(dataSnapshot1.getValue(Integer.class));
                                                dataSnapshot1.getRef().removeValue();
                                            }
                                            else if(i==position){
                                                check=false;
                                                //Log.e("heeeee",""+dataSnapshot1.getValue(Integer.class));
                                                dataSnapshot1.getRef().removeValue();
                                                listIDFoodInMenu.remove(position);
                                            }
                                            else {
                                                i++;
                                            }

                                        }
                                        for(int k=0;k<listtem.size();k++){
                                            FirebaseDatabase.getInstance().getReference("User").child("Menu").child(""+thu).child(""+i).setValue(listtem.get(k));
                                            i++;
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                                dialog.dismiss();
                            }
                        });

                        btnhuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return listIDFoodInMenu.size();
    }

    public class Menuviewholder extends RecyclerView.ViewHolder
    {

        private ImageView imgFood;
        TextView title,price;
        ImageView btn_edit,btn_delete;

        public Menuviewholder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            btn_edit=itemView.findViewById(R.id.btn_edit_adapter_menu);
            btn_delete=itemView.findViewById(R.id.btn_delete_adapter_menu);
        }

    }
}