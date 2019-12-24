package com.example.capstoneprototype.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.ProductSearchAct;
import com.example.capstoneprototype.Adapters.searchPagerAdapter;
import com.example.capstoneprototype.MainActivity;
import com.example.capstoneprototype.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class searchFrag extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    EditText edit_search;
    searchPagerAdapter searchpageradapter;
    searchListener mlistener;

    public searchFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_search, container, false);
        edit_search=(EditText)v.findViewById(R.id.searchfrag_search);







        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    Intent intent=new Intent(getContext(), ProductSearchAct.class);
                    intent.putExtra("search_da",edit_search.getText().toString().trim());
                    startActivity(intent);
                }
                return false;
            }
        });

        return v;
    }

    public void  sendChildData(String search_data){
        Toast.makeText(getContext(), "data recieved", Toast.LENGTH_SHORT).show();


    }


    public interface searchListener{
        public void sendSearch(String data);
    }




}
