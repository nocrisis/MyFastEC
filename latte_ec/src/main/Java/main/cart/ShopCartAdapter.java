package main.cart;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.catherine.latte.ec.R;
import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.ui.recycler.MultipleFields;
import com.catherine.latte_core.ui.recycler.MultipleItemEntity;
import com.catherine.latte_core.ui.recycler.MultipleRecyclerAdapter;
import com.catherine.latte_core.ui.recycler.MultipleViewHolder;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

public class ShopCartAdapter extends MultipleRecyclerAdapter {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);

    }

    @Override
    protected void convert(@NonNull MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                //取出所有控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);
                //赋值
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvTitle.setText(title);
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);
                //根据数据状态显示左侧√circle
                if (isSelected) {
                    iconIsSelected.setTextColor(
                            ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main)
                    );
                } else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧√circle点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            iconIsSelected.setTextColor(
                                    ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main)
                            );
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);

                        }
                    }
                });
                break;
        }
    }
}
