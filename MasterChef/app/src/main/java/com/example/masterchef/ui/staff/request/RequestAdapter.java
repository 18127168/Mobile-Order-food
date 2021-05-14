package com.example.masterchef.ui.staff.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.R;
import com.example.masterchef.ServeItem;
import com.example.masterchef.YeuCau;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    List<YeuCau> ListServe;
    Context contexts;
    LayoutInflater inflater;


    public RequestAdapter(Context context, List<YeuCau> LS){
        this.ListServe = LS;
        this.contexts = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_staff_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.request_id.setText("Bàn " + String.valueOf(ListServe.get(position).getTable()) + " yêu cầu hỗ trợ");
        holder.request_time.setText(String.valueOf(ListServe.get(position).getdate()));
        holder.done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexts, "Yêu cầu đã được xử lý", Toast.LENGTH_SHORT).show();
                ListServe.get(position).getRef().child("status").setValue(1);
            }
        });
    }

    @Override
    public int getItemCount(){
        return ListServe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView request_id, request_time;
        Button done_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            request_id = itemView.findViewById(R.id.request_id);
            request_time = itemView.findViewById(R.id.time_request);
            done_btn = itemView.findViewById(R.id.done_btn);

        }

        @Override
        public void onClick(View v) {

        }
    }


}