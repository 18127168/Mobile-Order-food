package com.example.masterchef.ui.customer.menu;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.Ingredient;
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

import com.example.masterchef.ItemClickListener;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.example.masterchef.MainActivity.server;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context contexts;
    ArrayList<String> listTitles;
    StorageReference storeImage;
    List<Integer> IDFoodInMenus;
    DatabaseWork databaseWork = new DatabaseWork();

    public MenuAdapter(Context context, List<Integer> IDFoodInMenu){
        this.inflater = LayoutInflater.from(context);
        this.IDFoodInMenus = IDFoodInMenu;
        this.contexts = context;
        listTitles = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_customer_main_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.i("Click", "true");
                holder.dialog.show();
            }
        });

        Food menuFood = databaseWork.GetFoodWithID(Integer.parseInt(IDFoodInMenus.get(position) + ""));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference(server.getText().toString());
        Query userQuery = dataref.child("Ingredient").orderByChild("ID");
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTitles.add(menuFood.getTenmon());

                String detail = "Gồm các nguyên liệu: ";
                boolean first = true;

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (menuFood.getIdnguyenlieu().contains(postSnapshot.getValue(Ingredient.class).getIdnguyenlieu() + "")) {
                            if (!first){
                                detail += ", ";
                            }
                            detail += postSnapshot.getValue(Ingredient.class).getTen();
                            first = false;
                        }
                    }
                }

                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(menuFood.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(holder.imgFood);
                holder.title.setText(menuFood.getTenmon());
                holder.price.setText(menuFood.getGiatien() + " đ");

                holder.choose_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.choose_btn.setBackgroundColor(contexts.getResources().getColor(R.color.my_red));
                        holder.choose_btn.setText("Đã Chọn");
                        SelectedFood.addSelectedFood(Integer.parseInt(IDFoodInMenus.get(position) + ""));
                    }
                });

                if (SelectedFood.checkExist(Integer.parseInt(IDFoodInMenus.get(position) + "")) != -1){
                    holder.choose_btn.setBackgroundColor(contexts.getResources().getColor(R.color.my_red));
                    holder.choose_btn.setText("Đã Chọn");
                }

                holder.dialog = new Dialog(contexts);
                holder.dialog.setContentView(R.layout.custom_dialog_customer_fooddetails);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    holder.dialog.getWindow().setBackgroundDrawable(getDrawable(contexts, R.drawable.background));
                }
                holder.dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.dialog.setCancelable(false);

                Button closeButton = holder.dialog.findViewById(R.id.menu_closeDialog_btn);
                ImageView detailImage = holder.dialog.findViewById(R.id.menu_fooddetail_image);
                TextView detailIngredian = holder.dialog.findViewById(R.id.ingredient_dialog_textView);
                TextView detailDiscription = holder.dialog.findViewById(R.id.discription_dialog_textView);

                storeImage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orderdoan-a172f.appspot.com/").child(menuFood.getFlagName());
                Glide.with(contexts.getApplicationContext()).using(new FirebaseImageLoader()).load(storeImage).into(detailImage);
                detailIngredian.setText(detail);
                detailDiscription.setText(menuFood.getmoTa());

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
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
        return IDFoodInMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView imgFood;
        TextView title, price;
        Button choose_btn;
        Dialog dialog;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_title);
            imgFood = itemView.findViewById(R.id.menu_image);
            price = itemView.findViewById(R.id.menu_price);
            choose_btn = itemView.findViewById(R.id.menu_choose_btn);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

    public ArrayList<String> getListTitlesFood(){
        return this.listTitles;
    }
}