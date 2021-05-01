package com.example.masterchef.ui.customer.switchtable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.masterchef.MainActivity.IDTable;
import static com.example.masterchef.MainActivity.server;

public class SwitchTableAdapter extends RecyclerView.Adapter<SwitchTableAdapter.ViewHolder> {

    List<Tables> tables;
    LayoutInflater inflater;

    public SwitchTableAdapter(Context context, List<Tables> tables){
        this.tables = tables;
        this.inflater = LayoutInflater.from(context);
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
                         int id = 1;
                         if (snapshot.exists()) {
                             for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                 HoaDon hoadon = postSnapshot.getValue(HoaDon.class);
                                 if( (hoadon.getTable() == IDTable) && hoadon.getThanhToan() == false){
                                     postSnapshot.getRef().child("table").setValue(tables.get(position).getId());
                                 }
                                 id++;
                             }
                         }

                         Date c = Calendar.getInstance().getTime();
                         SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                         String formattedDate = df.format(c);

                         Log.e("Date", formattedDate);

                         List<SelectedFood.Food> ListFood = SelectedFood.Foods;
                         String listIDFood = "", listNumFood = "", listNoteFood = "", listNumFinishedFood = "", listNumServedFood = "";
                         for (int i = 0; i < ListFood.size(); i++) {
                             if (i != 0) {
                                 listIDFood += ",";
                                 listNumFood += ",";
                                 listNoteFood += ";";
                                 listNumFinishedFood += ",";
                                 listNumServedFood += ",";
                             }
                             listIDFood += ListFood.get(i).getIdFood();
                             listNumFood += ListFood.get(i).getQuantity();
                             listNoteFood += ListFood.get(i).getNote();
                             listNumFinishedFood += "0";
                             listNumServedFood += "0";
                         }


                         HoaDon hoaDon = new HoaDon();

                         hoaDon.setID(listIDFood);
                         hoaDon.setDate(formattedDate);
                         hoaDon.setSoLuong(listNumFood);
                         hoaDon.setTable(tables.get(position).getId());
                         hoaDon.setNotes(listNoteFood);
                         hoaDon.setHoanthanh(listNumFinishedFood);
                         hoaDon.setHoaDonSo(id);
                         hoaDon.setTrangthai(3);
                         hoaDon.setPhucVu(listNumServedFood);

                         DatabaseReference dtReferenceef = FirebaseDatabase.getInstance().getReference().child("User").child("HoaDon");
                         dtReferenceef.push().setValue(hoaDon);

                         SelectedFood.clearSelectedFood();

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
        ImageView imgFood;
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