package com.example.masterchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ChefActivity extends AppCompatActivity {
    Button giamchef,tangchef;
    StorageReference listref =FirebaseStorage.getInstance().getReference();
     FirebaseListAdapter<Food> adapter;
     StorageReference storeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
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
         adapter = new FirebaseListAdapter<Food>(this, Food.class,R.layout.activity_chef_adapter_food,
                 FirebaseDatabase.getInstance().getReference("User").child("Order").orderByChild("HoaDonSo")) {
            @Override
            protected void populateView(View v, Food model, int position) {

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
                            FirebaseDatabase.getInstance()
                                    .getReference("User").child("ChefComplete")
                                    .push()
                                    .setValue(model);
                            adapter.getRef(position).removeValue();
                        }
                    });
                    holder.hetnguyenlieu = (Button) v.findViewById(R.id.buttonHetnguyenlieu);
                    holder.hetnguyenlieu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.setTrangthai("Thieu Nguyen Lieu");
                            FirebaseDatabase.getInstance()
                                    .getReference("User").child("ThieuNguyenLieu")
                                    .push()
                                    .setValue(model);
                            adapter.getRef(position).removeValue();
                        }
                    });
                    v.setTag(holder);
                holder.TenMonView.setText(model.getTenmon());
                holder.BanView.setText("Ban: " + model.getTable());
                holder.TinhtrangView.setText("Tinh trang: " + model.getTrangthai());
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(model.getFlagName());
                Glide.with(ChefActivity.this).using(new FirebaseImageLoader()).load(storeImage).into(holder.flagView);
            }
        };

        listView.setAdapter(adapter);

    }

    private FirebaseListAdapter<Food> sortTangListFood(FirebaseListAdapter<Food> ques){

        /*Collections.sort(ques, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                if (o1.getHoaDonSo() == o2.getHoaDonSo()){
                    if (o1.getTimeToFinish() < o2.getTimeToFinish()) {
                        return -1;
                    } else {
                        if (o1.getTimeToFinish() == o2.getTimeToFinish()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                }
                else if(o1.getHoaDonSo() <o2.getHoaDonSo()){ return -1;}
                else { return 1;}
            }
        });*/
        return ques;
    }
    private List<Food> sortGiamListFood(List<Food> ques){
        Collections.sort(ques, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                if (o1.getHoaDonSo() == o2.getHoaDonSo()){
                    if (o1.getTimeToFinish() < o2.getTimeToFinish()) {
                        return 1;
                    } else {
                        if (o1.getTimeToFinish() == o2.getTimeToFinish()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
                else if(o1.getHoaDonSo() <o2.getHoaDonSo()){ return -1;}
                else { return 1;}
            }
        });
        return ques;
    }
    static class ViewHolder {
        ImageView flagView;
        TextView BanView;
        TextView TenMonView;
        TextView TinhtrangView;
        Button xong,hetnguyenlieu;
    }
}
class Food{
    private String FlagName;
    private String Tenmon,Trangthai;
    private int Table,TimeToFinish,HoaDonSo;
    public Food(){
        this.FlagName = "";
        this.Table = 0;
        this.Tenmon = "";
        this.Trangthai = "";
        this.TimeToFinish = 0;
        this.HoaDonSo = 0;
    }
    public Food(String flagname,int table,String tenmon,String trangthai,int timetofinish,int donthux){
        this.FlagName = flagname;
        this.Table = table;
        this.Tenmon = tenmon;
        this.Trangthai = trangthai;
        this.TimeToFinish = timetofinish;
        this.HoaDonSo = donthux;
    }
    public void setFlagName(String a){
        FlagName = a;
    }
    public String getFlagName(){return FlagName;}
    public void setTable(int a){
        Table=a;
    }
    public int getTable(){return Table;}
    public void setTenmon(String a){
    Tenmon = a;
    }
    public String getTenmon(){return Tenmon;}
    public void setTrangthai(String a){
        Trangthai = a;
    }
    public String getTrangthai(){return Trangthai;}
    public void setTimeToFinish(int a){
        TimeToFinish = a;
    }
    public int getTimeToFinish(){return TimeToFinish;}
    public void setHoaDonSo(int a){
        HoaDonSo = a;
    }
    public int getHoaDonSo(){return HoaDonSo;}
}