package com.catherine.latte_core.delegate.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public interface IWebViewInitializer {
    WebView initWebView(WebView  webView);

    //浏览器本身
    WebViewClient initWebViewClient();

    //内部页面
    WebChromeClient initWebChromeClient();
}
