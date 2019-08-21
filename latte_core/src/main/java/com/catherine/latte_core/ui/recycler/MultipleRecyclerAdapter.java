package com.catherine.latte_core.ui.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.catherine.latte_core.R;
import com.catherine.latte_core.ui.banner.BannerCreator;
import com.catherine.latte_core.ui.image.GlideApp;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {
    //确保值加载一次banner,防止每次滑动上来，滑动下去重复加载
    private boolean mIsInitBanner = false;

    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions().centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        //屏蔽具体的convert子类
        return new MultipleRecyclerAdapter(converter.convert());
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return super.createBaseViewHolder(view);
    }

    @Override
    protected void convert(@NonNull MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imgUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext).load(imgUrl).apply(RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.img_single));
                /*Glide.with(mContext)
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into((AppCompatImageView) holder.getView(R.id.img_single));*/
//                GlideApp.with()
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.tv_multiple, text);
                imgUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((AppCompatImageView) holder.getView(R.id.img_multiple));
                //默认使用原缓存
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
        return getData().get(i).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
