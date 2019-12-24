package com.example.capstoneprototype.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.R;
import com.example.capstoneprototype.fragments.CartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.myViewholder>{
    ArrayList<productDetails> pdata;
    Context context;
    listner mlistener;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    DatabaseReference mref= FirebaseDatabase.getInstance().getReference();

    public cartAdapter(ArrayList<productDetails> pdata, Context context,listner mlistener) {
        this.pdata = pdata;
        this.context = context;
        this.mlistener=mlistener;
    }

    public cartAdapter(ArrayList<productDetails> pdata) {
        this.pdata = pdata;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);

        return new myViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, final int position) {
        holder.name.setText(pdata.get(position).getPname());
        holder.price.setText("\u20B9 "+pdata.get(position).getPprice());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mlistener.clicklistner(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdata.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder{
        TextView name,price;
        Button del;

        public myViewholder(@NonNull View itemView) {
            super(itemView);
            del=(Button)itemView.findViewById(R.id.card_l_del);
            name=(TextView)itemView.findViewById(R.id.cart_l_name);
            price=(TextView)itemView.findViewById(R.id.cart_l_price);
        }
    }

    public interface listner{
        public void clicklistner(int position);
    }
}

