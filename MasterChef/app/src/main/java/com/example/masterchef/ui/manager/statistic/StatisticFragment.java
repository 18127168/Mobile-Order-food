package com.example.masterchef.ui.manager.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_statistic, container, false);
        BarChart revenueChart = root.findViewById(R.id.revenueChart);
        ArrayList<BarEntry> revenue = new ArrayList<>();
        revenue.add(new BarEntry(1, 100));
        revenue.add(new BarEntry(2, 400));
        revenue.add(new BarEntry(3, 200));
        revenue.add(new BarEntry(4, 300));
        revenue.add(new BarEntry(5, 500));

        BarDataSet barDataSet = new BarDataSet(revenue, "Doanh thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);

        revenueChart.setFitBars(true);
        revenueChart.setData(barData);
        revenueChart.getDescription().setText("Revenue Chart");
        revenueChart.animateY(2000);


        BarChart costChart = root.findViewById(R.id.costChart);

        ArrayList<BarEntry> cost = new ArrayList<>();
        cost.add(new BarEntry(1, 100));
        cost.add(new BarEntry(2, 400));
        cost.add(new BarEntry(3, 200));
        cost.add(new BarEntry(4, 300));
        cost.add(new BarEntry(5, 500));

        BarDataSet barDataSet2 = new BarDataSet(cost, "Chi phí");
        barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet2.setValueTextColor(Color.BLACK);
        barDataSet2.setValueTextSize(10f);

        BarData barData2 = new BarData(barDataSet2);

        costChart.setFitBars(true);
        costChart.setData(barData2);
        costChart.getDescription().setText("Cost Chart");
        costChart.animateY(2000);


        BarChart profitChart = root.findViewById(R.id.profitChart);

        ArrayList<BarEntry> profit = new ArrayList<>();
        profit.add(new BarEntry(1, 100));
        profit.add(new BarEntry(2, 400));
        profit.add(new BarEntry(3, 200));
        profit.add(new BarEntry(4, 300));
        profit.add(new BarEntry(5, 500));

        BarDataSet barDataSet3 = new BarDataSet(profit, "Lợi nhuận");
        barDataSet3.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet3.setValueTextColor(Color.BLACK);
        barDataSet3.setValueTextSize(10f);
        BarData barData3 = new BarData(barDataSet3);
        profitChart.setFitBars(true);
        profitChart.setData(barData3);
        profitChart.getDescription().setText("Profit Chart");
        profitChart.animateY(2000);
        return root;
    }
}