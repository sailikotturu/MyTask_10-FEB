package com.example.sailik.mytask_10_feb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NextScreenActivity extends AppCompatActivity {

    private WebView mWebpageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_screen);

        mWebpageView = (WebView) findViewById(R.id.webpageview);

        Intent intent = getIntent();
        String url_string = intent.getStringExtra("message");


        mWebpageView  = new WebView(this);

        mWebpageView.getSettings().setJavaScriptEnabled(true);



        mWebpageView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                
                Toast.makeText(NextScreenActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        mWebpageView .loadUrl(url_string);
        setContentView(mWebpageView );

    }
}
