package com.example.masterchef.ui.customer.checkbill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;

public class CheckBillFragment extends Fragment {

    private CheckBillViewModel galleryViewModel;
    public RecyclerView dataList;
    String[] titles = {"Cá kèo nướng", "King crab", "Gỏi bò", "Tôm hùm", "Dê ré"};
    int[] prices = {200, 300, 400, 500, 600};
    int[] numberfoods = {2, 3, 4, 5, 6};
    TextView totalcost, VAT, billcost;

    CheckBillAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =  new ViewModelProvider(this).get(CheckBillViewModel.class);

        View root = inflater.inflate(R.layout.fragment_customer_checkbill, container, false);
        dataList = root.findViewById(R.id.checkbill_recyclerCategory);

        adapter = new CheckBillAdapter(getActivity(), titles, prices, numberfoods);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        totalcost = root.findViewById(R.id.checkbill_totalcost);
        VAT = root.findViewById(R.id.checkbill_VAT);
        billcost = root.findViewById(R.id.checkbill_billcost);

        int sum = 0;
        for (int i = 0; i < titles.length; i++){ sum += prices[i]*numberfoods[i]; }

        totalcost.setText(Integer.toString(sum));
        VAT.setText(Integer.toString(sum*10/100));
        billcost.setText(Integer.toString(sum + sum*10/100));

        return root;
    }
}