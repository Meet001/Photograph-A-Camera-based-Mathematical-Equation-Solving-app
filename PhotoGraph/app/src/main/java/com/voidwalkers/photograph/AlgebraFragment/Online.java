package com.voidwalkers.photograph.AlgebraFragment;


import android.content.Intent;
import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.voidwalkers.photograph.GlobalMemory;
import com.voidwalkers.photograph.Latex;
import com.voidwalkers.photograph.MainActivity;
import com.voidwalkers.photograph.R;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.voidwalkers.photograph.ScannerFragment.CameraFragment.latexInput;
/**
 * Created by Wangchuk on 24-10-2017.
 */

/**
 *   Creating a WebView to display
 *   the relevant Wolfram Alpha page.
 *   This is only attempted if the input isn't supported yet.
 *
 */

public class Online extends AppCompatActivity {

    @Override
    /**
     * Creates the activity
     */
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        Log.e("TAG23", "Getting started");


        WebView mypage = (WebView) findViewById(R.id.function_webview);

        mypage.setWebViewClient(new myWebClient());
        WebSettings webSettings = mypage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //   mypage.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        // String keystring = latexInput.replaceAll("\\+", "%2B");

        try {
            String encodedString = URLEncoder.encode(Latex.latexInput, "UTF-8");
            mypage.loadUrl("http://m.wolframalpha.com/input/?i=" + encodedString);
        }
        catch(UnsupportedEncodingException e){
        }


        //   mypage.loadUrl("http://api.wolframalpha.com/v1/simple?appid=Q5AE5H-Q9AWKUUYKK&layout=labelbar&i="+encodedString);

        //Log.i("TAG23", "Webpage launched");n

    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }

    /**
     * Extends the WebViewClient to allow the webpage to be
     * displayed within the activity itself.
     */
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            view.loadUrl(url);
            return true;

        }
    }

}