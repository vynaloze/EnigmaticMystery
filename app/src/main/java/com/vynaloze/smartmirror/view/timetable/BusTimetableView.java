package com.vynaloze.smartmirror.view.timetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vynaloze.smartmirror.util.ApplicationContextProvider;

import java.io.InputStream;

public class BusTimetableView extends WebView {
    public BusTimetableView(Context context) {
        super(context);
        initialiseView();
    }

    public BusTimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiseView();
    }

    public BusTimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiseView();
    }

    public BusTimetableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialiseView();
    }

    public BusTimetableView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        initialiseView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initialiseView() {
        this.getSettings().setJavaScriptEnabled(true);
        this.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                injectCSS(view);
                super.onPageFinished(view, url);
            }
        });
        this.loadUrl("http://rozklady.lodz.pl/Home/TimeTableReal?busStopId=1460");
        this.scrollTo(5, 85);
        this.setInitialScale(50);
    }

    private void injectCSS(WebView webView) {
        try {
            InputStream inputStream = ApplicationContextProvider.getContext().getAssets().open("style.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
