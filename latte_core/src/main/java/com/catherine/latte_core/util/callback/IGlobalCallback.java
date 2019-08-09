package com.catherine.latte_core.util.callback;


import androidx.annotation.Nullable;

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
