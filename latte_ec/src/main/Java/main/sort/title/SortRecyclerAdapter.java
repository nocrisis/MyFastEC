package main.sort.title;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.ui.recycler.ItemType;
import com.catherine.latte_core.ui.recycler.MultipleFields;
import com.catherine.latte_core.ui.recycler.MultipleItemEntity;
import com.catherine.latte_core.ui.recycler.MultipleRecyclerAdapter;
import com.catherine.latte_core.ui.recycler.MultipleViewHolder;


import java.util.List;

import main.sort.SortDelegate;
import main.sort.content.ContentDelegate;

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {
    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;

        //添加标题菜单布局
        addItemType(ItemType.TITLE_MENU_LIST, R.layout.item_title_menu);

    }

    @Override
    protected void convert(@NonNull final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch ((holder.getItemViewType())) {
            case ItemType.TITLE_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_title_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int curPosition = holder.getAdapterPosition();
                        if (mPrePosition != curPosition) {
                            //还原上一个未点击状态
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);
                            // 更新选中的item
                            /*final Box boxobj = new Box(25);只是说明了Box所生成的实例是最终的，引用不可变
                            但这个实例的成员变量如果没有被final注明了就还是可以改变的，所以boxobj.height = 32;是可以的*/
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(curPosition);
                            mPrePosition = curPosition;

                            final int contentId = getData().get(curPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    //选中左侧橙色阴影
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_title_item_name, text);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate) {
        final LatteDelegate contentDelegate = DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.replaceFragment(delegate, false);
        }
    }
}
