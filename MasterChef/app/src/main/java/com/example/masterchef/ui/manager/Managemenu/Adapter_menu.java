package com.example.masterchef.ui.manager.Managemenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.R;

import java.util.List;

public class Adapter_menu extends RecyclerView.Adapter<Adapter_menu.Menuviewholder> {
    private List<Menu> mmenu;
    public void setData(List<Menu> list){
        this.mmenu=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter_menu.Menuviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_adapter_managermenu,parent,false);
        return new Adapter_menu.Menuviewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_menu.Menuviewholder holder, int position) {
        Menu menu=mmenu.get(position);
        if(menu==null){
            return;
        }
        holder.imageView.setImageResource(menu.getResourceid());
        holder.tv1.setText(menu.getName());
        //holder.tv2.setText(menu.getName());
    }

    @Override
    public int getItemCount() {
        if(mmenu!=null){
            return mmenu.size();
        }
        return 0;
    }

    public class Menuviewholder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        TextView tv1,tv2;
        public Menuviewholder(@NonNull View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.menu_img);
            tv1=itemView.findViewById(R.id.menu_tv1);
            //tv2=itemView.findViewById(R.id.menu_tv2);
        }

    }
}
