package com.catherine.latte_core.ui.refresh;

import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;
import com.orhanobut.logger.Logger;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url) {
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Logger.d(response);
                        Toast.makeText(Latte.getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Logger.d("获取index数据失败");
                        Toast.makeText(Latte.getApplicationContext(), "获取index数据失败", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Logger.d("获取index数据错误:" + msg);
                        Toast.makeText(Latte.getApplicationContext(), "获取index数据错误:" + msg, Toast.LENGTH_LONG).show();

                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
