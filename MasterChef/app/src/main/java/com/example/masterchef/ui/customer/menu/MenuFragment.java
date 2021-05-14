package com.example.masterchef.ui.customer.menu;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.CustomerActivity;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.HoaDon;
import com.example.masterchef.MainActivity;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.server;


public class MenuFragment extends Fragment {

    public RecyclerView dataList;
    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();
    public static boolean firstTime = true;

    MenuAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_customer_menu, container, false);

        dataList = root.findViewById(R.id.menu_recyclerCategory);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_customer_welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(getActivity(), R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button dialog_btn = dialog.findViewById(R.id.welcome_closeDialog_btn);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (firstTime){
            dialog.show();
            firstTime = false;
        }

        EditText searchBar = root.findViewById(R.id.menu_search_bar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference(server.getText().toString());
        Query userQuery = dataRef.child("Menu").orderByKey();
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (day == 1) day = 8;
                listIDFoodInMenu = databaseWork.GetFoodInMenu(day);

                Query userQuery = dataRef.child("Food").orderByChild("ID");
                userQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        searchBar.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String keyword = searchBar.getText().toString();

                                ArrayList<Integer> tempListIDFood = new ArrayList<>();

                                for (int i = 0; i < listTitles.size(); i++) {
                                    if (listTitles.get(i).contains(keyword)) {
                                        tempListIDFood.add(listIDFoodInMenu.get(i));
                                    }
                                }

                                adapter = new MenuAdapter(getActivity(), tempListIDFood);

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                                dataList.setLayoutManager(gridLayoutManager);
                                dataList.setAdapter(adapter);
                            }
                        });

                        adapter = new MenuAdapter(getActivity(), listIDFoodInMenu);

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                        dataList.setLayoutManager(gridLayoutManager);
                        dataList.setAdapter(adapter);

                        listTitles = adapter.getListTitlesFood();

                        Query userQuery = dataRef.child("HoaDon").orderByChild("ID");
                        userQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                                        if((hoadon.getTable() == IDTable) && (hoadon.getTrangthai() == 3)){
                                            postSnapshot.getRef().removeValue();

                                           String[] listId = hoadon.getID().split(",");
                                           String[] listNotes = hoadon.getNotes().split(";");
                                           String[] listSoLuong = hoadon.getSoLuong().split(",");

                                           for (int i = 0; i < listId.length; i++) {
                                               SelectedFood.addSelectedFoodWithNumAndNote(Integer.parseInt(listId[i]), Integer.parseInt(listSoLuong[i]), listNotes[i]);
                                           }

                                           searchBar.setText("");
                                           adapter = new MenuAdapter(getActivity(), listIDFoodInMenu);

                                           GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                                           dataList.setLayoutManager(gridLayoutManager);
                                           dataList.setAdapter(adapter);

                                           listTitles = adapter.getListTitlesFood();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return root;
    }

}