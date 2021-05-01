package com.example.masterchef.ui.customer.pickedfood;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.HoaDon;
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

import static com.example.masterchef.MainActivity.server;
import static com.example.masterchef.MainActivity.IDTable;

public class PickedFoodFragment extends Fragment {

    public RecyclerView dataList;
    List<SelectedFood.Food> listIDsFoodInMenuWithQuantity = new ArrayList<>();
    Button pickedfood_OrderBtn;
    DatabaseWork databaseWork = new DatabaseWork();
    PickedFoodAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listIDsFoodInMenuWithQuantity = SelectedFood.Foods;

        View root = inflater.inflate(R.layout.fragment_customer_pickedfood, container, false);
        dataList = root.findViewById(R.id.pickedfood_recyclerCategory);
        pickedfood_OrderBtn = root.findViewById(R.id.pickedfood_OrderBtn);

        if (listIDsFoodInMenuWithQuantity.size() == 0){
            pickedfood_OrderBtn.setEnabled(false);
        }

        pickedfood_OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataref = database.getReference(server.getText().toString());
                Query userQuery = dataref.child("HoaDon");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int id = 1;
                        if(snapshot.exists()){
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) { id++; }
                        }

                        List<SelectedFood.Food> tempList = adapter.getListIDsFoodInMenuWithQuantity();

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
                            listNoteFood += tempList.get(i).getNote();
                            listNumFinishedFood +=  "0";
                            listNumServedFood += "0";
                        }

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);

                        Log.e("Date", formattedDate);

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

                        DatabaseReference dtReferenceef =FirebaseDatabase.getInstance().getReference().child("User").child("HoaDon");
                        dtReferenceef.push().setValue(hoaDon);

                        SelectedFood.clearSelectedFood();
                        listIDsFoodInMenuWithQuantity = new ArrayList<>();
                        adapter.updateListIDsFoodInMenuWithQuantity(listIDsFoodInMenuWithQuantity);
                        adapter.notifyDataSetChanged();
                        pickedfood_OrderBtn.setEnabled(false);
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
}