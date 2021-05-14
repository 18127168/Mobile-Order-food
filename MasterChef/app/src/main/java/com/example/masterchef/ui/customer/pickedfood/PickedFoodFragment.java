package com.example.masterchef.ui.customer.pickedfood;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.HoaDon;
import com.example.masterchef.Ingredient;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.example.masterchef.MainActivity.server;
import static com.example.masterchef.MainActivity.IDTable;

public class PickedFoodFragment extends Fragment {

    public RecyclerView dataList;
    List<SelectedFood.Food> listIDsFoodInMenuWithQuantity = new ArrayList<>();
    Button pickedfood_OrderBtn;
    PickedFoodAdapter adapter;
    boolean isEnoughIngredient = true;
    List<Integer> listNotEnoughIngredient;
    List<Integer> listIDConsumeIngredient;
    List<Integer> listNumConsumeIngredient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listIDsFoodInMenuWithQuantity = SelectedFood.Foods;

        View root = inflater.inflate(R.layout.fragment_customer_pickedfood, container, false);
        dataList = root.findViewById(R.id.pickedfood_recyclerCategory);
        pickedfood_OrderBtn = root.findViewById(R.id.pickedfood_OrderBtn);

        if (listIDsFoodInMenuWithQuantity.size() == 0){
            pickedfood_OrderBtn.setEnabled(false);
        }

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_customer_outofstock);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(getActivity(), R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button dialog_btn = dialog.findViewById(R.id.close_outOfStockDialog_btn);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pickedfood_OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SelectedFood.Food> tempList = adapter.getListIDsFoodInMenuWithQuantity();

                listNotEnoughIngredient = new ArrayList<>();
                listIDConsumeIngredient = new ArrayList<>();
                listNumConsumeIngredient = new ArrayList<>();

                for (int i = 0; i < tempList.size(); i++) {
                    isEnoughIngredients1(tempList.get(i).getIdFood(), tempList.get(i).getQuantity());
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataref = database.getReference(server.getText().toString());
                Query userQuery = dataref.child("Ingredient").orderByChild("IDNguyenLieu");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataref = database.getReference(server.getText().toString());
                        Query userQuery = dataref.child("HoaDon");
                        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                if (!isEnoughIngredient){
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference dataref = database.getReference(server.getText().toString());
                                    Query userQuery = dataref.child("Food");
                                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                String notEnough = "";
                                                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                                                    if (listNotEnoughIngredient.contains(postSnapshot.getValue(Food.class).getID())){
                                                        if (!notEnough.equals("")) {
                                                            notEnough += ", ";
                                                        }
                                                        notEnough += postSnapshot.getValue(Food.class).getTenmon();
                                                    }
                                                }

                                                TextView content = dialog.findViewById(R.id.sorry_content_outOfStockDialog);
                                                content.setText("Các món " + notEnough + " hiện tại không đủ nguyên liệu. Nhà hàng chúng tôi chân thành xin lỗi quý khách vì sự bất tiện này. Xin quý khách vui lòng chọn món khác!");
                                                dialog.show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    Dialog dialog = new Dialog(getActivity());
                                    dialog.setContentView(R.layout.custom_dialog_customer_oder_annou);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                        dialog.getWindow().setBackgroundDrawable(getDrawable(getActivity(), R.drawable.background));
                                    }
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.setCancelable(false);

                                    Button dialog_btn = dialog.findViewById(R.id.order_closeDialog_btn);
                                    dialog_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    
                                    dialog.show();

                                    int id = 1;
                                    if(snapshot2.exists()){
                                        for (DataSnapshot postSnapshot: snapshot2.getChildren()) { id++; }
                                    }

                                    String listIDFood = "", listNumFood = "", listNoteFood = "", listNumFinishedFood = "", listNumServedFood = "";
                                    for (int i = 0; i < tempList.size(); i++){
                                        if (i != 0) {
                                            listIDFood += ",";
                                            listNumFood += ",";
                                            listNoteFood += ";";
                                            listNumFinishedFood +=  ",";
                                            listNumServedFood +=  ",";
                                        }
                                        listIDFood += tempList.get(i).getIdFood();
                                        listNumFood += tempList.get(i).getQuantity();
                                        if (tempList.get(i).getNote().equals("")){
                                            listNoteFood += " ";
                                        } else {
                                            listNoteFood += tempList.get(i).getNote();
                                        }
                                        listNumFinishedFood +=  "0";
                                        listNumServedFood += "0";
                                    }

                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    String formattedDate = df.format(c);

                                    HoaDon hoaDon = new HoaDon();

                                    hoaDon.setID(listIDFood);
                                    hoaDon.setDate(formattedDate);
                                    hoaDon.setSoLuong(listNumFood);
                                    hoaDon.setTable(IDTable);
                                    hoaDon.setNotes(listNoteFood);
                                    hoaDon.setHoanthanh(listNumFinishedFood);
                                    hoaDon.setHoaDonSo(id);
                                    hoaDon.setTrangthai(0);
                                    hoaDon.setPhucVu(listNumServedFood);

                                    DatabaseReference dtReferenceef = FirebaseDatabase.getInstance().getReference().child(server.getText().toString()).child("HoaDon");
                                    dtReferenceef.child(id + "").setValue(hoaDon);

                                    SelectedFood.clearSelectedFood();
                                    listIDsFoodInMenuWithQuantity = new ArrayList<>();
                                    adapter.updateListIDsFoodInMenuWithQuantity(listIDsFoodInMenuWithQuantity);
                                    adapter.notifyDataSetChanged();
                                    pickedfood_OrderBtn.setEnabled(false);

                                    if(snapshot1.exists()){
                                        for (int i = 0; i < listIDConsumeIngredient.size(); i++) {
                                            for (DataSnapshot postSnapshot : snapshot1.getChildren()) {
                                                if (postSnapshot.getValue(Ingredient.class).getIdnguyenlieu() == listIDConsumeIngredient.get(i))
                                                    postSnapshot.getRef().child("Soluongtonkho").setValue(postSnapshot.getValue(Ingredient.class).getSoluongtonkho() - listNumConsumeIngredient.get(i));
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        adapter = new PickedFoodAdapter(getActivity(), listIDsFoodInMenuWithQuantity);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }

    public void isEnoughIngredients2(int IDNguyenLieu, int soLuong){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Ingredient").orderByChild("IDNguyenLieu");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getValue(Ingredient.class).getIdnguyenlieu() == IDNguyenLieu) {
                            if (postSnapshot.getValue(Ingredient.class).getSoluongtonkho() < soLuong) {
                                isEnoughIngredient = false;
                                if (!listNotEnoughIngredient.contains(IDNguyenLieu)){
                                    listNotEnoughIngredient.add(IDNguyenLieu);
                                }
                            }
                            else {
                                listIDConsumeIngredient.add(IDNguyenLieu);
                                listNumConsumeIngredient.add(soLuong);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void isEnoughIngredients1(int IDFood, int num){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        if (postSnapshot.getValue(Food.class).getID() == IDFood){
                            String[] listNguyenLieu = postSnapshot.getValue(Food.class).getIdnguyenlieu().split(",");
                            String[] listNumNguyenLieu = postSnapshot.getValue(Food.class).getsoluongnguyenlieu().split(",");

                            for (int i = 0; i < listNguyenLieu.length; i++){
                                isEnoughIngredients2(Integer.parseInt(listNguyenLieu[i]), num*Integer.parseInt(listNumNguyenLieu[i]));
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}