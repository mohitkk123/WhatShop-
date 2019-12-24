package com.example.capstoneprototype.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstoneprototype.Adapters.messages_adapter;
import com.example.capstoneprototype.DataModels.message_data;
import com.example.capstoneprototype.DataModels.productDetails;
import com.example.capstoneprototype.DataModels.shopdetails;
import com.example.capstoneprototype.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatMessageActivity extends AppCompatActivity {


    FirebaseAuth mauth;
    DatabaseReference mref;

    productDetails product;
    shopdetails shop;

    EditText  message;
    ProgressBar message_progress;
    RecyclerView messages_rv;
    String user_name;
    String shop_id,shop_message_id;

    ArrayList<message_data> mdata=new ArrayList<>();
    messages_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();
        message_progress=(ProgressBar)findViewById(R.id.chatmact_progress);
        messages_rv=(RecyclerView)findViewById(R.id.chatmact_recycler) ;
        messages_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new messages_adapter(mdata);
        messages_rv.setAdapter(adapter);

        message=(EditText)findViewById(R.id.chatmact_mess_edit);

        if(getIntent()!=null){
            product=getIntent().getParcelableExtra("pd");
            shop=getIntent().getParcelableExtra("shop data");
            if(product!=null){
                shop_message_id=product.getShop_id();
            }
            else if(shop!=null){
                shop_message_id=shop.getStoreId();
            }
            else{

                shop_id=getIntent().getExtras().getString("shop_ids");
                shop_message_id=shop_id;
            }

        }
        if(mauth.getCurrentUser()==null){
            Toast.makeText(this, "You have to login first", Toast.LENGTH_LONG).show();
            Intent i=new Intent(ChatMessageActivity.this,LoginAct.class);
            startActivity(i);
        }else {
           mref.child("Users_Profile").child(mauth.getCurrentUser().getUid()).child("user_name").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   user_name=dataSnapshot.getValue().toString();
                   Toast.makeText(ChatMessageActivity.this, ""+user_name, Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });


            populateMessages();
        }
    }



    public void chatmact_send(View view) {
        message_progress.setVisibility(View.VISIBLE);


        //saving message in users child


            mref.child("Users_Profile").child(mauth.getCurrentUser().getUid()).child("Chats").child(shop_message_id).push().child("You").setValue(message.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    mref.child("Businesses").child(shop_message_id).child("Chats").child(mauth.getCurrentUser().getUid()).push().child(user_name).setValue(message.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChatMessageActivity.this, "message sent", Toast.LENGTH_SHORT).show();
                            message_progress.setVisibility(View.GONE);
                            message.setText("");
                        }
                    });
                }
            });






    }
// loading messages
    private void populateMessages() {
        message_progress.setVisibility(View.VISIBLE);
        mref.child("Users_Profile").child(mauth.getCurrentUser().getUid()).child("Chats").child(shop_message_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mdata!=null){
                    mdata.clear();
                }
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        for(DataSnapshot dataSnapshot11:dataSnapshot1.getChildren()){

                            message_data messageData=new message_data(dataSnapshot11.getKey().toString(),dataSnapshot11.getValue().toString());
                            mdata.add(messageData);
                        }

                    }
                    message_progress.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();

                }else{
                    message_progress.setVisibility(View.GONE);
                    Toast.makeText(ChatMessageActivity.this, "start a new chat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
