package com.example.capstoneprototype.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstoneprototype.Adapters.searchPagerAdapter;
import com.example.capstoneprototype.R;
import com.google.android.material.tabs.TabLayout;

public class ProductSearchAct extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    searchPagerAdapter searchpageradapter;
    EditText edit_search;
    String search_string;

    sendDataListener senddataListener;
    sendDataListenerB senddataListenerB;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        intent=getIntent();
        edit_search=(EditText)findViewById(R.id.searchAct_search) ;

        if(intent!=null){
            search_string=intent.getExtras().getString("search_da");
            edit_search.setText(search_string);

        }

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    search_string=edit_search.getText().toString();
                    senddataListener.sendSearchD(search_string);
                    senddataListenerB.sendSearchDB(search_string);
                    return true;
                }
                return false;
            }
        });
        viewPager=(ViewPager)findViewById(R.id.searchfrag_viewpager);
        tabLayout=(TabLayout)findViewById(R.id.search_tab);
        searchpageradapter=new searchPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(searchpageradapter);
        tabLayout.setupWithViewPager(viewPager);

    }
//for nearby frag
  public  interface sendDataListener{
        public void sendSearchD(String s);
  }

  public void setSenddataListener_setter(sendDataListener listener){
        this.senddataListener=listener;
  }


  //for online frad

    public  interface sendDataListenerB{
        public void sendSearchDB(String s);
    }

    public void setSenddataListener_setterB(sendDataListenerB listener){
        this.senddataListenerB=listener;
    }


}
