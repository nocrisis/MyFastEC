package com.catherine.latte_core.delegate.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.catherine.latte_core.delegate.LatteDelegate;

public abstract class Event implements IEvent {
    private Context mContext = null;
    private String mAction = null;
    private LatteDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Context getContext() {
        return mContext;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(LatteDelegate delegate) {
        this.mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
