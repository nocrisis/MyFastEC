package main.discovery;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.bottom.BottomItemDelegate;
import com.catherine.latte_core.delegate.web.WebDelegateImpl;

public class DiscoveryDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_discovery;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("test.html");
        delegate.setTopDelegate(this.getParentDelegate());//bottomBarDelegate
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container,delegate);
    }
}
