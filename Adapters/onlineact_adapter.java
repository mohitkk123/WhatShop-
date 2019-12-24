package com.example.capstoneprototype.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class onlineact_adapter extends RecyclerView.Adapter<onlineact_adapter.Myviewholder> {
    ArrayList<String> pdes=new ArrayList<>();

    public onlineact_adapter(ArrayList<String> pdes) {
        this.pdes = pdes;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlineact_recycler_card_layout,parent,false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.highlight.setText((position+1)+". "+pdes.get(position));

    }

    @Override
    public int getItemCount() {
        return pdes.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{
        TextView highlight;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            highlight=(TextView)itemView.findViewById(R.id.onlineact_product_high_data);
        }
    }
}
