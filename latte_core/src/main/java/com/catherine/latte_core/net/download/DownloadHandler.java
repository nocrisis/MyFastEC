package com.catherine.latte_core.net.download;

import android.os.AsyncTask;

import com.catherine.latte_core.net.RestCreator;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.IRequest;
import com.catherine.latte_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String url;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest request;
    private final ISuccess success;
    private final IError error;
    private final IFailure failure;
    private final String downloadDir;
    private final String extension;
    private final String downloadName;

    public DownloadHandler(String URL, IRequest request, ISuccess success, IError error,
                           IFailure failure, String dir, String extension,
                           String name) {
        this.url = URL;
        this.request = request;
        this.success = success;
        this.error = error;
        this.failure = failure;
        this.downloadDir = dir;
        this.extension = extension;
        this.downloadName = name;
    }

    public final void handleDownload() {
        if (request != null) {
            request.onRequestStart();
        }
        RestCreator.getRestService().download(url, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(request, success, error, failure);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downloadDir, extension, responseBody, downloadName);
                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (request != null) {
                                    request.onRequestEnd();
                                }
                            } else {
                                if (error != null) {
                                    error.onError(response.code(), response.message());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (failure != null) {
                            failure.onFailure();
                        }
                    }
                });

    }
}
