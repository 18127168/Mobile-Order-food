package com.example.masterchef.ui.manager.discount;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masterchef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.masterchef.MainActivity.server;

public class ManagediscountFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListView listemploy;
    private Button btnthem,btnxoa,btnsua,btn;
    private String tem1;
    SearchView searchView;
    private RecyclerView recyclerView1;
    private discount_adapter dis;
    List<discount> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.manager_fragment_discount, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("User");
        Query userQuery = dataref.child("khuyenmai");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(discount.class));
                }
                dis=new discount_adapter(getActivity(),list);
                recyclerView1=root.findViewById(R.id.discount_rcv);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView1.setAdapter(dis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ImageView imag=root.findViewById(R.id.discount_add);
        imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.discount_dialog_add);
                EditText makhuyenmai=(EditText)dialog.findViewById(R.id.ma_discount);
                EditText Mota=(EditText)dialog.findViewById(R.id.motadiscount);
                EditText ngaybatdau=(EditText)dialog.findViewById(R.id.ngaybatdaudiscount);
                EditText ngayketthuc=(EditText)dialog.findViewById(R.id.ngayketthucdiscount);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btn_dongy=dialog.findViewById(R.id.btn_add_discount);
                Button btn_huy=dialog.findViewById(R.id.btn_huy_add_discount);
                btn_dongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mkm=makhuyenmai.getText().toString().trim();
                        String mt=Mota.getText().toString().trim();
                        String nbd=ngaybatdau.getText().toString().trim();
                        String nkt=ngayketthuc.getText().toString().trim();
                        int tem=Integer.parseInt(mt);
                        discount e=new discount(mkm,nbd,tem,nkt);
                        DatabaseReference UserDbref=FirebaseDatabase.getInstance().getReference().child("User").child("khuyenmai");
                        UserDbref.push().setValue(e);
                        dialog.dismiss();
                    }

                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                }
            });


        return root;
    }


}