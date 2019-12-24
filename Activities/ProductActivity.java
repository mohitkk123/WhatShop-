package com.example.capstoneprototype.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProductActivity extends AppCompatActivity {
    TextView ptitle,pdes,price,category;
    ImageView img;
    productDetails product;
    String shop_id;
    Button chat_button,add_cart_button;
    FirebaseAuth mauth;
    DatabaseReference mref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ptitle=(TextView)findViewById(R.id.prodact_title);
        pdes=(TextView)findViewById(R.id.prodact_des);
        price=(TextView)findViewById(R.id.prodact_price);
       category=(TextView)findViewById(R.id.prodact_category);
       img=(ImageView)findViewById(R.id.productbg);
       chat_button=(Button)findViewById(R.id.prodact_chat_b);
       add_cart_button=(Button)findViewById(R.id.prodact_add_cart);
       mauth=FirebaseAuth.getInstance();
       mref= FirebaseDatabase.getInstance().getReference();

        Intent i=getIntent();
        if(i!=null){

            product=i.getParcelableExtra("product_d_object");


            ptitle.setText(product.getPname());
            pdes.setText(product.getPdes());
            price.setText(product.getPprice());
            category.setText(product.getPcat());
            shop_id=product.getShop_id();
            Glide.with(this).load(product.getPimageurl()).into(img);

        }
        //start chat activity

        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProductActivity.this,ChatMessageActivity.class);
                i.putExtra("pd",product);
                startActivity(i);
            }
        });

        //add product to cart
        add_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductActivity.this, "do add to cart implementation", Toast.LENGTH_SHORT).show();
                if(mauth.getCurrentUser().getUid()!=null){
                    mref.child("Cart").child(mauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().push().setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProductActivity.this, "Value added", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }else{
                    Toast.makeText(ProductActivity.this, "please log in first", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ProductActivity.this,LoginAct.class);
                    startActivity(intent);
                }

            }
        });


    }
}
