package com.example.masterchef.ui.manager.Managemenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.masterchef.R;

import java.util.List;

public class Adapter_weekdays extends RecyclerView.Adapter<Adapter_weekdays.Menu_categoricalViewholder> {
    private Context mcontext;
    private List<category> menuCategoricals;

    public Adapter_weekdays(Context mcontext) {
        this.mcontext = mcontext;
    }
    public void setData(List<category> list){
        this.menuCategoricals=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter_weekdays.Menu_categoricalViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_adapter_managemenu1,parent,false);
        return new Adapter_weekdays.Menu_categoricalViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_weekdays.Menu_categoricalViewholder holder, int position) {
        category menu_categorical=menuCategoricals.get(position);
        if(menu_categorical==null){
            return;
        }
        holder.textView.setText(menu_categorical.getName());//thuc don thu 2
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mcontext,2);
        holder.recyclerView.setLayoutManager(gridLayoutManager);
        Adapter_menu adaptermenu =new Adapter_menu();
        adaptermenu.setData(menu_categorical.getMenus());
        holder.recyclerView.setAdapter(adaptermenu);
    }

    @Override
    public int getItemCount() {
        if(menuCategoricals!=null){
            return menuCategoricals.size();
        }
        return 0;
    }

    public class Menu_categoricalViewholder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;
        public Menu_categoricalViewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.name_category_tv);
            recyclerView=itemView.findViewById(R.id.rev_menu);

        }
    }
}
