package com.example.capstoneprototype.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneprototype.DataModels.message_data;
import com.example.capstoneprototype.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class messages_adapter extends RecyclerView.Adapter<messages_adapter.myMessageViewholder> {

    ArrayList<message_data> messageData;

    public messages_adapter(ArrayList<message_data> messageData) {
        this.messageData = messageData;
    }

    @NonNull
    @Override
    public myMessageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false);
        return new myMessageViewholder(v);
    }

    @Override
    public int getItemCount() {
        return messageData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull myMessageViewholder holder, int position) {
        holder.name.setText(messageData.get(position).getName());
        holder.message.setText(messageData.get(position).getMessage());

    }

    public class myMessageViewholder extends RecyclerView.ViewHolder{
        TextView name,message;


        public myMessageViewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.chatmact_name);
            message=(TextView)itemView.findViewById(R.id.chatmact_message);
        }
    }
}
