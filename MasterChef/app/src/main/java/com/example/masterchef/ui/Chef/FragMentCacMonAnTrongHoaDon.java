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

import com.example.masterchef.ChefActivity;
import com.example.masterchef.HoaDon;
import com.example.masterchef.MainActivity;
import com.example.masterchef.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.masterchef.R.layout.fragment_cac_mon_an_trong_hoa_don;
import static com.example.masterchef.R.layout.fragment_listhoadon;

public class FragMentCacMonAnTrongHoaDon extends Fragment {
        public ListView listView;
        public TextView ban,tinhtrang;
        public DatabaseReference refCurrent;
        View compactactivity;
        public HoaDon data;
        public FragMentCacMonAnTrongHoaDon(HoaDon model, DatabaseReference linkofHoaDon){
            this.data=model;
            this.refCurrent=linkofHoaDon;
        }
        public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
            View root =  inflater.inflate(fragment_cac_mon_an_trong_hoa_don, container, false);
            compactactivity = root;
            listView = (ListView) root.findViewById(R.id.list_cac_mon_an_cua_1_ban);
            ban = (TextView) root.findViewById(R.id.Ban);
            tinhtrang = (TextView) root.findViewById(R.id.tinhtrang);

            ban.setText("Bàn: "+data.getTable());
            String trangthai;
            if(data.getTrangthai() == 0) trangthai = "Đã Gọi";
            else trangthai = "Đã Hoàn Thành";
            tinhtrang.setText("Tinh trang: " + trangthai);
            String[] listIDMonAn = data.getID().split(",");
            String[] listSoLuong = data.getSoLuong().split(",");
            String[] listHoanThanh = data.getHoanthanh().split(",");
            listView.setAdapter(new AdapterHienCacMonAnTrongHoaDon(getActivity(),listIDMonAn,listSoLuong,listHoanThanh,refCurrent));
            return root;
        }

        static class ViewHolder {
            TextView BanView;
            TextView HoaDonSoView;
            TextView TinhtrangView;
        }
    }


