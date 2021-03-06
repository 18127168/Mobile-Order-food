package com.example.masterchef.ui.Chef;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.masterchef.HoaDon;
import com.example.masterchef.MainActivity;
import com.example.masterchef.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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

        query = FirebaseDatabase.getInstance().getReference(MainActivity.server.getText().toString()).child("HoaDon").orderByChild("trangthai").equalTo(0);
        adapter = new FirebaseListAdapter<HoaDon>(getActivity(), HoaDon.class, R.layout.customer_adapter_chef_hoadon,query) {
            @Override
            protected void populateView(View v, HoaDon model, int position) {

                ViewHolder holder;
                holder = new ViewHolder();
                holder.BanView = (TextView) v.findViewById(R.id.banViewHoaDonAdapter);
                holder.HoaDonSoView = (TextView) v.findViewById(R.id.hoadonsoViewHoaDonAdapter);
                holder.TinhtrangView = (TextView) v.findViewById(R.id.trangthaiViewHoaDonAdapter);
                v.setTag(holder);
                holder.HoaDonSoView.setText("H??a ????n S??? : "+model.getHoaDonSo());
                holder.BanView.setText("Ban: " + model.getTable());
                String trangthai;
                if(model.getTrangthai() == 0) trangthai = "???? G???i";
                else trangthai = "???? Ho??n Th??nh";
                holder.TinhtrangView.setText("Tinh trang: " + trangthai);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragmentContent(new FragMentCacMonAnTrongHoaDon(model,adapter.getRef(position)),root.getContext());
                    }
                });
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
