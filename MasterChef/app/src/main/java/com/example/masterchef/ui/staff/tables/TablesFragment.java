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

import com.example.masterchef.R;
import com.example.masterchef.ui.customer.menu.MenuAdapter;
import com.example.masterchef.ui.customer.menu.MenuViewModel;

public class TablesFragment extends Fragment {

    private TablesViewModel galleryViewModel;
    public RecyclerView dataList;
    String[] titles = {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table3};
    int[] seats = {4, 4, 6, 8, 10};
    TablesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(TablesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_staff_tables, container, false);
        dataList = root.findViewById(R.id.menu_recyclerCategory);

        adapter = new TablesAdapter(getActivity(), root, images, titles, seats);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }
}