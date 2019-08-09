package main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.bottom.BottomItemDelegate;

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
