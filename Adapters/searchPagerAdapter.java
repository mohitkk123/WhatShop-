package com.example.capstoneprototype.Adapters;

import com.example.capstoneprototype.fragments.Nearby_search_frag;
import com.example.capstoneprototype.fragments.Online_search_frag;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class searchPagerAdapter extends FragmentStatePagerAdapter{


    public searchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="Nearby";
        }if(position==1){
            title="Online";
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Nearby_search_frag();
        }else {
            return new Online_search_frag();
        }

    }
}
