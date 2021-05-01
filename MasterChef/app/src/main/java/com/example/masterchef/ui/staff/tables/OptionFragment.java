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

public class OptionFragment extends Fragment {

    public RecyclerView dataList;
    String[] titles = {"Cá kèo nướng", "King crab", "Gỏi bò", "Tôm hùm", "Dê ré"};
    int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
    int[] prices = {100000, 120000, 125000, 200000, 250000};
    OptionAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_tables_option, container, false);
        dataList = root.findViewById(R.id.option_recyclerCategory);

        adapter = new OptionAdapter(getActivity(), images, titles, prices);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }
}