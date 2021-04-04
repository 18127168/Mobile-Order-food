package com.example.masterchef.ui.manager.Managestaff;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.masterchef.R;

import java.util.ArrayList;

public class MangestaffFragment extends Fragment {

    private ListView listemploy;
    private Button btnthem,btnxoa,btnsua,btn;
    private String tem1;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_managestaff, container, false);
        btnthem=(Button)root.findViewById(R.id.btn_them);
        listemploy=root.findViewById(R.id.manager_staff_lv);
        //searchView=findViewById(R.id.search_barr);
        staff e1=new staff("1","ada lovelace","December 10,1815");
        staff e2=new staff("2","ada lovelace","December 10,1815");
        staff e3=new staff("3","ada lovelace","December 10,1815");
        staff e4=new staff("4","ada lovelace","December 10,1815");
        ArrayList<staff> staff =new ArrayList<>();
        staff.add(e1);
        staff.add(e2);
        staff.add(e3);
        staff.add(e4);

        Adapter adapter =new Adapter(getActivity(),R.layout.manager_adapter_liststaff, staff);
        listemploy.setAdapter(adapter);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_them){
                    Toast.makeText(getActivity(),"heel",Toast.LENGTH_SHORT).show();
                    Dialog dialog=new Dialog(getActivity());
                    dialog.setContentView(R.layout.manager_employee_dialogadd);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Button btnhuy=(Button) dialog.findViewById(R.id.btnh);
                    Button btndongy=(Button) dialog.findViewById(R.id.btnd);
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btndongy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText Stt=(EditText)dialog.findViewById(R.id.text1);
                            EditText Name=(EditText)dialog.findViewById(R.id.text2);
                            EditText Type=(EditText)dialog.findViewById(R.id.text3);
                            String stt=Stt.getText().toString().trim();
                            tem1=stt;
                            String name=Name.getText().toString().trim();
                            String type=Type.getText().toString().trim();
                            staff temp=new staff(stt,name,type);
                            staff.add(temp);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        return root;
    }
}