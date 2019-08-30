package com.catherine.latte_core.net;

import android.content.Context;

import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.IRequest;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.net.callback.RequestCallbacks;
import com.catherine.latte_core.net.download.DownloadHandler;
import com.catherine.latte_core.ui.LatteLoader;
import com.catherine.latte_core.ui.LoadingStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String DOWNLOAD_NAME;
    private final RequestBody BODY;
    private final File FILE;
    private final LoadingStyle LOADER_STYLE;
    private final Context CONTEXT;


    public RestClient(String url, Map<String, Object> params, IRequest request,
                      ISuccess success, IError error, IFailure failure, RequestBody body,
                      LoadingStyle loadingStyle, Context context, File file,
                      String downloadDir, String extension, String downloadName) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.LOADER_STYLE = loadingStyle;
        this.CONTEXT = context;
        this.FILE = file;
        this.DOWNLOAD_NAME = downloadName;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;

    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(
                        MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part
                        .createFormData("file", FILE.getName(), requestBody);
                call = RestCreator.getRestService().upload(URL, body);
            case UPLOAD_PARAM:
                MultipartBody.Builder builder = new MultipartBody.Builder();
                for (Map.Entry<String, Object> entry : PARAMS.entrySet()) {
                    String key = entry.getKey();
                    String value = null;
                    //空值处理
                    if (entry.getValue() == null) {
                        value = "";
                    } else {
                        value = entry.getValue().toString();
                    }
                    builder.addFormDataPart(key, value);
                }
                final RequestBody fileBody = RequestBody.create(MediaType.parse(
                        MultipartBody.FORM.toString()), FILE);
//此方法报错 final MultipartBody multipartBody = builder.addFormDataPart("file", FILE.getName(), fileBody).build();
                final MultipartBody.Part part = MultipartBody.Part
                        .createFormData("file", FILE.getName(), fileBody);
                MultipartBody multipartBody = builder
                        .addPart(part)
                        .setType(MultipartBody.FORM)
                        .build();
                call = RestCreator.getRestService().uploadParam(URL, multipartBody);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
    }

    private Callback<String> getRequestCallBack() {
        return new RequestCallbacks(REQUEST, SUCCESS, ERROR, FAILURE, LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("POST_RAW params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PUT_RAW params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void uploadParam() {
        request(HttpMethod.UPLOAD_PARAM);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, SUCCESS, ERROR, FAILURE, DOWNLOAD_DIR,
                EXTENSION, DOWNLOAD_NAME).handleDownload();
    }
}
