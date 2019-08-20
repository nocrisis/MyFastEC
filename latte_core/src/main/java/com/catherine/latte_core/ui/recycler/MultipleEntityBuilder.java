package com.catherine.latte_core.ui.recycler;

import java.util.LinkedHashMap;

//builder模式可以吧builder也在MultipleEntity的 静态内部类，这里代码太长，所以分开
public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //先清除之前的数据
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);//??key相同什么意义
        return this;//答：相同key即同一类的数据一起好后续处理，如view展示
    }
//将json解析出来的数据按不同的key(如item)分类，放在一起
    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
