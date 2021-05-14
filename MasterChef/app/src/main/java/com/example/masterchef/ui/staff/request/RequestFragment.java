package com.example.masterchef.ui.staff.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.example.masterchef.ServeItem;
import com.example.masterchef.YeuCau;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestFragment extends Fragment {

    public RecyclerView dataList;
    DatabaseWork databaseWork = new DatabaseWork();
    RequestAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_request, container, false);
        dataList = root.findViewById(R.id.request_recyclerCategory);


        Query query = FirebaseDatabase.getInstance().getReference("User").child("YeuCau");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("onDataChange ", "ok");

                List<YeuCau> ListServe = databaseWork.GetListRequest(snapshot);
                adapter = new RequestAdapter(getActivity(), ListServe);

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