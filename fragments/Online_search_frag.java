package com.example.capstoneprototype.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.Online_Product_details_Act;
import com.example.capstoneprototype.Activities.ProductSearchAct;
import com.example.capstoneprototype.Adapters.onlineP_recyclerAdapter;
import com.example.capstoneprototype.DataModels.online_product_data;
import com.example.capstoneprototype.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Online_search_frag extends Fragment implements ProductSearchAct.sendDataListenerB,onlineP_recyclerAdapter.ItemClickListener {
String search_data;
ProgressBar progress_online;

    String SEARCH_SITE="https://www.snapdeal.com/search?keyword=";

RecyclerView rv_online;


    onlineP_recyclerAdapter adapter;
    ArrayList<online_product_data> products=new ArrayList<>();


    public Online_search_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_online_search_frag, container, false);

        ((ProductSearchAct)getActivity()).setSenddataListener_setterB(this);

        progress_online=(ProgressBar)v.findViewById(R.id.progress_online_search);
        rv_online=(RecyclerView)v.findViewById(R.id.recycler_online);
        adapter=new onlineP_recyclerAdapter(products,getContext(),this);
        rv_online.setAdapter(adapter);



        return v;
    }


    @Override
    public void sendSearchDB(String s) {
        if(products!=null){
            products.clear();
        }
        progress_online.setVisibility(View.VISIBLE);
     OnlineAsync startOnlineSearch=new OnlineAsync();
     startOnlineSearch.execute(s);




    }

    @Override
    public void setItemListener(int position) {
        Toast.makeText(getContext(), "clicked "+position, Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getContext(), Online_Product_details_Act.class);
        i.putExtra("onlineact_data",products.get(position));
        startActivity(i);

    }

    public class OnlineAsync extends AsyncTask<String,Integer, ArrayList<online_product_data>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected ArrayList<online_product_data> doInBackground(String... strings) {
            String SEARCH_SITE="https://www.snapdeal.com/search?keyword=";
            ArrayList<online_product_data> products=new ArrayList<>();
            String search_main=strings[0].replaceAll("\\s+","+");

            try{

                Document document = Jsoup.connect(SEARCH_SITE + search_main).get();

                Elements elements = document.select(".col-xs-6.favDp.product-tuple-listing.js-tuple");
                for (Element element : elements) {
                    String title= element.select(".product-title").text();  //product title
                    String price=element.select(".lfloat.product-price").text(); // product price;

                    String pic=element.getElementsByTag("source").attr("srcset"); //product pictures
                    String link=element.select(".dp-widget-link").attr("href"); //product links;
                    String mrp=element.select(".lfloat.product-desc-price.strike").text();
                    String mrp_off=element.select(".product-discount").text();

                    products.add(new online_product_data(title,price,pic,link,mrp,mrp_off));
                    Log.d("data ", "run: "+mrp+"\n");

                }


            }catch (IOException e){

            }
            return products;
        }


        @Override
        protected void onPostExecute(ArrayList<online_product_data> online_product_Data) {
            super.onPostExecute(online_product_Data);
            progress_online.setVisibility(View.INVISIBLE);

            rv_online.setLayoutManager(new LinearLayoutManager(getContext()));





            products.addAll(online_product_Data);
            adapter.notifyDataSetChanged();

            Toast.makeText(getContext(), " \n"+online_product_Data.size(), Toast.LENGTH_SHORT).show();
        }
    }


}
