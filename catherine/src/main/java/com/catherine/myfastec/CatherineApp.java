package com.catherine.myfastec;

import android.app.Application;

import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.net.Interceptors.DebugInterceptor;

public class CatherineApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://192.168.120.205:91/")
//                .withApiHost("http://127.0.0.1/")
//                .withInterceptor(new DebugInterceptor("index",R.raw.test_json))
                .configure();
    }
}
