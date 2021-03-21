package com.example.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    EditText user,pass;
    Button btnlogin,btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("User");
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

        btnlogin = (Button) findViewById(R.id.button2);
        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Query userQuery = dataref.child("username").orderByChild("user").equalTo(user.getText().toString());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            boolean checklogin = false;
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                User usersnapshot = snapshot.getValue(User.class);
                                if(usersnapshot.pass.equals(pass.getText().toString())) {
                                    Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, AccessEmployee.class);
                                    startActivity(intent);
                                    checklogin = true;
                                }
                            }
                            if(checklogin == false)
                                Toast.makeText(MainActivity.this,"Password wrong!!!",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"User wrong!!!",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}