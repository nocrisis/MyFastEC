package com.catherine.latte_core.net.rx;

import android.content.Context;

import com.catherine.latte_core.net.RestCreator;
import com.catherine.latte_core.ui.LoadingStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {
    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private LoadingStyle mLoaderStyle = null;
    private Context mContext = null;
    private File mFile = null;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(LoadingStyle style, Context context) {
        this.mLoaderStyle = style;
        this.mContext = context;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mLoaderStyle = LoadingStyle.BallClipRotatePulseIndicator;
        this.mContext = context;
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mBody, mLoaderStyle, mContext, mFile);
    }
}
