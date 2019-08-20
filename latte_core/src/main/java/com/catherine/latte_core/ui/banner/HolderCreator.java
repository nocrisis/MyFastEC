package com.catherine.latte_core.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class HolderCreator implements CBViewHolderCreator {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
