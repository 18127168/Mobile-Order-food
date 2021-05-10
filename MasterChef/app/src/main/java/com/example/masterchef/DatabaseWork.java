package com.example.masterchef;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseWork {
    String server = MainActivity.server.getText().toString();
    Context context; // activity now
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataref = database.getReference(server);

    // lay thong tin ban
    public List<Tables> GetTables() {
        List<Tables> tabl = new ArrayList<>();
        Query query = dataref.child("username");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.hasChild("ban")) {
                            Tables e = new Tables();
                            e.setId(Integer.parseInt(dataSnapshot.child("ban").getValue().toString()));
                            e.setSeats(Integer.parseInt((dataSnapshot.child("seats").getValue().toString())));
                            tabl.add(e);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  tabl;
    }

    // lay ds mon an can phuc vu
    public List<ServeItem> ListServe() {
        List<ServeItem> list = new ArrayList<>();
        Query query = dataref.child("HoaDon");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String stt = dataSnapshot.child("trangthai").getValue().toString();
                        if (stt.equals("0") || stt.equals("1")) {
                            HoaDon bill = dataSnapshot.getValue(HoaDon.class);
                            String[] Foods = bill.getID().split(",");
                            String[] HoanThanh = bill.getHoanthanh().split(",");
                            String[] PhucVu = bill.getPhucVu().split(",");

                            for (int i = 0; i < Foods.length; i++) {
                                int compare= Integer.parseInt(HoanThanh[i]) - Integer.parseInt(PhucVu[i]);
                                if (compare > 0) {
                                    ServeItem serveItem = new ServeItem();
                                    //serveItem.setTable(bill.getTable());
                                    serveItem.setBill(bill.getHoaDonSo());
                                    serveItem.setFood(Integer.parseInt(Foods[i]));
                                    serveItem.setQuantity(compare);
                                    serveItem.setRef(dataSnapshot.getRef());

                                    String[] cmp = PhucVu;
                                    cmp[i] = HoanThanh[i];
                                    serveItem.setComplete(cmp);

                                    list.add(serveItem);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  list;
    }
  

   //lay id cua cac mon an trong menu cua 1 ngay nao do. //done // i check it and it work // cn = 1
    public List<Integer> GetFoodInMenu(int ThuMay) {
        List<Integer> result = new ArrayList<>();
        Query userQuery = dataref.child("Menu").orderByKey();
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        List<Integer> menuofthisday = (List<Integer>) postSnapshot.getValue();
                        if(postSnapshot.getKey().equals(""+ThuMay)){
                            for(int i=0;i<menuofthisday.size();i++)
                                result.add(menuofthisday.get(i));

                            break;
                        };
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return result;
    }
    //dua vao id lay ten mon trong thuc an
    public Food GetFoodWithID(int ID){
        Food result = new Food();
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Food foodSearch = postSnapshot.getValue(Food.class);
                        if(foodSearch.getID() == ID){
                            result.setID(foodSearch.getID());
                            result.setFlagName(foodSearch.getFlagName());
                            result.setTenmon(foodSearch.getTenmon());
                            result.setSoluongnguyenlieu(foodSearch.getSoluongnguyenlieu());
                            result.setIdnguyenlieu(foodSearch.getIdnguyenlieu());
                            result.setgiatien(foodSearch.getGiatien());
                            result.setTimeToFinish(foodSearch.getTimeToFinish());
                            result.setIdnguyenlieu(foodSearch.getIdnguyenlieu());
                            result.setSoluongnguyenlieu(foodSearch.getSoluongnguyenlieu());
                            break;
                        };
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result;
    }
    //lay hoa don theo hoadonso
    public List<HoaDon> GetHoaDon(int hoadonso){
        List<HoaDon> result = new ArrayList<>();
        Query userQuery = dataref.child("HoaDon").orderByChild("hoaDonSo");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                        HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                        if(hoadon.getHoaDonSo()== hoadonso){
                            result.add(hoadon);
                        };
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return result;
    }
    //add mon an vao db food
    public void AddFood(Food FoodNeedAdd){
        Query userQuery = dataref.child("Food").orderByChild("Tenmon").equalTo(FoodNeedAdd.getTenmon());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context,"Food exists",Toast.LENGTH_LONG).show();
                }
                else{
                    String userId = dataref.push().getKey();

                    Query userQuery = dataref.child("Food").orderByChild("ID");
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                int id = 0;
                                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                    id++;
                                }
                                FoodNeedAdd.setID(id+1);
                                dataref.child(userId).setValue(FoodNeedAdd);
                            }
                            else{
                                FoodNeedAdd.setID(1);
                                dataref.child(userId).setValue(FoodNeedAdd);
                            }
                            Toast.makeText(context,"Add Food Success" ,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    // xoa mon an khoi db food
    public void DeleteFood(Food FoodNeedDelete){
        Query query = dataref.child("Food").orderByChild("ID").equalTo(FoodNeedDelete.getID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    Query query = dataref.child("Food").orderByChild("ID");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int ID = 1;
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                Food foodSearch = snapshot.getValue(Food.class);
                                Map<String, Object> postValues = new HashMap<String,Object>();
                                foodSearch.setID(ID);
                                postValues.put(snapshot.getRef().getKey(),snapshot);
                                dataref.child("Food").updateChildren(postValues);
                                ID++;
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
}
