package com.example.masterchef.ui.Chef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.google.firebase.database.DatabaseReference;
import static com.example.masterchef.R.layout.fragment_cac_mon_an_trong_hoa_don;

public class FragMentCacMonAnTrongHoaDon extends Fragment {
    public ListView listView;
    public TextView ban,date;
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
        date = (TextView) root.findViewById(R.id.ngay);
        ban.setText("Bàn: "+data.getTable());
        date.setText("Ngày: " + data.getDate());
        String[] listIDMonAn = data.getID().split(",");
        String[] listSoLuong = data.getSoLuong().split(",");
        String[] listHoanThanh = data.getHoanthanh().split(",");
        String[] listNotes = data.getNotes().split(";");
        listView.setAdapter(new AdapterHienCacMonAnTrongHoaDon(getActivity(),listIDMonAn,listSoLuong,listHoanThanh,listNotes,refCurrent));
        return root;
    }
}
