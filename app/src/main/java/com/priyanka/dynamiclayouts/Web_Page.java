package com.priyanka.dynamiclayouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_Page extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__page);
        WebView myweb=findViewById(R.id.webView);

        intent=getIntent();

        String url=intent.getStringExtra("site");
        Log.e("intent", "onCreate: url--> "+url );

        myweb.setWebViewClient(new WebViewClient());
        myweb.getSettings().setLoadsImagesAutomatically(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myweb.loadUrl(url);
    }
}