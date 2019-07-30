package launcher;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.catherine.latte.ec.R;
import com.catherine.latte.ec.R2;
import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.ui.launcher.ScrollLauncherTag;
import com.catherine.latte_core.util.storage.LattePreference;
import com.catherine.latte_core.util.time.BaseTimerTask;
import com.catherine.latte_core.util.time.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    private Timer mTimer;
    private int mCount = 5;

    @OnClick(R2.id.tv_launcher_timer)
    void OnClickTimeView() {
        //点击跳过广告
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    private void initTimerTask() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }
    //判断是否开启滑动页
    private void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //检查用户是否已经登录APP
            Toast.makeText(getContext(), "检查用户是否已经登录APP", Toast.LENGTH_LONG);
        }
    }
    @Override
    public void onTimer() {
        //倒计时每隔1s 改变mTvTimer ui
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimerTask();
    }
}
