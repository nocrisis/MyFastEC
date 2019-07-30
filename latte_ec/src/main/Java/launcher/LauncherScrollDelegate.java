package launcher;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.catherine.latte.ec.R;
import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.ui.launcher.LauncherHolderCreator;
import com.catherine.latte_core.ui.launcher.ScrollLauncherTag;
import com.catherine.latte_core.util.storage.LattePreference;

import java.util.ArrayList;

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> mBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        //必须清空，虽然这里有new ArrayList,但是SingleTask模式不会创新创建FragmentActivity，只是再次运行onCreateView
        //否则就会出现INTEGERS又一样的增加了一倍。detach()将此Fragment从Activity中分离，会销毁其布局，但不会销毁该实例
        INTEGERS.clear();
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        INTEGERS.add(R.mipmap.launcher_06);
        mBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                //下方小球指标
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mBanner = new ConvenientBanner<>(getActivity());
        return mBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }


    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个banner
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否已经登录
        }
    }

}
