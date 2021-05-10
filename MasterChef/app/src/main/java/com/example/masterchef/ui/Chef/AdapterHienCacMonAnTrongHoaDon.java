package com.example.masterchef.ui.chef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.masterchef.MainActivity.server;

public class AdapterHienCacMonAnTrongHoaDon extends BaseAdapter {
    private String[] listIDMonAn,ListSoluong,ListHoanThanh;
    private LayoutInflater layoutInflater;
    private Context context;
    private Food foodData;
    private DatabaseReference refCurrent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataref = database.getReference(server.getText().toString());
    StorageReference storeImage;

    public AdapterHienCacMonAnTrongHoaDon(Context aContext,  String[] listData,  String[] listSoluong,  String[] listHoanThanh,DatabaseReference linkRef) {
        this.context = aContext;
        this.listIDMonAn = listData;
        this.ListSoluong = listSoluong;
        this.ListHoanThanh = listHoanThanh;
        layoutInflater = LayoutInflater.from(aContext);
        this.refCurrent = linkRef;
    }

    @Override
    public int getCount() {
        return listIDMonAn.length;
    }

    @Override
    public Object getItem(int position) {
        return listIDMonAn[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_adapter_chef_order_menu_, null);
            holder = new ViewHolder();
            holder.MonAnView = (TextView) convertView.findViewById(R.id.monanAdapter);
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageViewadapter);
            holder.SoluonghoanthanhView = (TextView) convertView.findViewById(R.id.hoanthanh);
            holder.plus = (Button) convertView.findViewById(R.id.buttonincrease);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String ID = listIDMonAn[position];
        String Soluong = ListSoluong[position];
        String HoanThanh = ListHoanThanh[position];
        convertView.setTag(holder);
        Query userQuery = dataref.child("Food").orderByChild("ID").equalTo(Integer.parseInt(ID));
        View finalConvertView = convertView;
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            foodData =  postSnapshot.getValue(Food.class);
                        }
                    }
                holder.MonAnView.setText(foodData.getTenmon());
                holder.SoluonghoanthanhView.setText(HoanThanh + " / " + Soluong);
                holder.plus.setOnClickListener(new View.OnClickListener() { //tru nguyen lieu thi viet trong nay
                    @Override
                    public void onClick(View v) {
                        int intofHoanThanh = Integer.parseInt(HoanThanh);
                        int intofSoLuong = Integer.parseInt(Soluong);
                        if(intofHoanThanh < intofSoLuong){
                            intofHoanThanh += 1;
                            ListHoanThanh[position] = ""+intofHoanThanh;
                            String newStringHoanThanh ="";
                            for(int i=0;i<ListHoanThanh.length;i++){
                                newStringHoanThanh += ListHoanThanh[i];
                                if(i!= ListHoanThanh.length-1)
                                    newStringHoanThanh += ",";
                            }
                            refCurrent.child("Hoanthanh").setValue(newStringHoanThanh);

                            if(checkBillFinish()){
                                refCurrent.child("trangthai").setValue(1);
                            }

                            notifyDataSetChanged();
                        }
                    }
                });
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(foodData.getFlagName());
                Glide.with(finalConvertView.getContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.flagView);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return convertView;
    }
    static class ViewHolder {
        ImageView flagView;
        TextView MonAnView;
        TextView SoluonghoanthanhView;
        Button plus;
    }
    public boolean checkBillFinish(){
        for(int i=0;i<ListHoanThanh.length;i++){
            if(Integer.parseInt(ListHoanThanh[i]) != Integer.parseInt(ListSoluong[i]))
                return false;
        }
        return true;
    }
}
