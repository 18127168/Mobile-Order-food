package com.example.masterchef.ui.staff.tables;

import android.app.DownloadManager;
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
import com.example.masterchef.R;
import com.example.masterchef.Tables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TablesFragment extends Fragment {

    private TablesViewModel galleryViewModel;
    public RecyclerView dataList;
//    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};//    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
////    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
////    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};//    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
////    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
////    int[] seats = {4, 4, 6, 8, 10};//    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
////    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
////    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};
    //    String[] table_id= {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
//    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
//    int[] seats = {4, 4, 6, 8, 10};





    List<Integer> table_id = new ArrayList<>();
    List<Integer> seats = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    TablesAdapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(TablesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_staff_tables, container, false);
        dataList = root.findViewById(R.id.menu_recyclerCategory);


        List<Tables> tables = databaseWork.GetTables();
        Query query = FirebaseDatabase.getInstance().getReference("User").child("username");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                adapter = new TablesAdapter(getActivity(), root, tables);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
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