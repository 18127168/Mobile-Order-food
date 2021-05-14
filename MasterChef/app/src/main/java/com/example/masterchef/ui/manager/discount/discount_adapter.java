package com.example.masterchef.ui.manager.discount;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.HoaDon;
import com.example.masterchef.R;
import com.example.masterchef.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class discount_adapter extends RecyclerView.Adapter<discount_adapter.ViewHolder> {
    Context mcontext;
    List<discount> list;
    int size;

    public discount_adapter(Context mcontext, List<discount> list) {
        this.mcontext = mcontext;
        this.list = list;
        size=list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.manager_staff_discount,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.tv1.setText(list.get(position).getMakhuyenmai());
       holder.tv2.setText(list.get(position).getNgaybatdau()+"-"+list.get(position).getNgayketthuc());
       holder.tv3.setText(""+list.get(position).getMota()+"%");
       holder.img_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseDatabase database = FirebaseDatabase.getInstance();
               DatabaseReference dataref=database.getReference("User");
               Query userQuery = dataref.child("khuyenmai").orderByChild("makhuyenmai").equalTo(list.get(position).getMakhuyenmai());
               userQuery.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot postSnapshot: snapshot.getChildren()){
                           postSnapshot.getRef().removeValue();

                       }
                       //list.remove(position);
                       //discount_adapter.this.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

           }
       });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView tv1,tv2,tv3;
        ImageView img_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.discount_tv1);
            tv2=itemView.findViewById(R.id.discount_tv2);
            tv3=itemView.findViewById(R.id.discount_tv3);
            img_delete=itemView.findViewById(R.id.discount_delete);
        }
    }
}