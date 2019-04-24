package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class VerticalScrollLinearLayout extends LinearLayout {

    private Scroller mScroller;

    public VerticalScrollLinearLayout(Context context) {
        this(context, null);
    }

    public VerticalScrollLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerticalScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //调用此方法滚动到目标位置  
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移  
    public void smoothScrollBy(int dx, int dy) {
//设置mScroller的滚动偏移量  
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果  
    }

    @Override
    public void computeScroll() {
//先判断mScroller滚动是否完成  
        if (mScroller.computeScrollOffset()) {
//这里调用View的scrollTo()完成实际的滚动  
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//必须调用该方法，否则不一定能看到滚动效果  
            postInvalidate();
        }
        super.computeScroll();
    }

}
