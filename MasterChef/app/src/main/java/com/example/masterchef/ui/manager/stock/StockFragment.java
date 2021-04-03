package com.example.masterchef.ui.manager.stock;

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

public class StockFragment extends Fragment {

    private StockViewModel galleryViewModel;
    public RecyclerView dataList;
    String[] titles = {"Nguyên liệu 1", "Nguyên liệu 2", "Nguyên liệu 3", "Nguyên liệu 4", "Nguyên liệu 5"};
    int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
    int[] quantity = {14, 11, 19, 15, 26};
    Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(StockViewModel.class);

        View root = inflater.inflate(R.layout.manager_fragment_stock, container, false);
        dataList = root.findViewById(R.id.recyclerCategory);

        adapter = new Adapter(getActivity(), images, titles, quantity);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }
}