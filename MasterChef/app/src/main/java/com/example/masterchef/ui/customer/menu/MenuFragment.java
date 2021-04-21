package com.example.masterchef.ui.customer.menu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MenuFragment extends Fragment {

    public RecyclerView dataList;
    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();

    MenuAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_customer_menu, container, false);

        dataList = root.findViewById(R.id.menu_recyclerCategory);

        EditText searchBar = root.findViewById(R.id.menu_search_bar);
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

                for (int i = 0; i < listTitles.size(); i++){
                    if(listTitles.get(i).contains(keyword)){
                        tempListIDFood.add(listIDFoodInMenu.get(i));
                    }
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataref = database.getReference("User");
                Query userQuery = dataref.child("Food").orderByChild("ID");
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter = new MenuAdapter(getActivity(), tempListIDFood);

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                        dataList.setLayoutManager(gridLayoutManager);
                        dataList.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        listIDFoodInMenu = databaseWork.GetFoodInMenu(day);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("User");
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter = new MenuAdapter(getActivity(), listIDFoodInMenu);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);

                listTitles = adapter.getListTitlesFood();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return root;
    }

}