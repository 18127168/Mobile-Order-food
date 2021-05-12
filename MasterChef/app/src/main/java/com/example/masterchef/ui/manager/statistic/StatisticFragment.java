package com.example.masterchef.ui.manager.statistic;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.masterchef.MainActivity.server;

public class StatisticFragment extends Fragment {
    final long DonViTrieuVND = 1000000;
    final Calendar myCalendar = Calendar.getInstance();
    EditText to;
    BarChart revenueChart,costChart, profitChart;
    ArrayList<BarEntry> revenue = new ArrayList<>();
    ArrayList<BarEntry> cost = new ArrayList<>();
    ArrayList<BarEntry> profit = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.manager_fragment_statistic, container, false);
        revenueChart = root.findViewById(R.id.revenueChart);
        costChart = root.findViewById(R.id.costChart);
        profitChart = root.findViewById(R.id.profitChart);

        to = root.findViewById(R.id.Today);

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String formatedDate = "";
                        String currentDateFormat =""+  selectedYear;  //"MM/yyyy"
                        to.setText(currentDateFormat);
                        updateQuery();
                    }
                });
                pd.show(getFragmentManager(), "MonthYearPickerDialog");

            }
        });
        updateQuery();
        return root;
    }
    public void updateQuery(){
        Query userQuery = FirebaseDatabase.getInstance().getReference(server.getText().toString()).child("ThongKe").orderByKey();
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<Integer> check12Month = new ArrayList<>();
                    cost.clear();
                    revenue.clear();
                    profit.clear();
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        String key = postSnapshot.getKey();
                        String month = ""+key.charAt(0) + key.charAt(1);
                        String year = ""+key.charAt(2) + key.charAt(3)+key.charAt(4) + key.charAt(5);
                        if(year.equals(to.getText().toString())){
                            check12Month.add(Integer.parseInt(month));
                            ThongKe value = postSnapshot.getValue(ThongKe.class);
                            cost.add(new BarEntry(Integer.parseInt(month),value.Chiphi /DonViTrieuVND));
                            revenue.add(new BarEntry(Integer.parseInt(month),value.DoanhThu/DonViTrieuVND));
                            profit.add(new BarEntry(Integer.parseInt(month),(value.DoanhThu-value.Chiphi)/DonViTrieuVND));
                        };
                    }
                    for(int i=1;i<=12;i++){
                        if(!check12Month.contains(i)){
                            cost.add(new BarEntry(i,0 ));
                            revenue.add(new BarEntry(i,0));
                            profit.add(new BarEntry(i,0));
                        }
                    }
                    profitChart.notifyDataSetChanged();
                    costChart.notifyDataSetChanged();
                    revenueChart.notifyDataSetChanged();
                    updateChart();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateChart(){
        BarDataSet barDataSet = new BarDataSet(revenue, "Doanh thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        revenueChart.setFitBars(true);
        revenueChart.setData(barData);
        revenueChart.getDescription().setText("Đơn vị: Triệu VND");
        revenueChart.getDescription().setTextSize(15);
        revenueChart.animateY(2000);

        BarDataSet barDataSet2 = new BarDataSet(cost, "Chi phí");
        barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet2.setValueTextColor(Color.BLACK);
        barDataSet2.setValueTextSize(10f);

        BarData barData2 = new BarData(barDataSet2);

        costChart.setFitBars(true);
        costChart.setData(barData2);
        costChart.getDescription().setText("Đơn vị: Triệu VND");
        costChart.getDescription().setTextSize(15);
        costChart.animateY(2000);

        BarDataSet barDataSet3 = new BarDataSet(profit, "Lợi nhuận");
        barDataSet3.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet3.setValueTextColor(Color.BLACK);
        barDataSet3.setValueTextSize(10f);
        BarData barData3 = new BarData(barDataSet3);
        profitChart.setFitBars(true);
        profitChart.setData(barData3);
        profitChart.getDescription().setText("Đơn vị: Triệu VND");
        profitChart.getDescription().setTextSize(15);
        profitChart.animateY(2000);
    }
}