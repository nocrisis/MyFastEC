package com.catherine.myfastec;

import android.app.Application;

import com.catherine.latte_core.app.Latte;

import dao.DaoManager;

public class CatherineApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
//                .withApiHost("http://192.168.120.205:91/")
                .withApiHost("http://127.0.0.1/")
//                .withInterceptor(new DebugInterceptor("index",R.raw.test_json))
                .withAppId("")
                .withAppSecret("")
                .configure();
        DaoManager.getInstance().init(this);
        //initStetho();翻不了墙
    }
//chrome://inspect/#devices
   /* private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }*/
}
