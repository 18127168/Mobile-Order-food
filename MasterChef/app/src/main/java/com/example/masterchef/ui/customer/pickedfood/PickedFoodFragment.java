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

public class PickedFoodFragment extends Fragment {

    private PickedFoodViewModel galleryViewModel;
    public RecyclerView dataList;
    String[] titles = {"Cá kèo nướng", "King crab", "Gỏi bò", "Tôm hùm", "Dê ré"};
    int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5};
    int[] prices = {100000, 110000, 120000, 150000, 160000};
    int[] numberfood = {1, 2, 3, 4, 5};
    String[] note = {"Không cay","Không tỏi","","",""};

    PickedFoodAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(PickedFoodViewModel.class);

        View root = inflater.inflate(R.layout.fragment_customer_pickedfood, container, false);
        dataList = root.findViewById(R.id.pickedfood_recyclerCategory);

        adapter = new PickedFoodAdapter(getActivity(), images, titles, prices, numberfood, note);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);



        return root;
    }
}