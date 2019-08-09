package com.catherine.latte_core.delegate.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.catherine.latte_core.R;
import com.catherine.latte_core.delegate.LatteDelegate;

public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    //fragment每次回来的时候要重新聚焦
    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //在几秒钟只能需要点击第二次
            if ((System.currentTimeMillis() - mExitTime) > mExitTime) {
                Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                _mActivity.finish();//单activity，退出相当于整个app退出
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;//事件已经被消费，不再向下传递了
        }
        return false;
    }


}
