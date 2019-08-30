package main.sort;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.bottom.BottomItemDelegate;

import main.sort.content.ContentDelegate;
import main.sort.title.TitleListDelegate;

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //super.onLazyInitView();如果放在Onbindview里打开index加载出BottomDelegate的时候就已经开始渲染了
    }

    //放在这里才会在点击sort图标时才开始渲染
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final TitleListDelegate listDelegate = new TitleListDelegate();
/*2. loadRootFragment(int containerId, SupportFragment toFragment)
    加载根Fragment，将Fragment事务添加到回退栈中,是栈底元素。
  3. replaceLoadRootFragment(int containerId, SupportFragment toFragment, boolean addToBack)
    以replace方式加载根Fragment， 由addToBack参数决定是否将Fragment事务添加到回退栈。*/
//addToBackStack true ,anim false
        loadRootFragment(R.id.sort_title_container, listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
//        replaceLoadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1), false);
        loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1), false, false);

    }
}
