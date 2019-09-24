package main.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catherine.latte.ec.R;

import com.catherine.latte.ec.R2;
import com.catherine.latte_core.delegate.bottom.BottomItemDelegate;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.ui.recycler.MultipleItemEntity;
import com.catherine.latte_core.util.log.LatteLogger;

import java.util.ArrayList;

import butterknife.BindView;

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess {
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;

    private ShopCartAdapter mAdapter = null;

    String url = "https://mockapi.eolinker.com/q7ElYxk2985f5c4146686b44c0501e4c43eb1ee3c3c6d35/shopcart";

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url(url)
                .loader(getContext())
                .success(this)
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "网络不通", Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        LatteLogger.d(response);
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter()
                .setJsonData(response).convert();

        mAdapter = new ShopCartAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
