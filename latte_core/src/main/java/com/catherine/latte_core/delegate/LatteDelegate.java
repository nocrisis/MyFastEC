package com.catherine.latte_core.delegate;

public abstract class LatteDelegate  extends PermissionCheckerDelegate{
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
