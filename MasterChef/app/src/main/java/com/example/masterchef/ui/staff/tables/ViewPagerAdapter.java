package com.example.masterchef.ui.staff.tables;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int table_id;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, int table_id) {
        super(fm, behavior);
        this.table_id = table_id;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        OptionFragment optionFragment = OptionFragment.instantiate();
//        Bundle bundle = optionFragment.getArguments();
//        int table_id = bundle.getInt("table_id");
//        bundle.putInt("table_id", table_id);
        switch (position) {
            case 0:
//                OrderFragment orderFragment = new OrderFragment();
//                orderFragment.setArguments(bundle);
//                return orderFragment;
                return new OrderFragment(table_id);
            case 1:
//                StatusFragment statusFragment = new StatusFragment();
//                statusFragment.setArguments(bundle);
//                return statusFragment;
                return new StatusFragment(table_id);
            case 2:
//                SwichFragment swichFragment = new SwichFragment();
//                swichFragment.setArguments(bundle);
//                return swichFragment;
                return new SwitchTableFragment(table_id);
            default:
//                OrderFragment orderFragment1 = new OrderFragment();
//                orderFragment1.setArguments(bundle);
//                return orderFragment1;
                return  new OrderFragment(table_id);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Order";
                break;
            case 1:
                title = "Status";
                break;
            case 2:
                title = "Switch table";
                break;
        }
        return title;
    }
}
