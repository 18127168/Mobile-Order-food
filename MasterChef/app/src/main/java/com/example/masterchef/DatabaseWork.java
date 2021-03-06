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
                            if (Integer.parseInt(dataSnapshot.child("ban").getValue().toString()) != -1) {
                                Tables e = new Tables();
                                e.setId(Integer.parseInt(dataSnapshot.child("ban").getValue().toString()));
                                e.setSeats(Integer.parseInt((dataSnapshot.child("seats").getValue().toString())));
                                tabl.add(e);
                            }
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
    public List<ServeItem> ListServe(DataSnapshot snapshot) {
        List<ServeItem> list = new ArrayList<>();

        if (snapshot.exists()) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String stt = dataSnapshot.child("trangthai").getValue().toString();
                if (stt.equals("0") || stt.equals("1")) {
                    HoaDon bill = dataSnapshot.getValue(HoaDon.class);
                    String[] Foods = bill.getID().split(",");
                    String[] HoanThanh = bill.getHoanthanh().split(",");
                    String[] PhucVu = bill.getPhucVu().split(",");
                    String[] SoLuong = bill.getSoLuong().split(",");

                    for (int i = 0; i < Foods.length; i++) {
                        int compare = Integer.parseInt(HoanThanh[i]) - Integer.parseInt(PhucVu[i]);
                        if (compare > 0) {
                            ServeItem serveItem = new ServeItem();
                            serveItem.setTable(bill.getTable());
                            serveItem.setBill(bill.getHoaDonSo());
                            serveItem.setFood(Integer.parseInt(Foods[i]));
                            serveItem.setQuantity(compare);
                            serveItem.setServed(Integer.parseInt(PhucVu[i]));
                            serveItem.setTotal(Integer.parseInt(SoLuong[i]));
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
        return list;
    }

    public List<YeuCau> GetListRequest(DataSnapshot snapshot) {
        List<YeuCau> list = new ArrayList<>();

        if (snapshot.exists()) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String status = dataSnapshot.child("status").getValue().toString();
                if (status.equals("0")) {
                    YeuCau res = new YeuCau();
                    int table = Integer.parseInt(dataSnapshot.child("table").getValue().toString());
                    int stt = Integer.parseInt(status);
                    String date = dataSnapshot.child("date").getValue().toString();

                    res.setdate(date);
                    res.setStatus(stt);
                    res.setTable(table);
                    res.setRef(dataSnapshot.getRef());

                    list.add(res);
                }
            }
        }

        return list;
    }

    // aaaaa
    public List<Ingredient> GetListIngredient(DataSnapshot snapshot) {
        List<Ingredient> list = new ArrayList<>();

        if (snapshot.exists()) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Ingredient nl = dataSnapshot.getValue(Ingredient.class);
                nl.setRef(dataSnapshot.getRef());
                list.add(nl);
            }
        }

        return list;
    }

    //
    public String AddIngredient(DataSnapshot snapshot, Ingredient ingredient) {

                if (snapshot.exists()) {
                    int id = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        id = Integer.parseInt(dataSnapshot.getKey());
                    }
                    id++;

                    ingredient.setIdnguyenlieu(id);
                    ingredient.setFlagName("ingredient" + id + ".jpg");
                    snapshot.getRef().child(id+"").setValue(ingredient);
                }

        return ingredient.getFlagName();
    }

    public String AddFood(DataSnapshot snapshot, Food food) {
        if (snapshot.exists()) {
            int id = 0;
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                id = Integer.parseInt(dataSnapshot.getKey());
            }
            id++;

            food.setID(id);
            food.setFlagName("food" + id + ".jpg");
            snapshot.getRef().child(id + "").setValue(food);

            return food.getFlagName();
        }
        return "";
    }

    public List<Item_Status> GetListStatus(DataSnapshot snapshot, int table_id) {
        List<Item_Status> list = new ArrayList<>();

        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String stt = ds.child("thanhToan").getValue().toString();
                String table = ds.child("table").getValue().toString();
                String hdSo = ds.child("hoaDonSo").getValue().toString();
                if (stt.equals("false") && table.equals(String.valueOf(table_id)) && !hdSo.equals("-1")) {
                    
                    HoaDon bill = ds.getValue(HoaDon.class);
                    String[] Foods = bill.getID().split(",");
                    String[] PhucVu = bill.getPhucVu().split(",");
                    String[] SoLuong = bill.getSoLuong().split(",");
                    int order_id = Integer.parseInt(ds.getKey().toString());

                    for (int i = 0; i < Foods.length; i++) {
                        Item_Status status = new Item_Status();
                        status.setFood_id(Integer.parseInt(Foods[i]));
                        status.setServed(Integer.parseInt(PhucVu[i]));
                        status.setQuantity(Integer.parseInt(SoLuong[i]));
                        status.setBill(order_id);

                        list.add(status);
                    }
                }
            }
        }

        return list;
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
                        if(postSnapshot.getKey().equals(""+ThuMay)){
                            List<Integer> menuofthisday = (List<Integer>) postSnapshot.getValue();
                            for(int i=0;i<menuofthisday.size();i++)
                                result.add(menuofthisday.get(i));

                            break;
                        }
                        ;
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
    public Food GetFoodWithID(int ID) {
        Food result = new Food();
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Food foodSearch = postSnapshot.getValue(Food.class);
                        if (foodSearch.getID() == ID) {
                            result.setID(foodSearch.getID());
                            result.setFlagName(foodSearch.getFlagName());
                            result.setTenmon(foodSearch.getTenmon());
                            result.setsoluongnguyenlieu(foodSearch.getsoluongnguyenlieu());
                            result.setIdnguyenlieu(foodSearch.getIdnguyenlieu());
                            result.setgiatien(foodSearch.getGiatien());
                            result.setTimeToFinish(foodSearch.getTimeToFinish());
                            result.setIdnguyenlieu(foodSearch.getIdnguyenlieu());
                            result.setMoTa(foodSearch.getmoTa());
                            break;
                        }
                        ;
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
    public List<HoaDon> GetHoaDon(int hoadonso) {
        List<HoaDon> result = new ArrayList<>();
        Query userQuery = dataref.child("HoaDon").orderByChild("hoaDonSo");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                        if (hoadon.getHoaDonSo() == hoadonso) {
                            result.add(hoadon);
                        }
                        ;
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
    public void AddFood(Food FoodNeedAdd) {
        Query userQuery = dataref.child("Food").orderByChild("Tenmon").equalTo(FoodNeedAdd.getTenmon());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "Food exists", Toast.LENGTH_LONG).show();
                } else {
                    String userId = dataref.push().getKey();

                    Query userQuery = dataref.child("Food").orderByChild("ID");
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int id = 0;
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    id++;
                                }
                                FoodNeedAdd.setID(id + 1);
                                dataref.child(userId).setValue(FoodNeedAdd);
                            } else {
                                FoodNeedAdd.setID(1);
                                dataref.child(userId).setValue(FoodNeedAdd);
                            }
                            Toast.makeText(context, "Add Food Success", Toast.LENGTH_LONG).show();
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
    public void DeleteFood(Food FoodNeedDelete) {
        Query query = dataref.child("Food").orderByChild("ID").equalTo(FoodNeedDelete.getID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    Query query = dataref.child("Food").orderByChild("ID");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int ID = 1;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Food foodSearch = snapshot.getValue(Food.class);
                                Map<String, Object> postValues = new HashMap<String, Object>();
                                foodSearch.setID(ID);
                                postValues.put(snapshot.getRef().getKey(), snapshot);
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
    public void AddHoaDon(HoaDon hoadon) {
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
