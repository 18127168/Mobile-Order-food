package com.example.masterchef.ui.manager.Managemenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MangemenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();
    Adapter_menu adapter1;
    private String tem1;
    private Adapter_weekdays categoryAdapterWeekdays;
    StorageReference storeImage;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_managemenu, container, false);
        recyclerView=root.findViewById(R.id.rcv_category);
        categoryAdapterWeekdays =new Adapter_weekdays(getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(categoryAdapterWeekdays);
        return root;
    }
}