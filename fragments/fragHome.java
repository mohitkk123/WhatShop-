package com.example.capstoneprototype.fragments;

import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.ProductActivity;
import com.example.capstoneprototype.Activities.ShopActivity;
import com.example.capstoneprototype.Adapters.horAdapter;
import com.example.capstoneprototype.Adapters.verAdapter;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.DataModels.shopdetails;
import com.example.capstoneprototype.DataModels.shopLocate;
import com.example.capstoneprototype.MainActivity;
import com.example.capstoneprototype.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragHome extends Fragment implements verAdapter.onProductClickListener,horAdapter.onShopClickLiatener {
    DatabaseReference mref;
ProgressBar progress1,progress2;

RecyclerView HorRv,VerRv;
ArrayList<shopdetails> shopdet=new ArrayList<>();
    horAdapter horad;

    FusedLocationProviderClient mclient;


ArrayList<productDetails> productdet=new ArrayList<>();
    verAdapter verad;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for  this fragment

        View v= inflater.inflate(R.layout.fragment_frag_home, container, false);

        HorRv=(RecyclerView)v.findViewById(R.id.horRecyclerView);
        VerRv =(RecyclerView)v.findViewById(R.id.verRecyclerView);
        progress1=(ProgressBar)v.findViewById(R.id.fraghome_progress1);
        progress2=(ProgressBar)v.findViewById(R.id.fraghome_progress2);
        mclient= LocationServices.getFusedLocationProviderClient(getContext());


        mref= FirebaseDatabase.getInstance().getReference();
        //shop recycler view
        populateShops();
         horad=new horAdapter(shopdet,getContext(),this);
        HorRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        HorRv.setAdapter(horad);
//        Toast.makeText(getContext(), ""+shopdet.get(0).getStorePicUrl(), Toast.LENGTH_SHORT).show();

    //product recycler view

        populateProducts();
         verad=new verAdapter(productdet,getContext(),this);
         GridLayoutManager gdmanager=new GridLayoutManager(getContext(),2);


        VerRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        VerRv.setAdapter(verad);



        return v;
    }


    public void populateShops(){
        progress1.setVisibility(View.VISIBLE);

        mref.child("Businesses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(shopdet!=null){
                        shopdet.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final shopdetails shop = dataSnapshot1.getValue(shopdetails.class);
                            mclient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    double theta = location.getLongitude() - shop.getLongt();
                                    double dist = Math.sin((location.getLatitude() * Math.PI / 180.0)) * Math.sin((shop.getLat() * Math.PI / 180.0))
                                            + Math.cos((location.getLatitude() * Math.PI / 180.0)) * Math.cos((shop.getLat() * Math.PI / 180.0))
                                            * Math.cos((theta * Math.PI / 180.0));
                                    dist = Math.acos(dist);
                                    dist = (dist * 180.0 / Math.PI);
                                    dist = dist * 60; // 60 nautical miles per degree of seperation
                                    dist = dist * 1852; // 1852 meters per nautical mile

                                    dist=Math.round(dist);
                                    if(dist<50000){

                                        shopdet.add(shop);
                                        horad.notifyDataSetChanged();
                                    }

                                }
                            });




                            progress1.setVisibility(View.INVISIBLE);


                        }
                    }else {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            shopdetails shop = dataSnapshot1.getValue(shopdetails.class);
                            shopdet.add(shop);
                            horad.notifyDataSetChanged();


                            progress1.setVisibility(View.INVISIBLE);

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void populateProducts(){
        progress2.setVisibility(View.VISIBLE);
        mref.child("Businesses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(productdet!=null){
                        productdet.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Products").getChildren()){
                                productDetails product=dataSnapshot2.getValue(productDetails.class);
                                productdet.add(product);
                                verad.notifyDataSetChanged();
                                progress2.setVisibility(View.INVISIBLE);




                            }

                        }

                    }else{
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            for(DataSnapshot dataSnapshot2:dataSnapshot1.child("Products").getChildren()){
                                productDetails product=dataSnapshot2.getValue(productDetails.class);
                                productdet.add(product);
                                verad.notifyDataSetChanged();
                                progress2.setVisibility(View.INVISIBLE);




                            }

                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onProductclick(int postion) {
        Intent i=new Intent(getContext(), ProductActivity.class);
        i.putExtra("product_d_object",productdet.get(postion));
        startActivity(i);

    }

    @Override
    public void onShopClick(int position) {
        Intent intent=new Intent(getContext(), ShopActivity.class);
        intent.putExtra("shop data",shopdet.get(position));
        startActivity(intent);

    }
}
