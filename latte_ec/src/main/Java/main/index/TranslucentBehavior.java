package main.index;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.catherine.latte.ec.R;
import com.catherine.latte_core.ui.recycler.RgbValue;


@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private static final int SHOW_SPEED = 3;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255, 124, 2);

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }
//    NestedScrollingParent2 继承于 NestedScrollingParent，主要就是覆写了以下几个方法，增加了一个 type 参数。
    /*boolean onStartNestedScroll (View child, View target, int axes, int type)：
    当子 view （直接或间接）调用startNestedScroll(View, int)时，会回调父控件该方法。
        child
        包裹 target 的父布局的直接子View(该直接子View不一定是发生滑动嵌套的view）
        target
        触发嵌套滑动的 view
        axes
        表示滚动的方向：ViewCompat.SCROLL_AXIS_VERTICAL(垂直方向滚动)ViewCompat.SCROLL_AXIS_HORIZONTAL(水平方向滚动)
        type
        触发滑动事件的类型：其值有 ViewCompat.TYPE_TOUCH  ViewCompat.TYPE_NON_TOUCH
    返回值解释 boolean true：表示父控件接受该嵌套滑动事件，后续嵌套滑动事件就会通知到该父控件
*/
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    /*void onNestedPreScroll (View target, int dx, int dy, int[] consumed, int type)：
        在子View消费滑动事件前，优先响应滑动操作，消费部分或全部滑动距离。
        target
        触发嵌套滑动的 view
        dx
        表示 view 本次 x 方向的滚动的总距离，单位：像素
        dy
        表示 view 本次 y 方向的滚动的总距离，单位：像素
        consumed
        输出：表示父布局消费的水平和垂直距离。
        type
        触发滑动事件的类型：其值有 ViewCompat.TYPE_TOUCH  ViewCompat.TYPE_NON_TOUCH*/
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //每次滑动增加滑动距离
        mDistanceY += dy;
        //toolbar的高度
        final int targetHeight = child.getBottom();

        //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {//已经开始滑了&&距离小于 toolbar 高度
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            //child即toolbar
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        } else if (mDistanceY > targetHeight) {//距离超过时设置为预设颜色
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
    }

    /*void onNestedScrollAccepted (View child, View target, int axes, int type)：
    当onStartNestedScroll返回true时，该方法被回调, 父控件针可以在该方法中对嵌套滑动做一些前期工作。
    覆写该方法时，记得要调用父类实现：super.onNestedScrollAccepted，如果存在父类的话。
    */

    /*void onNestedScroll (View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type)：
    接收子View处理完滑动后的滑动距离信息, 在这里父控件可以选择是否处理剩余的滑动距离。
    如果想要该方法得到回调，先前的onStartNestedScroll(View, View, int, int)必须返回true。
        dxConsumed
        表示 view 消费了 x 方向的距离
        dyConsumed
        表示 view 消费了 y 方向的距离
        dxUnconsumed
        表示 view 剩余未消费 x 方向距离
        dyUnconsumed
        表示 view 剩余未消费 y 方向距离
        offsetInWindow
        可选参数：如果非空，则表示在局部坐标系中，该 view 在滑动事件后与滑动前的偏移量，View实现可以根据该值调整期望的输入坐标追踪
        type
    触发滑动事件的类型：其值有 ViewCompat. TYPE_TOUCH ViewCompat. TYPE_NON_TOUCH
。*/

   // NestedScrollingChild2 接口
 /*1）boolean startNestedScroll (int axes, int type)：
    在子View准备滑动时，会调用该方法；在屏幕触摸事件中，相当于在 ACTION_DOWN 中调用该方法；
    在触碰滑动阶段，内嵌滑动会自动停止，就如同调用了requestDisallowInterceptTouchEvent(boolean)；
    在自编程的滑动中，使用者必须自己显示调用stopNestedScroll(int)来通知嵌套活动终止。
    该方法的主要作用其实就是用来找寻一个支持嵌套滑动的父控件（由内向外找，视图层级的View都有可能成为嵌套滑动父控件，
    只要其回调函数onStartNestedScroll返回true）。调用该方法后，会回调父控件onStartNestedScroll方法。
        axes
        表示滚动的方向：ViewCompat.SCROLL_AXIS_VERTICAL(垂直方向滚动)ViewCompat.SCROLL_AXIS_HORIZONTAL(水平方向滚动)
        type
        触发滑动事件的类型：其值有 ViewCompat. TYPE_TOUCH ViewCompat. TYPE_NON_TOUCH
    返回值解释 boolean true：如果找到了父控件（实现了 NestedScrollingParent2 或NestedScrollingParent）,
                            并且当前 view 使能了嵌套滑动功能（setNestedScrollingEnabled(true)）。*/
/*2）boolean dispatchNestedPreScroll (int dx, int dy, int[] consumed, int[] offsetInWindow, int type)：
        在滑动之前，调用该方法，具体为在屏幕触摸事件中，每一次接收到 ACTION_MOVE 事件时调用。
        该方法为父控件提供了在子View消费滑动事件前，消费部分或全部滑动事件的机会。
        调用该方法后，会回调父控件的onNestedPreScroll方法。
        boolean 	true：表示父控件消费了部分或全部滑动事件*/
/*3）boolean dispatchNestedScroll (int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type)：
        处于拖动状态时，会调用该方法，回调父控件的onNestedScroll方法，传递当前 view 滑动距离详情给到父控件。*/
/*4）void stopNestedScroll (int type)：
        当嵌套滑动停止时，调用该方法，会回调父控件的onStopNestedScroll，通知父控件嵌套滑动结束.
        该方法调用时机为 ACTION_UP 或者 ACTION_CANCEL 事件发生时，且没有惯性滑动（fling）事件。*/
    //supper NestedScrollingChild
/*5）boolean dispatchNestedPreFling (float velocityX, float velocityY)：
        在 view 消费 fling 事件前，将该事件分发给父控件，让父控件决定是否消费掉整个 fling 事件。
        该事件发生时机：在 ACTION_UP 事件发生时，并且存在惯性滚动。
        velocityX 	水平惯性滑动速率，单位：像素/秒  velocityY 	垂直惯性滑动速率，单位：像素/秒
        boolean 	true：父控件消费该 fling 事件*/
/*6）boolean dispatchNestedFling (float velocityX, float velocityY, boolean consumed)：
        分发 fling 事件给父控件。
        boolean 	true：父控件消费该 fling 事件或者对该事件做出了反应*/

}
