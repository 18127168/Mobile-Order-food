package com.example.masterchef.ui.manager.discount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;
import java.util.List;

public class discount_adapter extends RecyclerView.Adapter<discount_adapter.ViewHolder> {
    Context mcontext;
    List<discount> list;

    public discount_adapter(Context mcontext, List<discount> list) {
        this.mcontext = mcontext;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.manager_staff_discount,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        discount menu_discount=list.get(position);
        if(menu_discount==null){
            return;
        }
        holder.tv1.setText(menu_discount.getSTT());
        holder.tv2.setText(menu_discount.getMa());
        holder.tv3.setText(menu_discount.getNgayapdung());
        holder.tv4.setText(menu_discount.getMota());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView tv1,tv2,tv3,tv4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.discount_tv1);
            tv2=itemView.findViewById(R.id.discount_tv2);
            tv3=itemView.findViewById(R.id.discount_tv3);
            tv4=itemView.findViewById(R.id.discount_tv4);
        }
    }
}