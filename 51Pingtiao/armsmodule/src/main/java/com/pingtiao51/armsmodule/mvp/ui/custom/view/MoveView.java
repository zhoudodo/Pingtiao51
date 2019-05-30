package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class MoveView extends View {

    /**
     * View的宽高
     */
    private float width;
    private float height;

    /**
     * 触摸点相对于View的坐标
     */
    private float touchX;
    private float touchY;
    /**
     * 状态栏的高度
     */
    int barHeight = 0;
    /**
     * 屏幕的宽高
     */
    private int screenWidth;
    private int screenHeight;

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;
    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;
    public MoveView(Context context) {
        this(context,null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取状态栏高度
        barHeight = BarUtils.getStatusBarHeight();
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(width <= 0 || height <= 0){
            width = right - left;
            height = bottom - top;
        }
    }

    private boolean isClick = false;

    private float rawX = 0;
    private float rawY = 0;
    private long timeSap = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                clearAnimation();
                touchX = event.getX();
                touchY = event.getY();
                rawX = event.getRawX();
                rawY = event.getRawY();
                timeSap = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(event.getRawX() - rawX)<30  &&  Math.abs(event.getRawY() - rawY)<30){
                    isClick = true;
                }else{
                    isClick = false;
                }

                float maxH = (screenHeight- AutoSizeUtils.dp2px(getContext(),50) - barHeight - AutoSizeUtils.dp2px(getContext(),57) - height);
                float vaildValueY = event.getRawY() > screenHeight - AutoSizeUtils.dp2px(getContext(),50) - height? screenHeight - AutoSizeUtils.dp2px(getContext(),50)-height:event.getRawY();
                float nowY = vaildValueY - touchY - barHeight - AutoSizeUtils.dp2px(getContext(),57);
                float nowX = event.getRawX() - touchX;
                nowX = nowX < 0 ? 0 : (nowX + width > screenWidth) ? (screenWidth - width) : nowX;
                nowY = nowY < 0 ? 0 : nowY;
                this.setY(nowY);
                this.setX(nowX);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //这里做动画贴边效果
                float centerX = getX() + width / 2;
                if (centerX > screenWidth / 2) {
                    this.setY(getY());
                    this.setX(screenWidth - width);
                } else {
                    this.setY(getY());
                    this.setX(0);
                }
                invalidate();
                touchX = 0;
                touchY = 0;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(isClick && System.currentTimeMillis() - timeSap < 2000){
                        performClick();
                    }
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY()); // 调用Scroller中存储的值
            postInvalidate();
        }
    }
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = this.getScrollX();
        int scrollY = this.getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 500);
        invalidate();
    }
}
