package com.example.masterchef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChefActivity extends AppCompatActivity {
    Button giamchef,tangchef;
    StorageReference listref = FirebaseStorage.getInstance().getReference();
    FirebaseListAdapter<HoaDon> adapter;
    StorageReference storeImage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataref = database.getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        Toolbar toolbar = findViewById(R.id.chef_toolbar);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView) findViewById(R.id.listChefFood);

        giamchef = (Button) findViewById(R.id.giamchef);
        giamchef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tangchef = (Button) findViewById(R.id.tangchef);
        tangchef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //DatabaseWork db = new DatabaseWork();
        //MenuWithFoodInFireBase ac ;
        //ac = (MenuWithFoodInFireBase) db.GetFoodInMenu(2);
        adapter = new FirebaseListAdapter<HoaDon>(this, HoaDon.class,R.layout.custom_adapter_chef_order_menu_,
                FirebaseDatabase.getInstance().getReference(MainActivity.server.getText().toString()).child("HoaDon").orderByChild("HoaDonSo")) {
            @Override
            protected void populateView(View v, HoaDon model, int position) {
                HoaDon check = model;
                Query userQuery = dataref.child("Food").orderByChild("ID").equalTo(model.getID());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            Food foodCheckTheoIDCuaHoaDon;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                foodCheckTheoIDCuaHoaDon = snapshot.getValue(Food.class);
                                ViewHolder holder;
                                holder = new ViewHolder();
                                holder.flagView = (ImageView) v.findViewById(R.id.imageViewadapter);
                                holder.BanView = (TextView) v.findViewById(R.id.banAdapter);
                                holder.TenMonView = (TextView) v.findViewById(R.id.monanAdapter);
                                holder.TinhtrangView = (TextView) v.findViewById(R.id.trangthaiAdapter);
                                holder.xong = (Button) v.findViewById(R.id.buttonXong);
                                holder.xong.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        model.setTrangthai("Da xong");
                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                        postValues.put(adapter.getRef(position).getKey(),model);
                                        dataref.child("HoaDon").updateChildren(postValues);
                                    }
                                });
                                holder.hetnguyenlieu = (Button) v.findViewById(R.id.buttonHetnguyenlieu);
                                holder.hetnguyenlieu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        model.setTrangthai("Thieu Nguyen Lieu");
                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                        postValues.put(adapter.getRef(position).getKey(),model);
                                        dataref.child("HoaDon").updateChildren(postValues);
                                    }
                                });
                                v.setTag(holder);
                                holder.TenMonView.setText(foodCheckTheoIDCuaHoaDon.getTenmon());
                                holder.BanView.setText("Ban: " + model.getTable());
                                holder.TinhtrangView.setText("Tinh trang: " + model.getTrangthai());
                                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(foodCheckTheoIDCuaHoaDon.getFlagName());
                                Glide.with(ChefActivity.this).using(new FirebaseImageLoader()).load(storeImage).into(holder.flagView);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        };
         /*adapter = new FirebaseListAdapter<HoaDon>(this, HoaDon.class,R.layout.custom_adapter_chef_order_menu_,
                 dataref.child("HoaDon").orderByChild("HoaDonSo")) {
            @Override
            protected void populateView(View v, HoaDon model, int position) {
                HoaDon check = model;
                Query userQuery = dataref.child("Food").orderByChild("ID").equalTo(model.getID());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //if (dataSnapshot.exists())
                        {
                            Food foodCheckTheoIDCuaHoaDon;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                foodCheckTheoIDCuaHoaDon = snapshot.getValue(Food.class);
                                ViewHolder holder;
                                holder = new ViewHolder();
                                holder.flagView = (ImageView) v.findViewById(R.id.imageViewadapter);
                                holder.BanView = (TextView) v.findViewById(R.id.banAdapter);
                                holder.TenMonView = (TextView) v.findViewById(R.id.monanAdapter);
                                holder.TinhtrangView = (TextView) v.findViewById(R.id.trangthaiAdapter);
                                holder.xong = (Button) v.findViewById(R.id.buttonXong);
                                holder.xong.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        model.setTrangthai("Da xong");
                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                        postValues.put(adapter.getRef(position).getKey(),model);
                                        dataref.child("HoaDon").updateChildren(postValues);
                                    }
                                });
                                holder.hetnguyenlieu = (Button) v.findViewById(R.id.buttonHetnguyenlieu);
                                holder.hetnguyenlieu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        model.setTrangthai("Thieu Nguyen Lieu");
                                        Map<String, Object> postValues = new HashMap<String,Object>();
                                        postValues.put(adapter.getRef(position).getKey(),model);
                                        dataref.child("HoaDon").updateChildren(postValues);
                                    }
                                });
                                v.setTag(holder);
                                holder.TenMonView.setText(foodCheckTheoIDCuaHoaDon.getTenmon());
                                holder.BanView.setText("Ban: " + model.getTable());
                                holder.TinhtrangView.setText("Tinh trang: " + model.getTrangthai());
                                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(foodCheckTheoIDCuaHoaDon.getFlagName());
                                Glide.with(ChefActivity.this).using(new FirebaseImageLoader()).load(storeImage).into(holder.flagView);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                }
        };*/

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem exit_action = menu.findItem(R.id.action_logout);

        exit_action.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ChefActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }



    static class ViewHolder {
        ImageView flagView;
        TextView BanView;
        TextView TenMonView;
        TextView TinhtrangView;
        Button xong,hetnguyenlieu;
    }
}