package com.example.masterchef.ui.manager.Managemenu;

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
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterchef.DatabaseWork;
import com.example.masterchef.Food;
import com.example.masterchef.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.masterchef.MainActivity.server;

public class MangemenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    List<Integer> listIDFoodInMenu = new ArrayList<>();
    DatabaseWork databaseWork = new DatabaseWork();
    ArrayList<String> listTitles = new ArrayList<>();
    Adapter_menu adapter1;
    private String tem1;
    private Adapter_weekdays categoryAdapterWeekdays;
    StorageReference storeImage;
    SearchView searchView;
    Button addFood;

    private Uri filePath;
    private static final int Image_PICK_Code = 1000;
    private static final int Permisson_PICK_Code = 1001;
    public ImageView img_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_managemenu, container, false);
        recyclerView=root.findViewById(R.id.rcv_category);
        categoryAdapterWeekdays =new Adapter_weekdays(getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(categoryAdapterWeekdays);
        addFood = root.findViewById(R.id.them_mon_An);
        storeImage = FirebaseStorage.getInstance().getReference();
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_for_add_menu);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                EditText name = dialog.findViewById(R.id.food_name);
                EditText IDNguyenLieu = dialog.findViewById(R.id.ingredient_food);
                EditText gia = dialog.findViewById(R.id.food_price);
                EditText thoigian = dialog.findViewById(R.id.food_time);
                EditText mota = dialog.findViewById(R.id.food_des);
                EditText SoluongNguyenLieu = dialog.findViewById(R.id.food_number);
                Button img_choose = dialog.findViewById(R.id.choose_image_btn_food);
                Button ok = dialog.findViewById(R.id.btnd);
                Button ko = dialog.findViewById(R.id.btnh);
                img_view = dialog.findViewById(R.id.image_add_food);




                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Food food = new Food();
                        food.setTenmon(name.getText().toString());
                        food.setIdnguyenlieu(IDNguyenLieu.getText().toString());
                        food.setgiatien(Integer.parseInt(gia.getText().toString()));
                        food.setTimeToFinish(Integer.parseInt(thoigian.getText().toString()));
                        food.setMoTa(mota.getText().toString());
                        food.setsoluongnguyenlieu(SoluongNguyenLieu.getText().toString());

                        Query query = FirebaseDatabase.getInstance().getReference("User").child("Food");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String flag_name = databaseWork.AddFood(snapshot, food);

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

                ko.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                img_choose.setOnClickListener(new View.OnClickListener() {
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
    public void onActivityResult(int requestCode, int resultCode, @com.google.firebase.database.annotations.Nullable Intent data) {
        if(resultCode == -1 && requestCode == Image_PICK_Code){
            filePath = data.getData();
            img_view.setImageURI(data.getData());

        }
    }
}