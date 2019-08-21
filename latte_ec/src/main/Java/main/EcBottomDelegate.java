package main;

import com.catherine.latte_core.delegate.bottom.BaseBottomDelegate;
import com.catherine.latte_core.delegate.bottom.BottomItemDelegate;
import com.catherine.latte_core.delegate.bottom.BottomTabBean;
import com.catherine.latte_core.delegate.bottom.ItemBuilder;

import java.util.LinkedHashMap;

import main.index.IndexDelegate;
import main.sort.SortDelegate;

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return 0;
    }
}
