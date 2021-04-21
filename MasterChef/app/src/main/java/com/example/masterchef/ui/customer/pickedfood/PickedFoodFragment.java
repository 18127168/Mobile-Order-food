package com.example.masterchef.ui.customer.pickedfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;

import java.util.ArrayList;
import java.util.List;

public class PickedFoodFragment extends Fragment {

    public RecyclerView dataList;
    List<SelectedFood.Food> listIDsFoodInMenuWithQuantity = new ArrayList<>();

    PickedFoodAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listIDsFoodInMenuWithQuantity = SelectedFood.Foods;

        View root = inflater.inflate(R.layout.fragment_customer_pickedfood, container, false);
        dataList = root.findViewById(R.id.pickedfood_recyclerCategory);

        adapter = new PickedFoodAdapter(getActivity(), listIDsFoodInMenuWithQuantity);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        return root;
    }
}