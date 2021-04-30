package com.example.masterchef.ui.manager.Managemenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adapter_weekdays extends RecyclerView.Adapter<Adapter_weekdays.Menu_categoricalViewholder> {
    private Context mcontext;
    private ArrayList<String> list;
    DatabaseWork databaseWork = new DatabaseWork();

    public Adapter_weekdays(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Adapter_weekdays.Menu_categoricalViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_adapter_managemenu1,parent,false);

        return new Adapter_weekdays.Menu_categoricalViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_weekdays.Menu_categoricalViewholder holder, int position) {
        List<Integer> listIDFoodInMenu;

        if (position == 6) {
            listIDFoodInMenu = databaseWork.GetFoodInMenu(1);
            holder.textView.setText("Thực đơn Chủ Nhật");
        }
        else {
            listIDFoodInMenu = databaseWork.GetFoodInMenu(position + 2);
            holder.textView.setText("Thực đơn Thứ " + (position + 2));//thuc don thu 2
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userQuery = dataref.child("Food");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridLayoutManager gridLayoutManager=new GridLayoutManager(mcontext,2);

                Adapter_menu adaptermenu =new Adapter_menu(mcontext, listIDFoodInMenu);
                holder.recyclerView.setLayoutManager(gridLayoutManager);
                holder.recyclerView.setAdapter(adaptermenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class Menu_categoricalViewholder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;

        public Menu_categoricalViewholder(@NonNull View itemView) {
            super(itemView);
            recyclerView= itemView.findViewById(R.id.rev_menu);
            textView=itemView.findViewById(R.id.name_category_tv);
        }
    }
}
