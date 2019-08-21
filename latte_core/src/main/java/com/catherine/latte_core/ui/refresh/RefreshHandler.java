package com.catherine.latte_core.ui.refresh;

import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.ui.recycler.DataConverter;
import com.catherine.latte_core.ui.recycler.MultipleRecyclerAdapter;
import com.catherine.latte_core.util.log.LatteLogger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    public RefreshHandler(SwipeRefreshLayout refresh_layout, PagingBean bean,
                          RecyclerView recyclerview, DataConverter converter) {
        this.REFRESH_LAYOUT = refresh_layout;
        this.BEAN = bean;
        this.RECYCLERVIEW = recyclerview;
        this.CONVERTER = converter;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    /*创建时传入具体convert的子类*/
    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,
                                        RecyclerView recyclerview, DataConverter converter) {
        return new RefreshHandler(refresh_layout, new PagingBean(), recyclerview, converter);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
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
                       /* final IndexDataConverter converter = new IndexDataConverter();
                        converter.setJsonData(response);
                        final ArrayList<MultipleItemEntity> list = converter.convert();
                        final String image = list.get(1).getField(MultipleFields.IMAGE_URL);
                        Toast.makeText(Latte.getApplicationContext(), image, Toast.LENGTH_LONG).show();*/
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("pageSize"));
                        //设置Adapter的数据
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        //上拉加载的监听
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                        //第一次加载第一页时，告诉我们现在的pageIndex已经加一了

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        LatteLogger.d("获取index数据失败");
                        Toast.makeText(Latte.getApplicationContext(), "获取index数据失败", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LatteLogger.d("获取index数据错误:" + msg);
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

    @Override
    public void onLoadMoreRequested() {

    }
}
