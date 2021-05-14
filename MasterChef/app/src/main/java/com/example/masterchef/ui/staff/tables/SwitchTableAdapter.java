package com.example.masterchef.ui.staff.tables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.example.masterchef.Tables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.server;

public class SwitchTableAdapter extends RecyclerView.Adapter<SwitchTableAdapter.ViewHolder> {

    List<Tables> tables;
    LayoutInflater inflater;
    int table_id;
    Context context;

    public SwitchTableAdapter(Context context, List<Tables> tables, int tb_id){
        this.tables = tables;
        this.inflater = LayoutInflater.from(context);
        this.table_id = tb_id;
        this.context = context;

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getId() == tb_id) {
                tables.remove(i);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_table_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText("TABLE " + tables.get(position).getId());
        holder.seat.setText( "Số chỗ: " + tables.get(position).getSeats());
        holder.switch_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference dataref = database.getReference(server.getText().toString());
                 Query userQuery = dataref.child("HoaDon");
                 userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if (snapshot.exists()) {
                             for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                 if( (postSnapshot.getValue(HoaDon.class).getTable() == table_id) && postSnapshot.getValue(HoaDon.class).getThanhToan() == false){
                                     postSnapshot.getRef().child("table").setValue(tables.get(position).getId());
                                 }
                             }
                         }

                         Toast.makeText(context, "Switch table successfully!", Toast.LENGTH_SHORT).show();
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
        });
    }

    @Override
    public int getItemCount(){
        return tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, seat;
        Button switch_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.switchtable_title);
            seat = itemView.findViewById(R.id.switchtable_seat);
            switch_btn = itemView.findViewById(R.id.switchtable_choose_btn);
        }
    }
}