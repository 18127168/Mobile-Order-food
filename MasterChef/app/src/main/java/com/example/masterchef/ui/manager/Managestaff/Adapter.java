package com.example.masterchef.ui.manager.Managestaff;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        String Type=getItem(position).getLoai();
        String address=getItem(position).getDiachi();
        String card=getItem(position).getCmnd();
        String dan_toc=getItem(position).getDantoc();
        String ngay_sinh=getItem(position).getNgaysinh();
        String ngay_vao=getItem(position).getNgayvao();
        String sdt_=getItem(position).getSdt();
        String user_=getItem(position).getUser();
        String pass_=getItem(position).getPass();
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
        tvname.setText(name);
        tvtype.setText(type);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSnapshot snapshot;
                User user1=userArrayList.remove(position);
                Query query=FirebaseDatabase.getInstance().getReference("User").child("username").orderByChild("hoten").equalTo(user1.getHoten().toString());
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
                Dialog dialog=new Dialog(mcontext);
                dialog.setContentView(R.layout.manager_employee_dialogadd);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btnhuy=(Button) dialog.findViewById(R.id.btnh);
                Button btndongy=(Button) dialog.findViewById(R.id.btnd);
                // EditText Stt=(EditText)dialog.findViewById(R.id.text1);
                EditText ban=(EditText)dialog.findViewById(R.id.text1);
                EditText cmnd=(EditText)dialog.findViewById(R.id.text2);
                EditText dantoc=(EditText)dialog.findViewById(R.id.text3);
                EditText diachi=(EditText)dialog.findViewById(R.id.text4);
                EditText hoten=(EditText)dialog.findViewById(R.id.text5);
                EditText loai=(EditText)dialog.findViewById(R.id.text6);
                EditText ngaysinh=(EditText)dialog.findViewById(R.id.text7);
                EditText ngayvao=(EditText)dialog.findViewById(R.id.text8);
                EditText pass=(EditText)dialog.findViewById(R.id.text9);
                EditText sdt=(EditText)dialog.findViewById(R.id.text10);
                EditText user=(EditText)dialog.findViewById(R.id.text11);
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
                ban.setText(String.valueOf(ban_));
                /*cmnd.setText(userArrayList.get(position).getCmnd());
                dantoc.setText(userArrayList.get(position).getDantoc());
                diachi.setText(userArrayList.get(position).getDiachi());
                loai.setText(userArrayList.get(position).getLoai());
                ngaysinh.setText(userArrayList.get(position).getNgaysinh());
                ngayvao.setText(userArrayList.get(position).getNgayvao());
                pass.setText(userArrayList.get(position).getPass());
                sdt.setText(userArrayList.get(position).getSdt());
                user.setText(userArrayList.get(position).getUser());*/

                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btndongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String B=ban.getText().toString().trim();
                        int Ban=Integer.parseInt(B);
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
                        hashMap.put("ban",Ban);
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

                        dialog.dismiss();
                    }
                });
            }
        });

        return convertView;
    }
}
