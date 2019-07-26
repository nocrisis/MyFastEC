package com.catherine.myfastec;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

//    @BindView(R.id.textView)
    public TextView txt;


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient() ;

    }

//    @OnClick(R.id.send)
    public void testRestClient() {
        Toast.makeText(getContext(), "发送信息", Toast.LENGTH_LONG).show();
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("Code", "login");
        params.put("Username", "admin");
        params.put("Password", "xsjl2012");
        RestClient.builder()
                .url("ashx/data/login.ashx")
                .params(params)
//                .url("http://news.baidu.com")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("response", response);
//                        txt.setText(response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
//                        txt.setText("on failure");
                        Toast.makeText(getContext(), "on failure", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        Log.e("error", msg);
//                        txt.setText(code + msg);
                    }
                })
                .build()
                .get();
    }
}
