package com.catherine.latte_core.delegate.web.event;

import android.webkit.WebView;
import android.widget.Toast;

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_LONG).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            //依然报错，不知原因
//如果App与JS存在嵌套调用（即A调用B，B内部又去调用A），那么务必要保证两个调用在同一个线程中，不然运行时会报错“java.lang.Throwable: A WebView method was called on thread 'JavaBridge'. All WebView methods must be called on the same thread.”。
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("javascript:nativeCall()",null);
//                    webView.loadUrl("javascript:nativeCall()");会重新加载页面
                }
            });
        }
        return null;
    }
}
