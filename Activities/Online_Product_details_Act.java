package com.example.capstoneprototype.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstoneprototype.Adapters.onlineact_adapter;
import com.example.capstoneprototype.DataModels.online_product_data;
import com.example.capstoneprototype.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Online_Product_details_Act extends AppCompatActivity {
    ImageView img;
    TextView title,price,mrp,mrp_off;
    ProgressBar progress;
    RecyclerView onlineact_rv;
    online_product_data onlineD;
    onlineact_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online__product_details_);
        img=(ImageView)findViewById(R.id.onlineact_product);
        title=(TextView)findViewById(R.id.onlineact_title);
        price=(TextView)findViewById(R.id.onlineact_price);
        mrp=(TextView)findViewById(R.id.onlineact_mrp);
        mrp_off=(TextView)findViewById(R.id.onlineact_mrp_off);
        progress=(ProgressBar)findViewById(R.id.onlineact_progress);
        onlineact_rv=(RecyclerView)findViewById(R.id.onlineact_recycler);
        onlineact_rv.setLayoutManager(new LinearLayoutManager(this));

        Intent i=getIntent();
        if(i!=null){

             onlineD=i.getParcelableExtra("onlineact_data");
            Glide.with(this).load(onlineD.getPic()).into(img);
            title.setText(onlineD.getTitle());
            price.setText(onlineD.getPrice());
            mrp.setText(onlineD.getMrp());
            mrp_off.setText(onlineD.getMrp_off());

        }

        getOnlineData(onlineD.getLink());

    }

    public void getOnlineData(String link){
        progress.setVisibility(View.VISIBLE);
        MyAsyncTask task1=new MyAsyncTask();
        task1.execute(link);

    }
// web view activity
    public void onlineact_gotoWeb(View view) {
        Intent i=new Intent(Online_Product_details_Act.this,OnlineP_webviewAct.class);
        i.putExtra("link_data",onlineD.getLink());
        startActivity(i);
    }

    public class MyAsyncTask extends AsyncTask<String,Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> pdes=new ArrayList<>();
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements elements=document.select(".dtls-li");

                for(Element element:elements){
                    String des=element.getElementsByClass("h-content").text();

                    pdes.add(des);

                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            return pdes;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            adapter=new onlineact_adapter(strings);
            onlineact_rv.setAdapter(adapter);
            progress.setVisibility(View.INVISIBLE);

            Log.d("dataaaaaaaaaaaaaa", "onPostExecute: "+strings.toString());

        }
    }
}
