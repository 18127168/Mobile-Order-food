package com.example.masterchef.ui.customer.checkbill;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.masterchef.Discount;
import com.example.masterchef.Food;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.noHoaDon;
import static com.example.masterchef.MainActivity.server;

public class CheckBillFragment extends Fragment {

    public RecyclerView dataList;
    TextView totalcost, VAT, billcost, discount_price, text_checkbill_discount;
    EditText discount_code;
    Button checkbill_button, discount_button;
    List<Food> listFoods = new ArrayList<>();
    List<Integer> listNumberFoods = new ArrayList<>();
    List<Integer> listIDBills = new ArrayList<>();
    static int discount = 0;
    static String discount_codeeeee = "";
    static int sum = 0;
    CheckBillAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_customer_checkbill, container, false);
        dataList = root.findViewById(R.id.checkbill_recyclerCategory);
        discount_code = root.findViewById(R.id.checkbill_discount_code);
        totalcost = root.findViewById(R.id.checkbill_totalcost);
        discount_price = root.findViewById(R.id.checkbill_discount_price);
        text_checkbill_discount = root.findViewById(R.id.text_checkbill_discount);
        discount_button =  root.findViewById(R.id.checkbill_discount_button);
        VAT = root.findViewById(R.id.checkbill_VAT);
        billcost = root.findViewById(R.id.checkbill_billcost);
        checkbill_button = root.findViewById(R.id.checkbill_button);

        discount_code.setText(discount_codeeeee);
        if (discount != 0){
            text_checkbill_discount.setText("Tiền giảm giá(" + discount + "%)");
        }

        discount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataRef = database.getReference(server.getText().toString());
                Query userQuery = dataRef.child("khuyenmai");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                                if (postSnapshot.getValue(Discount.class).getMakhuyenmai().equals(discount_code.getText().toString())){
                                    discount_codeeeee = postSnapshot.getValue(Discount.class).getMakhuyenmai();
                                    discount = postSnapshot.getValue(Discount.class).getMota();

                                    text_checkbill_discount.setText("Tiền giảm giá(" + discount + "%)");

                                    totalcost.setText(sum + " đ");
                                    discount_price.setText("-" + sum*discount/100 + " đ" );
                                    VAT.setText((sum*discount/100 + sum)*10/100 + " đ");
                                    billcost.setText(sum - (sum*discount/100) + (sum*discount/100 + sum)*10/100 + " đ");

                                    break;
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_customer_checkbill_annou);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(getActivity(), R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button dialog_btn = dialog.findViewById(R.id.checkbill_closeDialog_btn);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        checkbill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataRef = database.getReference(server.getText().toString());
                Query userQuery = dataRef.child("ThongKe");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        setCompleteForBill();

                        boolean exist = false;

                        DateFormat dateFormat = new SimpleDateFormat("MMYYYY");
                        Date date = new Date();

                        if (snapshot.exists()){
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                if (postSnapshot.getKey().equals(dateFormat.format(date))){
                                    exist = true;
                                    postSnapshot.getRef().child("DoanhThu").setValue(postSnapshot.child("DoanhThu").getValue(Integer.class) + (sum - (sum*discount/100) + (sum*discount/100 + sum)*10/100));
                                }
                            }
                        }

                        if (!exist){
                            DatabaseReference dtReferenceef = FirebaseDatabase.getInstance().getReference().child(server.getText().toString()).child("ThongKe");
                            HashMap nObj =new HashMap();
                            nObj.put("Chiphi", 0);
                            nObj.put("DoanhThu", (sum - (sum*discount/100) + (sum*discount/100 + sum)*10/100));

                            dtReferenceef.child(dateFormat.format(date)).setValue(nObj);
                        }


                        totalcost.setText("0 đ");
                        discount_price.setText("0 đ");
                        VAT.setText("0 đ");
                        billcost.setText("0 đ");

                        sum = 0;

                        adapter.setEmptyListFoods();
                        adapter.notifyDataSetChanged();

                        dialog.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
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
                    noHoaDon = true;
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
                        if( (hoadon.getTable() == IDTable) && hoadon.getThanhToan() == false){
                            noHoaDon = false;
                            listIDBills.add(hoadon.getHoaDonSo());

                            String[] listSplitIDFood = hoadon.getID().split(",");
                            String[] listSplitNumFood = hoadon.getSoLuong().split(",");

                            for (int i = 0; i < listSplitIDFood.length; i++){
                                int tempNumberFood = Integer.parseInt(listSplitNumFood[i]);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference dataRef = database.getReference(server.getText().toString());
                                Query userQuery = dataRef.child("Food").orderByChild("ID").equalTo(Integer.parseInt(listSplitIDFood[i]));
                                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                                if (!noHoaDon) {
                                                    listFoods.add(postSnapshot.getValue(Food.class));
                                                    listNumberFoods.add(tempNumberFood);
                                                }
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                userQuery = dataRef.child("HoaDon").orderByChild("hoaDonSo");
                                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        List<Integer> listTempPosRemove = new ArrayList<>();

                                        for (int i = 0; i < listFoods.size() - 1; i++){
                                            if (!listTempPosRemove.contains(i)) {
                                                for (int j = i + 1; j < listFoods.size(); j++){
                                                    if (listFoods.get(i).getID() == listFoods.get(j).getID()){
                                                        listNumberFoods.set(i, listNumberFoods.get(i) + listNumberFoods.get(j));
                                                        listTempPosRemove.add(j);
                                                    }
                                                }
                                            }
                                        }

                                        for (int i = 0; i < listTempPosRemove.size(); i++){
                                            int k = listTempPosRemove.get(i);
                                            listFoods.remove(k);
                                        }

                                        adapter.setFoods(listFoods, listNumberFoods);
                                        adapter.notifyDataSetChanged();

                                        for (int i = 0; i < listFoods.size(); i++){
                                            sum += listFoods.get(i).getGiatien()*listNumberFoods.get(i);
                                        }

                                        totalcost.setText(sum + " đ");
                                        discount_price.setText("-" + sum*discount/100 + " đ" );
                                        VAT.setText((sum*discount/100 + sum)*10/100 + " đ");
                                        billcost.setText(sum - (sum*discount/100) + (sum*discount/100 + sum)*10/100 + " đ");
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
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
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
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