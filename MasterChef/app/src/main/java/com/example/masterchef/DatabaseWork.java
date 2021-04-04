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
import java.util.List;

public class DatabaseWork {

    /*FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataref = database.getReference("User");
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

    }
    public MenuWithFoodInFireBase GetFoodInMenu(int ThuMay) {
        MenuWithFoodInFireBase result = new MenuWithFoodInFireBase();
        Query userQuery = dataref.child("Menu").orderByKey();
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        MenuWithFoodInFireBase menu = postSnapshot.getValue(MenuWithFoodInFireBase.class);
                        if(postSnapshot.getKey().equals(""+ThuMay)){
                            result.listFoodInMenu = menu.listFoodInMenu;
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
    //public List<Menu> GetFood{

*/

}
class MenuWithFoodInFireBase{
    /*public List<Integer> listFoodInMenu;
    MenuWithFoodInFireBase(){
        listFoodInMenu = new ArrayList<>();
    }
    MenuWithFoodInFireBase(List<Integer> list){
        listFoodInMenu = list;
    }*/
}
