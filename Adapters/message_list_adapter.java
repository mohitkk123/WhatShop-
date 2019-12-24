package com.example.capstoneprototype.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneprototype.DataModels.message_data;
import com.example.capstoneprototype.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class message_list_adapter extends RecyclerView.Adapter<message_list_adapter.myMessageHolder>{
    ArrayList<message_data> mdata;
    chatClickListener mlistener;

    public message_list_adapter(ArrayList<message_data> mdata,chatClickListener mlistener) {
        this.mdata = mdata;
        this.mlistener=mlistener;
    }

    @NonNull
    @Override
    public myMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_chat_layout,parent,false);

        return new myMessageHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull myMessageHolder holder, int position) {
        holder.name.setText(mdata.get(position).getName());
        holder.message.setText(mdata.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,message;
        chatClickListener clickListener;

        public myMessageHolder(@NonNull View itemView,chatClickListener clickListener) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.chatact_name);
            message=(TextView)itemView.findViewById(R.id.chatact_message);
            itemView.setOnClickListener(this);
            this.clickListener=clickListener;

        }

        @Override
        public void onClick(View view) {
            mlistener.chatClick(getAdapterPosition());

        }
    }


    public interface chatClickListener{
        public void chatClick(int position);
    }


}
