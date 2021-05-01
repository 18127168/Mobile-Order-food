package com.example.masterchef.ui.manager.Managemenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MangemenuFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();
    private ListView listemploy;
    private Button btnthem, btnxoa, btnsua, btn;
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
        categoryAdapterWeekdays.setData(getListcategory());
        recyclerView.setAdapter(categoryAdapterWeekdays);
        return root;
    }
    private List<category> getListcategory(){ //Menu_category
        List<category> list=new ArrayList<>();
        List<Menu> listmenu=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        listIDFoodInMenu = databaseWork.GetFoodInMenu(day);
        
        listmenu.add(new Menu(R.drawable.food1,"Món ăn 1"));
        listmenu.add(new Menu(R.drawable.food2,"Món ăn 2"));
        listmenu.add(new Menu(R.drawable.food3,"Món ăn 3"));
        listmenu.add(new Menu(R.drawable.food4,"Món ăn 4"));

        list.add(new category("thứ 2",listmenu));
        list.add(new category("thứ 3",listmenu));
        list.add(new category("thứ 4",listmenu));
        list.add(new category("thứ 5",listmenu));
        return list;
    }
}