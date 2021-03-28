package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listemploy;
    private Button btnthem,btnxoa,btnsua,btn;
    private String tem1;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnthem=(Button)findViewById(R.id.btn_them);
        btnxoa=(Button)findViewById(R.id.btn_xoa);
        btnsua=(Button)findViewById(R.id.btn_sua);
        listemploy=findViewById(R.id.listview);
        searchView=findViewById(R.id.search_bar);
        employee e1=new employee("1","ada lovelace","December 10,1815");
        employee e2=new employee("2","ada lovelace","December 10,1815");
        employee e3=new employee("3","ada lovelace","December 10,1815");
        employee e4=new employee("4","ada lovelace","December 10,1815");
        ArrayList<employee> employees=new ArrayList<>();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employeeAdapter adapter =new employeeAdapter(this,R.layout.row,employees);
        listemploy.setAdapter(adapter);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_them){
                    Toast.makeText(MainActivity.this,"heel",Toast.LENGTH_SHORT).show();
                    Dialog dialog=new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog);
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
                            employee temp=new employee(stt,name,type);
                            employees.add(temp);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        


    }



}