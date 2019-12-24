package com.example.capstoneprototype.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.Adapters.verAdapter;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.DataModels.shopdetails;
import com.example.capstoneprototype.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity  implements verAdapter.onProductClickListener{

    String SHOP_ID;
    FirebaseAuth mauth;
    DatabaseReference mref;
    ArrayList<productDetails> products=new ArrayList<>();
    Button chat_b;


    ImageView shop_img;
    TextView storename,owner,category,city;
    RecyclerView shopact_rv;
    verAdapter adapter;
    shopdetails shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        chat_b=(Button)findViewById(R.id.shopact_chat_b) ;


        shop_img=(ImageView)findViewById(R.id.shopbg);
        city=(TextView)findViewById(R.id.shopact_city);
        storename=(TextView)findViewById(R.id.shopact_title);
        owner=(TextView)findViewById(R.id.shopact_owner);
        category=(TextView)findViewById(R.id.shopact_category);

        shopact_rv=(RecyclerView)findViewById(R.id.shopact_recycler);


        Intent intent=getIntent();
        if(intent!=null){
             shop=intent.getParcelableExtra("shop data");
            Glide.with(this).load(shop.getStorePicUrl()).into(shop_img);
            SHOP_ID=shop.getStoreId();
            storename.setText(shop.getShopname());
            owner.setText(shop.getName());
            category.setText(shop.getCategory());
            city.setText(shop.getCity());
        }

        populateShopActProducts();
         adapter=new verAdapter(products,this,this);
        shopact_rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        shopact_rv.setAdapter(adapter);

            chat_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ShopActivity.this, ChatMessageActivity.class);
                    intent.putExtra("shop data",shop);
                    startActivity(intent);
                }
            });
    }

    public void populateShopActProducts(){
       // Toast.makeText(this, ""+SHOP_ID, Toast.LENGTH_SHORT).show();
        Query query=mref.child("Businesses").orderByChild("storeId").equalTo(SHOP_ID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       Toast.makeText(ShopActivity.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                       if(dataSnapshot1.child("Products").exists()){
                           for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Products").getChildren()) {
                               if (dataSnapshot2.exists()) {
                                   productDetails product = dataSnapshot2.getValue(productDetails.class);
                                   products.add(product);



                               } else {
                                   Toast.makeText(ShopActivity.this, "no product available", Toast.LENGTH_SHORT).show();
                               }
                           }
                       }else{
                           Toast.makeText(ShopActivity.this, "No Product is available right now", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(ShopActivity.this,ProductActivity.class);
        intent.putExtra("product_d_object",products.get(postion));
        startActivity(intent);

    }
}
