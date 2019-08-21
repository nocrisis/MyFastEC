package com.catherine.latte_core.delegate;

public abstract class LatteDelegate  extends PermissionCheckerDelegate{
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        //SwipeBackFragment中获取父级容器
        return (T) getParentFragment();
    }
}
