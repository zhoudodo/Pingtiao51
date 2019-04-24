package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pingtiao51.armsmodule.R;

import java.util.List;

/**
 * Created by li_yx on 2017/5/17.
 */

public class CtsScrollTextView extends LinearLayout {

    private Context mContext;
    private TextView textViews[] = new TextView[3];

    private LinearLayout llayout;

    private String curText = null;

    /***
     * 每次动画执行时间
     */
    private int mAnimTime = 500;

    /**
     * 停留时间
     */
    private int mStillTime = 1500;

    /***
     * 轮播的string
     */
    private List<String> mTextList;

    /***
     * 当前轮播的索引
     */
    private int currentIndex = 1;

    /***
     * 动画模式
     */
    private int animMode = 0;// 默认向上 0--向上，1--向下

    public final static int ANIM_MODE_UP = 0;
    public final static int ANIM_MODE_DOWN = 1;

    private TranslateAnimation animationDown, animationUp;

    public CtsScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {
        llayout = new LinearLayout(mContext);
        llayout.setOrientation(LinearLayout.VERTICAL);
        this.addView(llayout);

        textViews[0] = addText();
        textViews[1] = addText();
        textViews[2] = addText();
    }

    /***
     * 当界面销毁时
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoScroll();// 防止内存泄漏的操作
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setViewsHeight();
    }

    /***
     * 重新设置VIEW的高度
     */
    private void setViewsHeight() {
        for (TextView tv : textViews) {
            LayoutParams lp = (LayoutParams) tv.getLayoutParams();
            lp.height = getHeight();
            lp.width = getWidth();
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setMaxLines(2);
            tv.setEllipsize(TextUtils.TruncateAt.END);
        }

        LayoutParams lp2 = (LayoutParams) llayout.getLayoutParams();
        lp2.height = getHeight() * (llayout.getChildCount());
        lp2.setMargins(0, -getHeight(), 0, 0);// 使向上偏移一定的高度，用padding,scrollTo都分有问题
        llayout.setLayoutParams(lp2);
    }


    public void setTextList(List<String> mTextList) {
        this.mTextList = mTextList;
    }


    private TextView addText() {
        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setMaxLines(1);
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        llayout.addView(tv);
        return tv;
    }

    /***
     * 设置初始的字
     */
    public void setText(String curText) {
        this.curText = curText;
        textViews[1].setText(curText);
    }

    /***
     * 开始自动滚动
     */
    public void startAutoScroll() {
        if (mTextList == null || mTextList.size() == 0 || mTextList.size() == 1) {
            return;
        }
        // 先停止动画
        stopAutoScroll();
        this.postDelayed(runnable, mStillTime);// 可用runnable来代替hander或者 timer
    }

    /***
     * 停止自动滚动
     */
    public void stopAutoScroll() {
        this.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentIndex = (currentIndex) % mTextList.size();
            switch (animMode) {
                case ANIM_MODE_UP:
                    setTextUpAnim(mTextList.get(currentIndex));
                    break;
                case ANIM_MODE_DOWN:
                    setTextDownAnim(mTextList.get(currentIndex));
                    break;
            }
            currentIndex++;
            CtsScrollTextView.this.postDelayed(runnable, mStillTime + mAnimTime);

        }
    };


    public void setTextUpAnim(String text) {
        this.curText = text;
        textViews[2].setText(text);
        up();// 向上的动画
    }


    public void setTextDownAnim(String text) {
        this.curText = text;
        textViews[0].setText(text);
        down();// 向上的动画
    }


    /***
     * 向上动画
     */
    private void up() {
        llayout.clearAnimation();
        if (animationUp == null)
            animationUp = new TranslateAnimation(0, 0, 0, -getHeight());
        animationUp.setDuration(mAnimTime);
        llayout.startAnimation(animationUp);
        animationUp.setAnimationListener(listener);
    }

    /***
     * 向下动画
     */
    public void down() {
        llayout.clearAnimation();
        if (animationDown == null)
            animationDown = new TranslateAnimation(0, 0, 0, getHeight());
        animationDown.setDuration(mAnimTime);
        llayout.startAnimation(animationDown);
        animationDown.setAnimationListener(listener);
    }

    /***
     * 动画监听，动画完成后，动画恢复，设置文本
     */
    private Animation.AnimationListener listener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation arg0) {
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            setText(curText);
        }
    };


    //-----------------------------------以下是textView要用到一些基本方法-----------------------------------
    public void setGravity(int graty) {
        for (TextView tv : textViews) {
            tv.setGravity(graty);
        }
    }

    public void setTextSize(int dpSize) {
        for (TextView tv : textViews) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
        }
    }

    public void setTextColor(int color) {
        for (TextView tv : textViews) {
            tv.setTextColor(color);
        }
    }

    public void setAnimTime(int mAnimTime) {
        this.mAnimTime = mAnimTime;
    }

    public void setStillTime(int mStillTime) {
        this.mStillTime = mStillTime;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setDuring(int during) {
        this.mAnimTime = during;
    }

    public void setAnimMode(int animMode) {
        this.animMode = animMode;
    }
}

