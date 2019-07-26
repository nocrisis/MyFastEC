package com.catherine.latte_core.net.callback;

import android.os.Handler;
import android.util.Log;

import com.catherine.latte_core.ui.LatteLoader;
import com.catherine.latte_core.ui.LoadingStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoadingStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();//static避免异步内存泄漏

    public RequestCallbacks(IRequest request, ISuccess success, IError error, IFailure failure, LoadingStyle loadingStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.LOADER_STYLE = loadingStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {//call已经执行
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        if (LOADER_STYLE != null) {
            stopLoading();
        }
    }

    private void stopLoading() {
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        if (LOADER_STYLE != null) {
            stopLoading();
        }
        Log.e("onFailure", t.getMessage());
    }
}
