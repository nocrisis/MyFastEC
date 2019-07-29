package com.catherine.latte_core.net.rx;

import android.content.Context;

import com.catherine.latte_core.net.HttpMethod;
import com.catherine.latte_core.net.RestCreator;
import com.catherine.latte_core.ui.LatteLoader;
import com.catherine.latte_core.ui.LoadingStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final File FILE;
    private final LoadingStyle LOADER_STYLE;
    private final Context CONTEXT;


    public RxRestClient(String url, Map<String, Object> params, RequestBody body,
                        LoadingStyle loadingStyle, Context context, File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.LOADER_STYLE = loadingStyle;
        this.CONTEXT = context;
        this.FILE = file;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(
                        MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part
                        .createFormData("file", FILE.getName(), requestBody);
                observable = RestCreator.getRxRestService().upload(URL, body);
            default:
                break;
        }
        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("POST_RAW params must be null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("PUT_RAW params must be null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<ResponseBody> download() {
        final Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestService().download(URL, PARAMS);
        return responseBodyObservable;
    }
}
