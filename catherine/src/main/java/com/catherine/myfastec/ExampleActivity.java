package com.catherine.myfastec;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.catherine.latte_core.activities.ProxyActivity;
import com.catherine.latte_core.delegate.LatteDelegate;

import launcher.LauncherDelegate;

public class ExampleActivity extends ProxyActivity {
    @Override
    public LatteDelegate setRootDelegate() {
//        return new ExampleDelegate();
        return new LauncherDelegate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
