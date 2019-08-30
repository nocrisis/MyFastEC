package com.catherine.latte_core.ui.recycler;

import java.util.ArrayList;


public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;
    //?类似 Template Method
/*本方法的精髓，不同的pojo使用不同的子类的convert去实现，convert调用时不需要知道子类，只在创建时传入具体的子类，factoryMethod*/
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }
//获取返回json全部字段
    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("RESULT'S DATA IS NULL!");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITIES.clear();
    }
}
