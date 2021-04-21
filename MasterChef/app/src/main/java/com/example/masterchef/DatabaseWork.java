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
    /*public void AddFoodToMenuAndToFirebase(Food food,int menuCuaThuMay, Context thisActivity){
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int id=0;
                boolean checkExist = false; //khong ton tai gia tri trung
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Food foodAdd = snapshot.getValue(Food.class);
                    if(foodAdd.getTenmon().equals(food.getTenmon())){
                        dataref.child("Menu").child(""+menuCuaThuMay).setValue(foodAdd.getID());
                        checkExist = true;
                        Toast.makeText(thisActivity,"Add Food Success" ,Toast.LENGTH_LONG).show();
                    }
                    else{
                        id++;
                    }
                }
                if(checkExist == false){
                    food.setID(id);

                    String userId = dataref.push().getKey();
                    //menu.listFoodInMenu.add(food.getID());
                    //dataref.child("Menu").child(userId).setValue(menu);
                    checkExist = true;
                    Toast.makeText(thisActivity,"Add Food Success" ,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: " ,error.getMessage());
            }
        });

    }*/

   //lay id cua cac mon an trong menu cua 1 ngay nao do.
   public List<Integer> GetFoodInMenu(int ThuMay) {
       List<Integer> result = new ArrayList<>();
       Query userQuery = dataref.child("Menu").orderByKey();
       userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                       List<Integer> menuofthisday = (List<Integer>) postSnapshot.getValue();
                       if(postSnapshot.getKey().equals(""+ThuMay)){
                           for(int i=0;i<menuofthisday.size();i++) {
                               String temp = menuofthisday.get(i) + "";
                               result.add(Integer.parseInt(temp));
                           }
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
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Food foodSearch = postSnapshot.getValue(Food.class);
                        if(foodSearch.getID() == ID){
                            result.setID(foodSearch.getID());
                            result.setFlagName(foodSearch.getFlagName());
                            result.setTenmon(foodSearch.getTenmon());
                            result.setTimeToFinish(foodSearch.getTimeToFinish());
                            result.setgiatien(foodSearch.getGiatien());
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
    // add hoadon
    public void AddHoaDon(HoaDon hoadon){
        Query userQuery = dataref.child("HoaDon").orderByChild("hoaDonSo");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userId = dataref.push().getKey();
                dataref.child(userId).setValue(hoadon);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
