package com.example.capstoneprototype.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.capstoneprototype.Adapters.orderAdapter;
import com.example.capstoneprototype.DataModels.order_data;
import com.example.capstoneprototype.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderStatActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<order_data> odata=new ArrayList<>();
    orderAdapter adapter;
    FirebaseAuth mauth;
    DatabaseReference mref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_stat);
        rv=(RecyclerView)findViewById(R.id.oact_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new orderAdapter(odata,this);

        rv.setAdapter(adapter);

        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        loadOrders();

    }

    private void loadOrders() {

        mref.child("Orders").child(mauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        order_data o=snapshot.getValue(order_data.class);
                        odata.add(o);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
