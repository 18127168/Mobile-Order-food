package com.example.masterchef.ui.manager.Managestaff;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.masterchef.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<staff> {
    private Context mcontext;
    int mresorce;

    public Adapter(Context context, int resource, ArrayList<staff> object){
        super(context,resource,object);
        mcontext=context;
        this.mresorce=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String stt=getItem(position).getStt();
        String name=getItem(position).getName();
        String type=getItem(position).getType();
        ImageView img_delete,img_edit;

        staff e=new staff(stt,name,type);
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresorce,parent,false);
        TextView Stt=(TextView)convertView.findViewById(R.id.stt);
        TextView tvname=(TextView)convertView.findViewById(R.id.name);
        TextView tvtype=(TextView)convertView.findViewById(R.id.nv);
        img_delete=(ImageView)convertView.findViewById(R.id.btn_delete) ;
        img_edit=(ImageView)convertView.findViewById(R.id.btn_edit) ;
        Stt.setText(stt);
        tvname.setText(name);
        tvtype.setText(type);
        return convertView;
    }
}
