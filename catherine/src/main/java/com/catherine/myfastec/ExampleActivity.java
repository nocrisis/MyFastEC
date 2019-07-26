package com.catherine.myfastec;

import com.catherine.latte_core.activities.ProxyActivity;
import com.catherine.latte_core.delegate.LatteDelegate;

public class ExampleActivity extends ProxyActivity {
    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
