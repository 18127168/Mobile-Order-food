package com.example.masterchef.ui.manager.discount;
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

import com.example.masterchef.R;

import java.util.ArrayList;
import java.util.List;

public class ManagediscountFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListView listemploy;
    private Button btnthem,btnxoa,btnsua,btn;
    private String tem1;
    SearchView searchView;
    private RecyclerView recyclerView1;
    private discount_adapter dis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_discount, container, false);
        recyclerView1=root.findViewById(R.id.discount_rcv);
        setRecyclerView();
        return root;
    }
    private void setRecyclerView(){
        //recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        dis=new discount_adapter(getActivity(),getList());
        recyclerView1.setAdapter(dis);

    }
    private List<discount> getList(){
        List<discount> discounts=new ArrayList<>();
        discounts.add(new discount("1","Thu2","10/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("2","Thu3","1/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("3","Thu4","10/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("4","Thu5","10/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("5","Thu6","10/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("6","Thu7","10/10/2020-20/10/2020","Giam 10"));
        discounts.add(new discount("7","Chunhat","10/10/2020-20/10/2020","Giam 10"));
        return discounts;
    }
}