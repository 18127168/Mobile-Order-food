package com.example.masterchef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.masterchef.ui.Chef.FragmentChef;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;


public class
ChefActivity extends AppCompatActivity {

    FirebaseListAdapter<HoaDon> adapter;
    StorageReference storeImage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataref = database.getReference("User");
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        Toolbar toolbar = findViewById(R.id.chef_toolbar);
        setSupportActionBar(toolbar);

        initFragment();
    }

    public void initFragment(){
        FragmentChef Fragmentchef = new FragmentChef();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_list, Fragmentchef);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem exit_action = menu.findItem(R.id.action_logout);

        exit_action.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ChefActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }
}