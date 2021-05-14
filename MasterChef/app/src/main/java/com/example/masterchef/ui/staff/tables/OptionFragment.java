package com.example.masterchef.ui.staff.tables;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.masterchef.MainActivity.server;

public class OptionFragment extends Fragment{

    TabLayout option_tab;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_tables_option, container, false);
        option_tab = root.findViewById(R.id.option_tab);
        viewPager = root.findViewById(R.id.view_pager);
        AppCompatActivity staff_activity = (AppCompatActivity) root.getContext();

        Bundle bundle = this.getArguments();
        Log.e("ok", bundle.getInt("table_id") + "");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(staff_activity.getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, bundle.getInt("table_id"));

        viewPager.setAdapter(viewPagerAdapter);
        option_tab.setupWithViewPager(viewPager);

        return root;
    }

}