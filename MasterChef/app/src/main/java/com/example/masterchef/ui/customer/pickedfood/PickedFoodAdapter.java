package com.example.masterchef.ui.customer.pickedfood;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.example.masterchef.SelectedFood;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PickedFoodAdapter extends RecyclerView.Adapter<PickedFoodAdapter.ViewHolder> {

    LayoutInflater inflater;
    Context contexts;
    List<SelectedFood.Food> listIDsFoodInMenuWithQuantity;
    StorageReference storeImage;
    DatabaseWork databaseWork = new DatabaseWork();
    String[] notes = {"","","","",""};

    public PickedFoodAdapter(Context context, List<SelectedFood.Food> listIDsFoodInMenuWithQuantity){
        this.inflater = LayoutInflater.from(context);
        this.contexts = context;
        this.listIDsFoodInMenuWithQuantity = listIDsFoodInMenuWithQuantity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_picked_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Food menuFood = databaseWork.GetFoodWithID(listIDsFoodInMenuWithQuantity.get(position).getIdFood());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref=database.getReference("User");
        Query userQuery = dataref.child("Food").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(menuFood.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.image);
                holder.title.setText(menuFood.getTenmon());
                holder.price.setText(menuFood.getGiatien() + " đ");
                holder.number.setText(Integer.toString(listIDsFoodInMenuWithQuantity.get(position).getQuantity()));
                holder.note.setText(listIDsFoodInMenuWithQuantity.get(position).getNote());

                holder.plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listIDsFoodInMenuWithQuantity.get(position).increaseQuantity();
                        holder.number.setText(Integer.toString(listIDsFoodInMenuWithQuantity.get(position).getQuantity()));
                    }
                });

                holder.minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listIDsFoodInMenuWithQuantity.get(position).decreaseQuantity();
                        holder.number.setText(Integer.toString(listIDsFoodInMenuWithQuantity.get(position).getQuantity()));
                    }
                });

                holder.number.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        SelectedFood.setQuantity(listIDsFoodInMenuWithQuantity.get(position).getIdFood(), Integer.parseInt(holder.number.getText().toString()));
                        // TODO Auto-generated method stub
                    }
                });

                holder.note.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                        SelectedFood.setNote(listIDsFoodInMenuWithQuantity.get(position).getIdFood(), holder.note.getText().toString());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public int getItemCount(){
        return listIDsFoodInMenuWithQuantity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        EditText number, note;
        ImageView image, minusBtn, plusBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.pickedfood_image);
            title = itemView.findViewById(R.id.pickedfood_title);
            price = itemView.findViewById(R.id.pickedfood_price);
            number = (EditText) itemView.findViewById(R.id.pickedfood_number);
            note = (EditText) itemView.findViewById(R.id.pickedfood_note);
            minusBtn = itemView.findViewById(R.id.pickedfood_minusBtn);
            plusBtn = itemView.findViewById(R.id.pickedfood_plusBtn);
        }
    }

    public List<SelectedFood.Food> getListIDsFoodInMenuWithQuantity() { return listIDsFoodInMenuWithQuantity; }

    public void updateListIDsFoodInMenuWithQuantity(List<SelectedFood.Food> listIDsFoodInMenuWithQuantity) {
        this.listIDsFoodInMenuWithQuantity = listIDsFoodInMenuWithQuantity;
    }
}