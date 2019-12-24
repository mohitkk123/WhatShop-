package com.example.capstoneprototype.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.DataModels.shopdetails;
import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class horAdapter extends RecyclerView.Adapter<horAdapter.HorViewholder>{
    ArrayList<shopdetails> shops;
    Context context;
    onShopClickLiatener monShopListener;

    public horAdapter(ArrayList<shopdetails> shops, Context context,onShopClickLiatener onShopListener) {
        this.shops = shops;
        this.context=context;
        this.monShopListener=onShopListener;
    }

    @NonNull
    @Override
    public HorViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card_view,parent,false);
        return new HorViewholder(view,monShopListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HorViewholder holder, int position) {
        Glide.with(context).load(shops.get(position).getStorePicUrl()).centerCrop().placeholder(R.drawable.background).into(holder.im);
        holder.tv.setText(""+shops.get(position).getShopname());
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class HorViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView im;
        TextView tv;

        onShopClickLiatener onShopListener;
        public HorViewholder(@NonNull View itemView,onShopClickLiatener onShopListener) {
            super(itemView);
            im=(ImageView)itemView.findViewById(R.id.imag_horrecycler);
            tv=(TextView)itemView.findViewById(R.id.shopname_hor_tv);
            this.onShopListener=onShopListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onShopListener.onShopClick(getAdapterPosition());

        }
    }

    public interface onShopClickLiatener{
        public void onShopClick(int position);
    }
}
