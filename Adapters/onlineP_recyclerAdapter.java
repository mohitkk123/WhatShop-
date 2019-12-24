package com.example.capstoneprototype.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.DataModels.online_product_data;
import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class onlineP_recyclerAdapter extends RecyclerView.Adapter<onlineP_recyclerAdapter.OnlineViewHolder> {
    ArrayList<online_product_data> products;
    Context context;
    ItemClickListener mOlistener;

    public onlineP_recyclerAdapter(ArrayList<online_product_data> products, Context context,ItemClickListener mOlistener) {
        this.products = products;
        this.context = context;
        this.mOlistener=mOlistener;
    }

    @NonNull
    @Override
    public OnlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.online_scrapped_product_layout,parent,false);

        return new OnlineViewHolder(v,mOlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineViewHolder holder, int position) {
        Glide.with(context).load(products.get(position).getPic()).into(holder.img);
        holder.title.setText(products.get(position).getTitle());
        holder.price.setText(products.get(position).getPrice());
        holder.mrp.setText(products.get(position).getMrp());
        holder.mrp_off.setText(products.get(position).getMrp_off());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class OnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView title,price,mrp,mrp_off;
        ItemClickListener mlistener;

        public OnlineViewHolder(@NonNull View itemView,ItemClickListener mlistener) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.online_img);
            title=(TextView)itemView.findViewById(R.id.online_title);
            price=(TextView)itemView.findViewById(R.id.online_price);
            mrp=(TextView)itemView.findViewById(R.id.online_mrp);
            mrp_off=(TextView)itemView.findViewById(R.id.online_mrp_off);
            this.mlistener=mlistener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOlistener.setItemListener(getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        public void setItemListener(int position);
    }


}
