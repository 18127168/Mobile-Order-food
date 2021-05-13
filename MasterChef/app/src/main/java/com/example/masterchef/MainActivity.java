package com.example.masterchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    public static EditText user,pass,server;
    public static int IDTable;
    public Button btnlogin;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        server = (EditText) findViewById(R.id.serverLogin);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        btnlogin = (Button) findViewById(R.id.button2);
        btnlogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if (user.getText().toString().equals("admin")  && pass.getText().toString().equals("admin")){
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dataref = database.getReference(server.getText().toString());
                    Query userQuery = dataref.child("username").orderByChild("user").equalTo(user.getText().toString());
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                boolean checklogin = false;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    User usersnapshot = snapshot.getValue(User.class);
                                    if (usersnapshot.pass.equals(pass.getText().toString())) {
                                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                        checklogin = true;
                                        if(usersnapshot.loai.equals("QuanLy")){ //thieu manager activity
                                            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
                                            startActivity(intent);
                                        }
                                        else if(usersnapshot.loai.equals("NhanVien")){//doi lai activity nhanvien
                                            Intent intent = new Intent(MainActivity.this, StaffActivity.class);
                                            startActivity(intent);
                                        }
                                        else if(usersnapshot.loai.equals("DauBep")){ // doi lai activity daubep
                                            Intent intent = new Intent(MainActivity.this, ChefActivity.class);
                                            startActivity(intent);
                                        }
                                        else if(usersnapshot.loai.equals("KhachHang")){//doi lai activity khachhang
                                            IDTable = usersnapshot.ban;
                                            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                                if (checklogin == false)
                                    Toast.makeText(MainActivity.this, "Password wrong!!!", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(MainActivity.this, "User wrong!!!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

    }
}