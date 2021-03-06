package com.example.masterchef.ui.manager.Managestaff;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.R;
import com.example.masterchef.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MangestaffFragment extends Fragment {

    private ListView listemploy;
    private ImageView img_them;
    private ImageView img_edit,img_delete;
    private String tem1;
    private  int stt=0;
    private boolean flag=true;
    SearchView searchView;
    public Adapter adapter;
    DatabaseWork databaseWork=new DatabaseWork();
    ArrayList<User> staffArrayList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_managestaff, container, false);
        img_them=(ImageView)root.findViewById(R.id.btn_them);
        img_delete=(ImageView)root.findViewById(R.id.btn_delete);
        img_edit=(ImageView)root.findViewById(R.id.btn_edit);
        listemploy=root.findViewById(R.id.manager_staff_lv);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userquery=dataref.child("username").orderByChild("loai");
        //t n??o
        adapter =new Adapter(getActivity(),R.layout.manager_adapter_liststaff, staffArrayList);
        listemploy.setAdapter(adapter);
        listemploy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(staffArrayList.get(position).getLoai().equals("NhanVien")) {
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.manage_employee_dialogshow);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    TextView cmnd = (TextView) dialog.findViewById(R.id.text2);
                    TextView dantoc = (TextView) dialog.findViewById(R.id.text3);
                    TextView diachi = (TextView) dialog.findViewById(R.id.text4);
                    TextView hoten = (TextView) dialog.findViewById(R.id.text5);
                    TextView loai = (TextView) dialog.findViewById(R.id.text6);
                    TextView ngaysinh = (TextView) dialog.findViewById(R.id.text7);
                    TextView ngayvao = (TextView) dialog.findViewById(R.id.text8);
                    TextView pass = (TextView) dialog.findViewById(R.id.text9);
                    TextView sdt = (TextView) dialog.findViewById(R.id.text10);
                    TextView user = (TextView) dialog.findViewById(R.id.text11);
                    Button btnhuy = (Button) dialog.findViewById(R.id.btnh);
                    User user1 = staffArrayList.get(position);
                    cmnd.setText("CMND: " + user1.getCmnd().toString());
                    dantoc.setText("Dan toc: " + user1.getDantoc().toString());
                    diachi.setText("dia chi: " + user1.getDiachi().toString());
                    hoten.setText("ho ten: " + user1.getHoten().toString());
                    loai.setText("loai: " + user1.getLoai().toString());
                    ngaysinh.setText("ngay sinh: " + user1.getNgaysinh().toString());
                    ngayvao.setText("ngay vao: " + user1.getNgayvao().toString());
                    sdt.setText("sdt: " + user1.getSdt().toString());
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                else if(staffArrayList.get(position).getLoai().equals("KhachHang")){
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.manage_table_dialog_show);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    TextView soban = (TextView) dialog.findViewById(R.id.SoBanTable);
                    TextView seats = (TextView) dialog.findViewById(R.id.SeatTable);
                    TextView loai = (TextView) dialog.findViewById(R.id.loaiTable);
                    TextView pass = (TextView) dialog.findViewById(R.id.passTable);
                    TextView user = (TextView) dialog.findViewById(R.id.usernameTable);
                    Button btnhuy = (Button) dialog.findViewById(R.id.btnh);
                    User user1 = staffArrayList.get(position);
                    loai.setText("Lo???i: " + user1.getLoai());
                    soban.setText("B??n: "+ user1.getBan());
                    seats.setText("S??? ch??? ng???i: "+user1.getSeats());
                    user.setText("User: " + user1.getUser());
                    pass.setText("Pass: "+user1.getPass());
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        img_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_them){
                    //Toast.makeText(getActivity(),"heel",Toast.LENGTH_SHORT).show();
                    Dialog dialog=new Dialog(getActivity());
                    ChangeDialogFromTableToEmp(dialog);

                }
            }
        });
        userquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User e = snapshot.getValue(User.class);
                    staffArrayList.add(e);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
    public void ChangeDialogFromEmployToTable(Dialog dialog){
        dialog.setContentView(R.layout.manage_table_dialog_add);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView addEmp = (TextView) dialog.findViewById(R.id.addNhanVien);
        Button btnhuy=(Button) dialog.findViewById(R.id.btnh_table);
        Button btndongy=(Button) dialog.findViewById(R.id.btnd_table);
        EditText pass=(EditText)dialog.findViewById(R.id.passAddTable);
        EditText user=(EditText)dialog.findViewById(R.id.userAddTable);
        EditText seats = (EditText) dialog.findViewById(R.id.soChoNgoiAddTable);
        EditText tableid = (EditText) dialog.findViewById(R.id.SobanAddTable);
        addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ChangeDialogFromTableToEmp(dialog);
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText Stt=(EditText)dialog.findViewById(R.id.text1);

                int Seat= Integer.parseInt( seats.getText().toString().trim());
                int tableID=Integer.parseInt(tableid.getText().toString().trim());
                String Pass=pass.getText().toString().trim();
                String User=user.getText().toString().trim();
                //DatabaseReference UserDbref=dataref.child("username");
                DatabaseReference UserDbref=FirebaseDatabase.getInstance().getReference().child("User").child("username");
                User e=new User(User,Pass,tableID,Seat);
                UserDbref.push().setValue(e);
                staffArrayList.add(e);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
    public void ChangeDialogFromTableToEmp(Dialog dialog){
        dialog.setContentView(R.layout.manager_employee_dialogadd);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView addTable = (TextView) dialog.findViewById(R.id.AddBan);
        Button btnhuy=(Button) dialog.findViewById(R.id.btnh);
        Button btndongy=(Button) dialog.findViewById(R.id.btnd);
        EditText cmnd=(EditText)dialog.findViewById(R.id.text2);
        EditText dantoc=(EditText)dialog.findViewById(R.id.text3);
        EditText diachi=(EditText)dialog.findViewById(R.id.text4);
        EditText hoten=(EditText)dialog.findViewById(R.id.text5);
        EditText ngaysinh=(EditText)dialog.findViewById(R.id.text7);
        EditText ngayvao=(EditText)dialog.findViewById(R.id.text8);
        EditText pass=(EditText)dialog.findViewById(R.id.text9);
        EditText sdt=(EditText)dialog.findViewById(R.id.text10);
        EditText user=(EditText)dialog.findViewById(R.id.text11);
        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDialogFromEmployToTable(dialog);
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText Stt=(EditText)dialog.findViewById(R.id.text1);

                String Cmnd=cmnd.getText().toString().trim();
                String Dantoc=dantoc.getText().toString().trim();
                String Diachi=diachi.getText().toString().trim();
                String Hoten=hoten.getText().toString().trim();
                String Ngaysinh=ngaysinh.getText().toString().trim();
                String Ngayvao=ngayvao.getText().toString().trim();
                String Pass=pass.getText().toString().trim();
                String Std=sdt.getText().toString().trim();
                String User=user.getText().toString().trim();
                //DatabaseReference UserDbref=dataref.child("username");
                DatabaseReference UserDbref=FirebaseDatabase.getInstance().getReference().child("User").child("username");
                User e=new User(User,Pass,Hoten,Ngaysinh,Cmnd,Std,"NhanVien",Dantoc,Ngayvao,Diachi,-1,-1);
                UserDbref.push().setValue(e);
                staffArrayList.add(e);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }
}