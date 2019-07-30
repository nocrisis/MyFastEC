package com.catherine.latte_core.ui.launcher;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bigkoo.convenientbanner.holder.Holder;

public class LauncherHolder implements Holder<Integer> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}
