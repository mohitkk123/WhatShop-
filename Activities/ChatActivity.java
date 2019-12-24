package com.example.capstoneprototype.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstoneprototype.Adapters.message_list_adapter;
import com.example.capstoneprototype.DataModels.message_data;
import com.example.capstoneprototype.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements message_list_adapter.chatClickListener{
    ProgressBar chat_load_progress;
    RecyclerView chat_load_rv;

    FirebaseAuth mauth;
    DatabaseReference mref;

    ArrayList<message_data> mdata=new ArrayList<>();
    message_list_adapter madapter;
    ArrayList<String> shop_ids=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat_load_progress=(ProgressBar)findViewById(R.id.chatact_progress);
        chat_load_rv=(RecyclerView)findViewById(R.id.chatact_rv);
        chat_load_rv.setLayoutManager(new LinearLayoutManager(this));
        madapter=new message_list_adapter(mdata,this);
        chat_load_rv.setAdapter(madapter);

        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();



    }

    public void populateChats(){

            chat_load_progress.setVisibility(View.VISIBLE);
            mref.child("Users_Profile").child(mauth.getCurrentUser().getUid()).child("Chats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            shop_ids.add(dataSnapshot1.getKey().toString());
                            mref.child("Businesses").child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (mdata != null) {
                                        mdata.clear();
                                    }

                                    final String shop_name = dataSnapshot.child("shopname").getValue().toString();
                                    // Toast.makeText(ChatActivity.this, ""+shop_name, Toast.LENGTH_SHORT).show();
                                    Query query = mref.child("Businesses").child(dataSnapshot1.getKey()).child("Chats").child(mauth.getCurrentUser().getUid()).orderByKey().limitToLast(1);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                chat_load_progress.setVisibility(View.INVISIBLE);
                                                for (DataSnapshot dataSnapshotfinal : dataSnapshot.getChildren()) {
                                                    for (DataSnapshot dataSnapshot2 : dataSnapshotfinal.getChildren()) {
                                                        message_data messageData = new message_data(shop_name, dataSnapshot2.getValue().toString());
                                                        // Toast.makeText(ChatActivity.this, ""+dataSnapshot2.getValue().toString(), Toast.LENGTH_SHORT).show();  message
                                                        mdata.add(messageData);
                                                    }
                                                }
                                                madapter.notifyDataSetChanged();
                                                // Toast.makeText(ChatActivity.this, ""+dataSnapshot.getkey().toString(), Toast.LENGTH_SHORT).show(); later  sender user key
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    @Override
    public void chatClick(int position) {
        Toast.makeText(this, "clicked "+position+" "+shop_ids.get(position), Toast.LENGTH_SHORT).show();
        Intent i=new Intent(ChatActivity.this,ChatMessageActivity.class);
        i.putExtra("shop_ids",shop_ids.get(position));
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            populateChats();

        }else{
            Toast.makeText(this, "Login first", Toast.LENGTH_LONG).show();
            Intent i=new Intent(ChatActivity.this,LoginAct.class);
            startActivity(i);

        }
    }
}
