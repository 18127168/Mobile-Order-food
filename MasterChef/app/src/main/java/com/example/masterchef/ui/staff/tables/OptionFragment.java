package com.example.masterchef.ui.staff.tables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.example.masterchef.ui.customer.menu.MenuAdapter;
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

public class OptionFragment extends Fragment {

    public RecyclerView dataList;
    OptionAdapter adapter;

    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_tables_option, container, false);
        dataList = root.findViewById(R.id.option_recyclerCategory);

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

                //listTitles = adapter.getListTitlesFood();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        return root;
    }

}