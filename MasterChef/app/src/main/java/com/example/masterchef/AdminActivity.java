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

public class AdminActivity extends AppCompatActivity {
    EditText user,pass,server;
    Button btnsignup,btnlogout;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        server =  (EditText) findViewById(R.id.Server);
        user = (EditText) findViewById(R.id.userSignUp);
        pass = (EditText) findViewById(R.id.passSignup);
        btnsignup = (Button) findViewById(R.id.buttosignup  );
        btnsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (user.getText().toString().equals("")){
                    Toast.makeText(AdminActivity.this,"Please enter username",Toast.LENGTH_LONG).show();
                }
                else if (pass.getText().toString().equals("")){
                    Toast.makeText(AdminActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                }
                else if (server.getText().toString().equals("")){
                    Toast.makeText(AdminActivity.this,"Please enter Server",Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dataref = database.getReference();
                    Query userQuery = dataref.orderByKey().equalTo(server.getText().toString());
                    userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(AdminActivity.this,"Server exists",Toast.LENGTH_LONG).show();
                            }
                            else{

                                String a= user.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference dataref = database.getReference(server.getText().toString()).child("username");
                                Query userQuery = dataref.orderByChild("user").equalTo(user.getText().toString());
                                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(AdminActivity.this,"Account exists",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            String userId = dataref.push().getKey();
                                            User usersigup = new User(user.getText().toString(), pass.getText().toString());
                                            dataref.child(userId).setValue(usersigup);
                                            Toast.makeText(AdminActivity.this,"Sigup Success with user:" + user.getText().toString() + " pass : "+pass.getText().toString(),Toast.LENGTH_LONG).show();
                                            user.setText("");
                                            pass.setText("");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                }
            }
        });
        btnlogout = (Button) findViewById(R.id.buttologout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AdminActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
            }
        });
    }
}