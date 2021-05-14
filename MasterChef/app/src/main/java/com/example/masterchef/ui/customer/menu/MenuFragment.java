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

import static com.example.masterchef.MainActivity.server;


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
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        listIDFoodInMenu = databaseWork.GetFoodInMenu(day);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addValueEventListener(new ValueEventListener() {
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