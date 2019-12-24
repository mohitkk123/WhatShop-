package com.example.capstoneprototype.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.OrderStatActivity;
import com.example.capstoneprototype.Adapters.cartAdapter;
import com.example.capstoneprototype.DataModels.order_data;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.DataModels.shop_order_set;
import com.example.capstoneprototype.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements cartAdapter.listner{

    ArrayList<productDetails> pdata=new ArrayList<>();
    RecyclerView rv;
cartAdapter adapter;
TextView totalcart;

FirebaseAuth mauth;
DatabaseReference mref;
ProgressBar mprogress;

Button checkout;
ImageView orderInt;

double sum=0;

String uname;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_cart, container, false);
       rv=(RecyclerView)v.findViewById(R.id.rv_cart);
       mprogress=(ProgressBar)v.findViewById(R.id.progress_cart) ;
       rv.setLayoutManager(new LinearLayoutManager(getContext()));
       totalcart=(TextView)v.findViewById(R.id.totalp_cart) ;
       adapter=new cartAdapter(pdata,getContext(),CartFragment.this);
       rv.setAdapter(adapter);
       checkout=(Button)v.findViewById(R.id.cart_checkout) ;
       mauth=FirebaseAuth.getInstance();
       orderInt=(ImageView)v.findViewById(R.id.ord_b_cart);
       orderInt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(getContext(), OrderStatActivity.class);
               startActivity(i);
           }
       });

       mref= FirebaseDatabase.getInstance().getReference();

       if(mauth.getCurrentUser().getUid()!=null) {
           loadCart();

           mref.child("Users_Profile").child(mauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   uname=dataSnapshot.child("user_name").getValue().toString();
                   Toast.makeText(getContext(), "name is "+uname, Toast.LENGTH_SHORT).show();


               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

       }else {
           Toast.makeText(getContext(), "Please Log in First!", Toast.LENGTH_LONG).show();
       }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdata.size()>0){

                  showAlert();

                }else{
                    Toast.makeText(getContext(), "cart is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });


       return v;
    }

    private void loadCart() {
        pdata.clear();
        mprogress.setVisibility(View.VISIBLE);

        mref.child("Cart").child(mauth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       productDetails p=snapshot.getValue(productDetails.class);
                       pdata.add(p);
                       updateT(p.getPprice(),1);
                       adapter.notifyDataSetChanged();
                   }



               }
               mprogress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

   public void updateT(String price,int m){
        if(sum>=0) {


            if (m == 1) {
                sum = sum + Double.parseDouble(price);
                totalcart.setText("Total: \u20B9 " + sum);

            }
            if (m == 2) {
                sum = sum - Double.parseDouble(price);
                totalcart.setText("Total: \u20B9 " + sum);
            }
        }else
            return;
    }

    @Override
    public void clicklistner(final int position) {
        updateT(pdata.get(position).getPprice(),2);





        pdata.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,pdata.size());

    }


    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        alertDialog.setTitle("Place Order");
        LayoutInflater inflater = this.getLayoutInflater();
        View confirm_layout = inflater.inflate(R.layout.cart_confirm, null);

        final RadioButton rad_cart=(RadioButton)confirm_layout.findViewById(R.id.cart_rad);
        final EditText    ed_cart=(EditText)confirm_layout.findViewById(R.id.cart_e1) ;

        alertDialog.setView(confirm_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_bag);

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(!TextUtils.isEmpty(ed_cart.getText().toString())){
                    if(rad_cart.isChecked()){

                        mref.child("Orders").child(mauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               dataSnapshot.getRef().push().setValue(new order_data(pdata,""+sum,ed_cart.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(getContext(), "Yay!!! Order confirm.", Toast.LENGTH_SHORT).show();
                                   }
                               });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        mref.child("Cart").child(mauth.getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pdata.clear();
                                adapter.notifyDataSetChanged();
                                totalcart.setText("Total: \u20B9 0.0");
                            }
                        });



                        for(int in=0;in<pdata.size();in++){
                            mref.child("Businesses").child(pdata.get(in).getShop_id()).child("Orders").push().setValue(new shop_order_set(pdata.get(in),uname,ed_cart.getText().toString()));

                        }





                    }else{
                        Toast.makeText(getContext(), "please select a payment method", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Enter address ", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        }).show();

    }
}
