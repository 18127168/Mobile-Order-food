package com.example.masterchef.ui.customer.switchtable;

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

public class SwitchTableFragment extends Fragment {

    private SwitchTableViewModel galleryViewModel;
    public RecyclerView dataList;
    int[] images = {R.drawable.table1, R.drawable.table2, R.drawable.table3, R.drawable.table1, R.drawable.table2};
    String[] titles = {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
    int[] seats = {4, 6, 8, 4, 6};
    SwitchTableAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(SwitchTableViewModel.class);

        View root = inflater.inflate(R.layout.fragment_customer_switchtable, container, false);
        dataList = root.findViewById(R.id.switchtable_recyclerCategory);

        adapter = new SwitchTableAdapter(getActivity(), images, titles, seats);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }
}