package com.example.capstoneprototype.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.ProductActivity;
import com.example.capstoneprototype.Activities.ProductSearchAct;
import com.example.capstoneprototype.Adapters.verAdapter;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nearby_search_frag extends Fragment implements ProductSearchAct.sendDataListener,verAdapter.onProductClickListener {

 FirebaseAuth mauth;
 DatabaseReference mref;
String search_str;

ArrayList<String> Businesse_children=new ArrayList<String>();
ArrayList<productDetails> products=new ArrayList<>();
RecyclerView rc_nearby;
verAdapter adapter;

    public Nearby_search_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_nearby_search_frag, container, false);

        //setting listener
        ((ProductSearchAct)getActivity()).setSenddataListener_setter(this);


        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();

        rc_nearby=(RecyclerView)v.findViewById(R.id.product_nearby_search);
        rc_nearby.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter=new verAdapter(products,getContext(),this);
        rc_nearby.setAdapter(adapter);




        return v;
    }

    @Override
    public void sendSearchD(String s) {
        search_str=s;
        if(products!=null){
            products.clear();
        }
    mref.child("Businesses").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                if(dataSnapshot1.exists()){
                    if(dataSnapshot1.child("Products").exists()){
                        Query q=mref.child("Businesses").child(dataSnapshot1.getKey()).child("Products").orderByChild("pname")
                                .startAt(search_str).endAt(search_str+"\uf8ff");
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Toast.makeText(getContext(), ""+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                    for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                        productDetails product=dataSnapshot2.getValue(productDetails.class);
                                        products.add(product);
                                        adapter.notifyDataSetChanged();
                                    }}else{

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });






    }
//adapter recycler on click item
    @Override
    public void onProductclick(int postion) {
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getContext(), ProductActivity.class);
        i.putExtra("product_d_object",products.get(postion));
        startActivity(i);

    }
}
