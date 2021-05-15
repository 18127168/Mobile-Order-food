package com.example.masterchef.ui.manager.stock;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Ingredient;
import com.example.masterchef.R;
import com.example.masterchef.ui.manager.statistic.ThongKe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.masterchef.MainActivity.server;

public class StockFragment extends Fragment {

    public RecyclerView dataList;
    Adapter adapter;
    DatabaseWork databaseWork = new DatabaseWork();

    StorageReference storeImage;
    private Uri filePath;
    private static final int Image_PICK_Code = 1000;
    private static final int Permisson_PICK_Code = 1001;
    public ImageView img_view;

    public View tmp;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.manager_fragment_stock, container, false);
        dataList = root.findViewById(R.id.recyclerCategory);
        tmp = root;

        Button add_new = root.findViewById(R.id.add_ingredient);

        storeImage = FirebaseStorage.getInstance().getReference();

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_new_ingredient);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                EditText name = dialog.findViewById(R.id.ingredient_name);
                EditText price = dialog.findViewById(R.id.ingredient_price);
                EditText number = dialog.findViewById(R.id.ingredient_number);

                img_view = dialog.findViewById(R.id.image_add);

                Button ci = dialog.findViewById(R.id.choose_image_btn);
                Button btnd = dialog.findViewById(R.id.btnd);
                Button btnh = dialog.findViewById(R.id.btnh);

                ci.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                                requestPermissions(permissions, Permisson_PICK_Code);
                            } else {
                                pickImageFromGallery();
                            }
                        } else {
                            pickImageFromGallery();
                        }
                    }
                });

                btnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Ingredient ingredient = new Ingredient();
                        ingredient.setTen(name.getText().toString());
                        ingredient.setGia(Integer.parseInt(price.getText().toString()));
                        ingredient.setSoluongtonkho(Integer.parseInt(number.getText().toString()));
                        Calendar cal = Calendar.getInstance();
                        int i = cal.get(Calendar.MONTH)+1;
                        String query1;
                        if(i<10)  query1 = "0"+i+cal.get(Calendar.YEAR);
                        else query1 =""+i+cal.get(Calendar.YEAR);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataref = database.getReference(server.getText().toString());
                        Query query2 = dataref.child("ThongKe").orderByKey().equalTo(query1);
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    ThongKe thongKe = null;
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        thongKe = dataSnapshot.getValue(ThongKe.class);
                                    }
                                    thongKe.Chiphi += ingredient.getGia()*Integer.parseInt(number.getText().toString());

                                    dataref.child("ThongKe").child(query1).child("Chiphi").setValue(thongKe.Chiphi);
                                }
                                else dataref.child("ThongKe").child(query1).child("Chiphi").setValue(ingredient.getGia()*Integer.parseInt(number.getText().toString()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Query query = FirebaseDatabase.getInstance().getReference("User").child("Ingredient");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String flag_name = databaseWork.AddIngredient(snapshot, ingredient);

                                if(filePath != null)
                                {
                                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                    progressDialog.setTitle("Uploading...");
                                    progressDialog.show();
                                    StorageReference ref
                                            = storeImage
                                            .child(flag_name);
                                    ref.delete();
                                    ref.putFile(filePath)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.e("",e.getMessage());
                                                }
                                            })
                                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                            .getTotalByteCount());
                                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                                }
                                            });
                                }

                                dialog.dismiss();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });





                    }
                });

                btnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference("User").child("Ingredient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Ingredient> list = databaseWork.GetListIngredient(snapshot);
                adapter = new Adapter(getActivity(), list);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Image_PICK_Code);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case    Permisson_PICK_Code:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(getContext(),"Denied....!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == -1 && requestCode == Image_PICK_Code){
            filePath = data.getData();
            img_view.setImageURI(data.getData());

        }
    }
}