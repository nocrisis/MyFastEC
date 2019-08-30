package com.catherine.myfastec;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.RestCreator;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.net.rx.RxRestClient;
import com.catherine.latte_core.ui.LatteLoader;

import java.util.WeakHashMap;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        testRestClient();
//        OnCallRxGet();
        OnCallRxClientGet();
    }

    @OnClick(R2.id.send)
    public void testRestClient() {
        Toast.makeText(getContext(), "发送信息", Toast.LENGTH_LONG).show();
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("Code", "login");
        params.put("Username", "admin");
        params.put("Password", "xsjl2012");
        RestClient.builder()
                .url("ashx/data/login.ashx")
                .params(params)
//                .url("http://127.0.0.1/index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "on failure", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        Log.e("error", msg);
                    }
                })
                .build()
                .get();
    }

    public void OnCallRxGet() {
        final String url = "ashx/data/login.ashx";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("Code", "login");
        params.put("Username", "admin");
        params.put("Password", "xsjl2012");
        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())//使用io线程（或newthread())处理网络逻辑
                .observeOn(AndroidSchedulers.mainThread())//处理结果观察到后在安卓的主线程里处理
                .doOnSubscribe(d -> LatteLoader.showLoading(getContext()))
                .doFinally(() -> LatteLoader.stopLoading())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {//如果不在ui线程中toast会报错
                        Log.i("response", s);
                        Toast.makeText(getContext(), "返回结果" + s + "，在ui线程中", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //不用自己创建CallBack，以及OnRequest的loading，observable有足够各个过程的api
    public void OnCallRxClientGet() {
        final String url = "ashx/data/login.ashx";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("Code", "login");
        params.put("Username", "admin");
        params.put("Password", "xsjl2112");
        RxRestClient.builder().url(url).params(params).build().get()
                .subscribeOn(Schedulers.io())//使用io线程（或newthread())处理网络逻辑
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> LatteLoader.showLoading(getContext()))
                .doFinally(() -> LatteLoader.stopLoading())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), "返回结果" + s + "，在ui线程中", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
