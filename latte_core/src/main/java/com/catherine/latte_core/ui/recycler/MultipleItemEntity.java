package com.catherine.latte_core.ui.recycler;



import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;


public class MultipleItemEntity implements MultiItemEntity {
                //内存不够的时候进行释放
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

    MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
        //.get()重新获得对该实例的强引用。而回收之后，调用get()方法就只能得到null了
        FIELDS_REFERENCE.get().putAll(fields);
    }

    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        //返回整个map
        return FIELDS_REFERENCE.get();
    }

    public final MultipleItemEntity setField(Object key, Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }
}
