package com.example.masterchef.ui.customer.checkbill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.server;

public class CheckBillFragment extends Fragment {

    public RecyclerView dataList;
    TextView totalcost, VAT, billcost, discount_price;
    EditText discount_code;
    Button checkbill_button;
    List<Food> listFoods = new ArrayList<>();
    List<Integer> listNumberFoods = new ArrayList<>();
    List<Integer> listIDBills = new ArrayList<>();
    boolean firstTime = true;
    DatabaseWork databaseWork = new DatabaseWork();

    CheckBillAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_customer_checkbill, container, false);
        dataList = root.findViewById(R.id.checkbill_recyclerCategory);
        discount_code = root.findViewById(R.id.checkbill_discount_code);
        totalcost = root.findViewById(R.id.checkbill_totalcost);
        discount_price = root.findViewById(R.id.checkbill_discount_price);
        VAT = root.findViewById(R.id.checkbill_VAT);
        billcost = root.findViewById(R.id.checkbill_billcost);
        checkbill_button = root.findViewById(R.id.checkbill_button);

        checkbill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCompleteForBill();

                adapter.setEmptyListFoods();
                adapter.notifyDataSetChanged();

                totalcost.setText("0 đ");
                discount_price.setText("0 đ");
                VAT.setText("0 đ");
                billcost.setText("0 đ");
            }
        });

        adapter = new CheckBillAdapter(getActivity(), listFoods, listNumberFoods);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference(server.getText().toString());
        Query userQuery = dataRef.child("HoaDon").orderByChild("hoaDonSo");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    listFoods = new ArrayList<>();
                    listNumberFoods = new ArrayList<>();
                    listIDBills = new ArrayList<>();
                    
                    totalcost.setText("0 đ");
                    discount_price.setText("0 đ");
                    VAT.setText("0 đ");
                    billcost.setText("0 đ");

                    adapter.setEmptyListFoods();
                    adapter.notifyDataSetChanged();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                        if(hoadon.getTable().contains(IDTable) && hoadon.getThanhToan() == false){
                            listIDBills.add(hoadon.getHoaDonSo());
                            firstTime = false;

                            String[] listSplitIDFood = hoadon.getID().split(",");
                            String[] listSplitNumFood = hoadon.getSoLuong().split(",");

                            for (int i = 0; i < listSplitIDFood.length; i++){
                                if ( listFoods.size() == 0){
                                    Food tempFood = databaseWork.GetFoodWithID(Integer.parseInt(listSplitIDFood[i]));
                                    int tempNumberFood = Integer.parseInt(listSplitNumFood[i]);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference dataRef = database.getReference(server.getText().toString());
                                    Query userQuery = dataRef.child("HoaDon").orderByChild("hoaDonSo");
                                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            listFoods.add(tempFood);
                                            listNumberFoods.add(tempNumberFood);

                                            adapter.setFoods(listFoods, listNumberFoods);
                                            adapter.notifyDataSetChanged();

                                            int sum = 0;
                                            for (int i = 0; i < listFoods.size(); i++){
                                                sum += listFoods.get(i).getGiatien()*listNumberFoods.get(i);
                                            }

                                            totalcost.setText(Integer.toString(sum) + " đ");
                                            discount_price.setText(Integer.toString(sum*10/100) + " đ" );
                                            VAT.setText(Integer.toString((sum - sum*10/100)*10/100) + " đ");
                                            billcost.setText(Integer.toString(sum - (sum - sum*10/100)*10/100) + " đ");
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                else {
                                    for (int j = 0; j < listFoods.size(); j++) {
                                        if (listFoods.get(j).getID() == Integer.parseInt(listSplitIDFood[i])) {
                                            listNumberFoods.set(j, listNumberFoods.get(i) + Integer.parseInt(listSplitNumFood[i]));
                                        } else if (j == listFoods.size() - 1) {
                                            Food tempFood = databaseWork.GetFoodWithID(Integer.parseInt(listSplitIDFood[i]));
                                            int tempNumberFood = Integer.parseInt(listSplitNumFood[i]);

                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference dataRef = database.getReference(server.getText().toString());
                                            Query userQuery = dataRef.child("HoaDon").orderByChild("hoaDonSo");
                                            userQuery.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    listFoods.add(tempFood);
                                                    listNumberFoods.add(tempNumberFood);

                                                    adapter.setFoods(listFoods, listNumberFoods);
                                                    adapter.notifyDataSetChanged();

                                                    int sum = 0;
                                                    for (int i = 0; i < listFoods.size(); i++){ sum += listFoods.get(i).getGiatien()*listNumberFoods.get(i); }

                                                    totalcost.setText(Integer.toString(sum) + " đ");
                                                    discount_price.setText(Integer.toString(sum*10/100) + " đ" );
                                                    VAT.setText(Integer.toString((sum - sum*10/100)*10/100) + " đ");
                                                    billcost.setText(Integer.toString(sum - (sum - sum*10/100)*10/100) + " đ");
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                            break;
                                        }
                                    }
                                }
                            }
                        };
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }

    public void setCompleteForBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference(server.getText().toString());
        Query userQuery = dataRef.child("HoaDon").orderByChild("hoaDonSo");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                        if (listIDBills.contains(hoadon.getHoaDonSo())) {
                            postSnapshot.getRef().child("thanhToan").setValue(true);
                        }
                    }
                }

                listFoods = new ArrayList<>();
                listNumberFoods = new ArrayList<>();
                listIDBills = new ArrayList<>();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}