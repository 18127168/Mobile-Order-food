package com.example.masterchef.ui.manager.Managestaff;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.masterchef.MainActivity;
import com.example.masterchef.R;
import com.example.masterchef.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends ArrayAdapter<User> {
    private Context mcontext;
    int mresorce;
    ArrayList<User> userArrayList=new ArrayList<>();
    public Adapter(Context context, int resource, ArrayList<User> object){
        super(context,resource,object);
        mcontext=context;
        this.mresorce=resource;
        this.userArrayList=object;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //int stt=getItem(position).getIdNhanVien();//ko lay dc I
        String name=getItem(position).getHoten();
        String address=getItem(position).getDiachi();
        String card=getItem(position).getCmnd();
        String dan_toc=getItem(position).getDantoc();
        String ngay_sinh=getItem(position).getNgaysinh();
        String ngay_vao=getItem(position).getNgayvao();
        String sdt_=getItem(position).getSdt();
        String user_=getItem(position).getUser();
        String pass_=getItem(position).getPass();

        int seat_=getItem(position).getSeats();
        int ban_= getItem(position).getBan();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User").child("username");
        String type=getItem(position).getLoai();
        ImageView img_delete,img_edit;
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresorce,parent,false);
        img_delete=(ImageView)convertView.findViewById(R.id.btn_delete);
        img_edit=(ImageView)convertView.findViewById(R.id.btn_edit);
        TextView Stt=(TextView)convertView.findViewById(R.id.stt);

        TextView tvname=(TextView)convertView.findViewById(R.id.name);
        TextView tvtype=(TextView)convertView.findViewById(R.id.nv);
        img_edit=(ImageView)convertView.findViewById(R.id.btn_edit) ;
        Stt.setText(String.valueOf(position));
        if(type.equals("NhanVien"))
            tvname.setText(name);
        else if(type.equals("KhachHang"))
            tvname.setText("Bàn "+ban_);
        else tvname.setText(user_);
        tvtype.setText(type);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSnapshot snapshot;
                User user1=userArrayList.remove(position);
                Query query=FirebaseDatabase.getInstance().getReference("User").child("username").orderByChild("user").equalTo(user_);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Adapter.this.notifyDataSetChanged();
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("NhanVien")){
                    Dialog dialog=new Dialog(mcontext);
                    dialog.setContentView(R.layout.manager_employee_dialogedit);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Button btnhuy=(Button) dialog.findViewById(R.id.dialog_delete);
                    Button btndongy=(Button) dialog.findViewById(R.id.dialog_add);
                    EditText cmnd=(EditText)dialog.findViewById(R.id.cmndedit);
                    EditText dantoc=(EditText)dialog.findViewById(R.id.dantocedit);
                    EditText diachi=(EditText)dialog.findViewById(R.id.diachiedit);
                    EditText hoten=(EditText)dialog.findViewById(R.id.hotenedit);
                    EditText loai=(EditText)dialog.findViewById(R.id.loaiedit);
                    EditText ngaysinh=(EditText)dialog.findViewById(R.id.ngaysinhedit);
                    EditText ngayvao=(EditText)dialog.findViewById(R.id.ngayvaoedit);
                    EditText pass=(EditText)dialog.findViewById(R.id.matkhauedit);
                    EditText sdt=(EditText)dialog.findViewById(R.id.sdtedit);
                    EditText user=(EditText)dialog.findViewById(R.id.taikhoanedit);
                    cmnd.setText(card);
                    dantoc.setText(dan_toc);
                    diachi.setText(address);
                    hoten.setText(name);
                    loai.setText(type);
                    ngaysinh.setText(ngay_sinh);
                    ngayvao.setText(ngay_vao);
                    pass.setText(pass_);
                    sdt.setText(sdt_);
                    user.setText(user_);
                    loai.setEnabled(true);
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btndongy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String B=ban.getText().toString().trim();
                            //int Ban=Integer.parseInt(B);
                            String Cmnd=cmnd.getText().toString().trim();
                            String Dantoc=dantoc.getText().toString().trim();
                            String Diachi=diachi.getText().toString().trim();
                            String Hoten=hoten.getText().toString().trim();
                            String Loai=loai.getText().toString().trim();
                            String Ngaysinh=ngaysinh.getText().toString().trim();
                            String Ngayvao=ngayvao.getText().toString().trim();
                            String Pass=pass.getText().toString().trim();
                            String Std=sdt.getText().toString().trim();
                            String User=user.getText().toString().trim();
                            HashMap hashMap=new HashMap();
                            //hashMap.put("ban",Ban);
                            hashMap.put("cmnd",Cmnd);
                            hashMap.put("dantoc",Dantoc);
                            hashMap.put("diachi",Diachi);
                            hashMap.put("hoten",Hoten);
                            hashMap.put("loai",Loai);
                            hashMap.put("ngaysinh",Ngaysinh);
                            hashMap.put("ngayvao",Ngayvao);
                            hashMap.put("pass",Pass);
                            hashMap.put("sdt",Std);
                            hashMap.put("user",User);

                            userArrayList.get(position).setHoten(Hoten);
                            userArrayList.get(position).setLoai(Loai);

                            //User e=new User(User,Pass,Hoten,Ngaysinh,Cmnd,Std,Loai,Dantoc,Ngayvao,Diachi,Ban);
                            //userArrayList.remove(position);
                            //userArrayList.add(e);
                            Query query=FirebaseDatabase.getInstance().getReference("User").child("username").orderByChild("hoten").equalTo(name);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                                        dataSnapshot1.getRef().updateChildren(hashMap);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Adapter.this.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Change Success!!!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
                else if(type.equals("QuanLy") || type.equals("DauBep")){
                    Dialog dialog=new Dialog(mcontext);
                    dialog.setContentView(R.layout.manager_edit_chef_edit_manage);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Button btnhuy=(Button) dialog.findViewById(R.id.dialog_delete_ChefManage);
                    Button btndongy=(Button) dialog.findViewById(R.id.dialog_add_ChefManage);
                    EditText pass=(EditText)dialog.findViewById(R.id.matkhauChefManageEdit);
                    EditText user=(EditText)dialog.findViewById(R.id.taikhoanChefManageEdit);
                    pass.setText(pass_);
                    user.setText(user_);
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btndongy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String B=ban.getText().toString().trim();
                            String Pass=pass.getText().toString().trim();
                            String User=user.getText().toString().trim();
                            HashMap hashMap=new HashMap();
                            //hashMap.put("ban",Ban);
                            hashMap.put("pass",Pass);
                            hashMap.put("user",User);

                            userArrayList.get(position).setUser(User);
                            userArrayList.get(position).setPass(Pass);
                            Query query=FirebaseDatabase.getInstance().getReference("User").child("username").orderByChild("loai").equalTo(type);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                                        dataSnapshot1.getRef().updateChildren(hashMap);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Adapter.this.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Change Success!!!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
                else {
                    Dialog dialog=new Dialog(mcontext);
                    dialog.setContentView(R.layout.manage_table_dialog_edit);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Button btnhuy=(Button) dialog.findViewById(R.id.dialog_delete_Table);
                    Button btndongy=(Button) dialog.findViewById(R.id.dialog_add_Table);
                    EditText pass=(EditText)dialog.findViewById(R.id.passKHedit);
                    EditText user=(EditText)dialog.findViewById(R.id.usernameKHedit);
                    EditText seat=(EditText)dialog.findViewById(R.id.SeatKHedit);
                    EditText ban=(EditText)dialog.findViewById(R.id.BanKHedit);
                    pass.setText(pass_);
                    user.setText(user_);
                    seat.setText(""+seat_);
                    ban.setText(""+ban_);
                    btnhuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btndongy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String B=ban.getText().toString().trim();
                            String Pass=pass.getText().toString().trim();
                            String User=user.getText().toString().trim();
                            String Seat=seat.getText().toString().trim();
                            String Ban=ban.getText().toString().trim();
                            HashMap hashMap=new HashMap();
                            //hashMap.put("ban",Ban);
                            hashMap.put("pass",Pass);
                            hashMap.put("user",User);

                            hashMap.put("seats",Integer.parseInt(Seat));
                            hashMap.put("ban",Integer.parseInt(Ban));

                            userArrayList.get(position).setPass(Pass);
                            userArrayList.get(position).setUser(User);
                            userArrayList.get(position).setSeats(Integer.parseInt(Seat));
                            userArrayList.get(position).setBan(Integer.parseInt(Ban));

                            Query query=FirebaseDatabase.getInstance().getReference("User").child("username").orderByChild("user").equalTo(user_);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                                        dataSnapshot1.getRef().updateChildren(hashMap);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Adapter.this.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Change Success!!!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
            }

        });

        return convertView;
    }
}
