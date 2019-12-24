package com.example.capstoneprototype.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.capstoneprototype.DataModels.order_data;
import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.myVuewHolder>{

    ArrayList<order_data> odata;
    Context context;

    public orderAdapter(ArrayList<order_data> odata, Context context) {
        this.odata = odata;
        this.context = context;
    }

    public orderAdapter() {
    }

    @NonNull
    @Override
    public myVuewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);

        return new myVuewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myVuewHolder holder, int position) {
holder.total.setText("Total Items Ordered : "+odata.get(position).getPdata().size());
holder.price.setText("Total Order sum : \u20B9 "+odata.get(position).getTotalp());
holder.address.setText("Delivery Address "+odata.get(position).getAddress());

        Glide.with(context).load(odata.get(position).getPdata().get(0).getPimageurl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return odata.size();
    }

    public class myVuewHolder extends RecyclerView.ViewHolder{

        TextView total,price,address;
        ImageView img;


        public myVuewHolder(@NonNull View itemView) {
            super(itemView);
            total=(TextView)itemView.findViewById(R.id.or_t);
            price=(TextView)itemView.findViewById(R.id.or_p);
            address=(TextView)itemView.findViewById(R.id.or_add);
            img=(ImageView)itemView.findViewById(R.id.or_img);
        }
    }
}
