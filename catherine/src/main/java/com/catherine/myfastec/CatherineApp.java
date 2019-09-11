package com.catherine.myfastec;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.delegate.web.event.TestEvent;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import dao.DaoManager;
import icon.FontEcModule;

public class CatherineApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withApiHost("http://192.168.120.205:91/")
                .withApiHost("https://www.easy-mock.com/mock/5d423b5993d2407e9ef5afc7/")
//                .withInterceptor(new DebugInterceptor("index",R.raw.test_json))
                /*.withAppId("")
                .withAppSecret("")*/
                .withJSInterface("latte")//js调用java的名称
                .withWebEvent("test",new TestEvent())
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
