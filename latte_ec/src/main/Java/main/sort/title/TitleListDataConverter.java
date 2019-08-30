package main.sort.title;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catherine.latte_core.ui.recycler.DataConverter;
import com.catherine.latte_core.ui.recycler.ItemType;
import com.catherine.latte_core.ui.recycler.MultipleFields;
import com.catherine.latte_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class TitleListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.TITLE_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    //title menu 是否点击标志
                    .setField(MultipleFields.TAG, false)
                    .build();
            dataList.add(entity);
            //设置第一个被选中的title menu
        }
        dataList.get(0).setField(MultipleFields.TAG, true);
        return dataList;
    }
}
