package com.example.masterchef.ui.staff.serve;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.ServeItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServeFragment extends Fragment {

    private ServeViewModel galleryViewModel;
    public RecyclerView dataList;
//    String[] titles = {"Cá kèo nướng", "King crab", "Gỏi bò", "Tôm hùm", "Dê ré"};
//    int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
//    int[] quantity = {1, 5, 2, 2, 1};
//    int[] table_id = {1, 2, 3, 4, 5};
    DatabaseWork databaseWork = new DatabaseWork();
    ServeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(ServeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_staff_serve, container, false);
        dataList = root.findViewById(R.id.serve_recyclerCategory);


        Query query = FirebaseDatabase.getInstance().getReference("User").child("HoaDon");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("onDataChange ", "ok");

                List<ServeItem> ListServe = databaseWork.ListServe(snapshot);
                adapter = new ServeAdapter(getActivity(), ListServe);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return root;
    }
}