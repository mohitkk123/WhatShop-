package com.example.capstoneprototype.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.capstoneprototype.R;

public class OnlineP_webviewAct extends AppCompatActivity {
    WebView browser_window;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_p_webview);
        browser_window=(WebView)findViewById(R.id.online_web);

        WebSettings webSettings = browser_window.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //browser_window.setWebViewClient(new WebViewClient());



        Intent i=getIntent();
        if(i!=null){
            link=i.getExtras().getString("link_data");


        }

        browser_window.setWebViewClient(new WebViewClient());
browser_window.loadUrl(link);



        Toast.makeText(this, ""+link, Toast.LENGTH_SHORT).show();

    }
}
