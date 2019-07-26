package com.catherine.latte_core.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.catherine.latte_core.R;
import com.catherine.latte_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;//缩放比
    private static final int LOADER_OFFSET_SCALE = 10;//偏移量
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoadingStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoadingStyle> styleEnum) {
        showLoading(context,styleEnum.name());
    }
    private static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView loadingIndicatorView = LoadingCreator.create(type, context);
        dialog.setContentView(loadingIndicatorView);
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }
//防止传入applicationContext，此时向webView跳转时会出错，应该Fragment getActivity()，Activity .this
    private static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }
    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog .isShowing()) {
                dialog.cancel();//有回调
               // dialog.dismiss();//仅消失
            }
        }
    }
}
