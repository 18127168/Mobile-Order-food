package com.example.masterchef.ui.staff.tables;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.ui.customer.menu.MenuAdapter;
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

import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.server;

public class OptionFragment extends Fragment {

    public RecyclerView dataList;
    OptionAdapter adapter;

    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();
    Button confirm_btn;
    TextView tb_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_tables_option, container, false);
        dataList = root.findViewById(R.id.option_recyclerCategory);

        Bundle bundle = this.getArguments();
        confirm_btn = root.findViewById(R.id.cf_btn);
        tb_id = root.findViewById(R.id.table_id);
        tb_id.setText("TABLE" + bundle.getInt("table_id"));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        listIDFoodInMenu = databaseWork.GetFoodInMenu(day);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter = new OptionAdapter(getActivity(), listIDFoodInMenu);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);


                confirm_btn.setOnClickListener(new View.OnClickListener() {
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

                                List<Integer> SF = adapter.GetSelectedFoods();
                                List<Integer> FQ = adapter.GetFoodQuantity();

                                String listIDFood = "", listNumFood = "", listNoteFood = "", listNumFinishedFood = "", listNumServedFood = "";
                                for (int i = 0; i < SF.size(); i++){
                                    if (i != 0) {
                                        listIDFood += ",";
                                        listNumFood += ",";
                                        listNoteFood += ";";
                                        listNumFinishedFood +=  ",";
                                        listNumServedFood +=  ",";
                                    }
                                    listIDFood += SF.get(i);
                                    listNumFood += FQ.get(i);
                                    listNoteFood += "";
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
                                hoaDon.setTable(bundle.getInt("table_id"));
                                hoaDon.setNotes(listNoteFood);
                                hoaDon.setHoanthanh(listNumFinishedFood);
                                hoaDon.setHoaDonSo(id);
                                hoaDon.setTrangthai(0);
                                hoaDon.setPhucVu(listNumServedFood);

                                DatabaseReference dtReferenceef = FirebaseDatabase.getInstance().getReference().child("User").child("HoaDon");
                                dtReferenceef.child(id + "").setValue(hoaDon);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                //listTitles = adapter.getListTitlesFood();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        return root;
    }

}