package detail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.LatteDelegate;

public class GoodsDetailDelegate extends LatteDelegate {
    public static GoodsDetailDelegate create() {
        return new GoodsDetailDelegate();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
