package com.example.masterchef.ui.Chef;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.masterchef.ChefActivity;
import com.example.masterchef.HoaDon;
import com.example.masterchef.MainActivity;
import com.example.masterchef.R;
import com.example.masterchef.ui.staff.serve.ServeAdapter;
import com.example.masterchef.ui.staff.serve.ServeViewModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static com.example.masterchef.R.layout.customer_adapter_chef_hoadon;
import static com.example.masterchef.R.layout.fragment_listhoadon;

public class FragmentChef extends Fragment {
    FirebaseListAdapter<HoaDon> adapter;
    Query query;
    public ListView listView;
    View compactactivity;
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(fragment_listhoadon, container, false);
        compactactivity = root;
        listView = (ListView) root.findViewById(R.id.listChefFood);
        query = FirebaseDatabase.getInstance().getReference(MainActivity.server.getText().toString()).child("HoaDon").orderByChild("HoaDonSo");
        adapter = new FirebaseListAdapter<HoaDon>(getActivity(), HoaDon.class, R.layout.customer_adapter_chef_hoadon,query) {
            @Override
            protected void populateView(View v, HoaDon model, int position) {

                ViewHolder holder;
                holder = new ViewHolder();
                holder.BanView = (TextView) v.findViewById(R.id.banViewHoaDonAdapter);
                holder.HoaDonSoView = (TextView) v.findViewById(R.id.hoadonsoViewHoaDonAdapter);
                holder.TinhtrangView = (TextView) v.findViewById(R.id.trangthaiViewHoaDonAdapter);
                v.setTag(holder);
                holder.HoaDonSoView.setText("Hóa Đơn Số : "+model.getHoaDonSo());
                holder.BanView.setText("Ban: " + model.getTable());
                holder.TinhtrangView.setText("Tinh trang: " + model.getTrangthai());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragmentContent(new FragMentCacMonAnTrongHoaDon(model),root.getContext());
                    }
                });
        /*  Query userQuery = dataref.child("Food").orderByChild("ID").equalTo(model.getID());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            Food foodCheckTheoIDCuaHoaDon;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                foodCheckTheoIDCuaHoaDon = snapshot.getValue(Food.class);
                                ViewHolder holder;
                                holder = new ViewHolder();
                                holder.BanView = (TextView) v.findViewById(R.id.banViewHoaDonAdapter);
                                holder.HoaDonSoView = (TextView) v.findViewById(R.id.hoadonsoViewHoaDonAdapter);
                                holder.TinhtrangView = (TextView) v.findViewById(R.id.trangthaiViewHoaDonAdapter);

                                v.setTag(holder);
                                holder.HoaDonSoView.setText(foodCheckTheoIDCuaHoaDon.getTenmon());
                                holder.BanView.setText("Ban: " + model.getTable());
                                holder.TinhtrangView.setText("Tinh trang: " + model.getTrangthai());

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/
            }

        };
        listView.setAdapter(adapter);
        return root;
    }
    protected void replaceFragmentContent(Fragment fragment,Context context) {
        if (fragment != null) {
            FragmentManager fmgr = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction ft = fmgr.beginTransaction();
            ft.replace(R.id.fragment_list, fragment);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    static class ViewHolder {
        TextView BanView;
        TextView HoaDonSoView;
        TextView TinhtrangView;
    }
}
