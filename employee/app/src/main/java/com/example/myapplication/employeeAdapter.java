package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class employeeAdapter  extends ArrayAdapter<employee> {
    private Context mcontext;
    int mresorce;
    public employeeAdapter(Context context, int resource, ArrayList<employee> object){
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
        employee e=new employee(stt,name,type);
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresorce,parent,false);
        TextView Stt=(TextView)convertView.findViewById(R.id.stt);
        TextView tvname=(TextView)convertView.findViewById(R.id.name);
        TextView tvtype=(TextView)convertView.findViewById(R.id.nv);
        Stt.setText(stt);
        tvname.setText(name);
        tvtype.setText(type);
        return convertView;
    }
}
