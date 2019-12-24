package com.example.capstoneprototype.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class verAdapter extends RecyclerView.Adapter<verAdapter.VeriewHolder>{
    ArrayList<productDetails> products;
    Context context;
    onProductClickListener onProductListener;

    public verAdapter(ArrayList<productDetails> products, Context context,onProductClickListener onProductListener) {
        this.products = products;
        this.context = context;
        this.onProductListener=onProductListener;
    }

    @NonNull
    @Override
    public VeriewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.porduct_card_view,parent,false);

        return new VeriewHolder(view,onProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VeriewHolder holder, int position) {
        Glide.with(context).load(products.get(position).getPimageurl().toString()).into(holder.img);
        holder.tv.setText("\u20B9 "+products.get(position).getPprice());
        holder.tv_pname.setText(products.get(position).getPname());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class VeriewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView tv,tv_pname;
        onProductClickListener onProductListener;

        public VeriewHolder(@NonNull View itemView,onProductClickListener onProductListener) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.imag_verrecycler);
            tv=(TextView)itemView.findViewById(R.id.price_ver_tv);
            tv_pname=(TextView)itemView.findViewById(R.id.pname_ver_tv);
            this.onProductListener=onProductListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
        onProductListener.onProductclick(getAdapterPosition());

        }
    }

    public interface onProductClickListener{
        public void onProductclick(int postion);


    }
}
