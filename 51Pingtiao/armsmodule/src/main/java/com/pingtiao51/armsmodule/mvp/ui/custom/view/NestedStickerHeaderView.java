package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.pingtiao51.armsmodule.R;

public class NestedStickerHeaderView extends RelativeLayout implements NestedScrollingParent2 , NestedScrollingChild2 {

    private int mMaxScrollTop = -1;
    private int mOriginBottom = -1;

    private static final String TAG = "NestedScrollView2";

    private View header;
    private RecyclerView recyclerView;

    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    public NestedStickerHeaderView(Context context) {
        super(context);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);

    }

    public NestedStickerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        header = getChildAt(1);
        recyclerView = findViewById(R.id.rv);
    }

    public void setMaxScrollTop(int maxScrollTop) {
        this.mMaxScrollTop = maxScrollTop;
    }

    public void setOriginBottom(int originBottom) {
        this.mOriginBottom = originBottom;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes, int type) {
        //只处理垂直方向的滑动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, @ViewCompat.NestedScrollType int type) {
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
        int dxUnconsumed, int dyUnconsumed, @ViewCompat.NestedScrollType int type) {
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //如果未初始化，则内部自己计算
        if (mMaxScrollTop == -1) {
            mMaxScrollTop = 0;
        }

        if (mOriginBottom == -1) {
            mOriginBottom = recyclerView.getTop();
            //获取recyclerView 上面的所有控件的高度
            /*View header = getChildAt(0);
            LayoutParams params = (LayoutParams) header.getLayoutParams();
            mOriginBottom = params.topMargin + header.getMeasuredHeight() + params.bottomMargin;*/

        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
        @ViewCompat.NestedScrollType int type) {
        LinearLayoutManager linearLayoutManager =
            (LinearLayoutManager) recyclerView.getLayoutManager();
        //recycleview 是否滑到顶部
        boolean isFirstItemVisible =
            linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
//        Log.d("dodo","dy = "+dy +" rv top =" + recyclerView.getTop() +
//                " mMaxScrollTop =" +mMaxScrollTop + "  mOriginBottom" + mOriginBottom );
        //向上滑动，且头部没有滑倒最大高度,此时让header滑动
        if (dy > 0 && recyclerView.getTop() > mMaxScrollTop) {
//            Log.d("dodo", "向上滑动，且头部没有滑倒最大高度,此时让header滑动");
            int maxOffset = recyclerView.getTop() - mMaxScrollTop;
            int offset = Math.min(maxOffset, dy);
            for(int i=0; i<this.getChildCount();i++) {
                if(getChildAt(i) != recyclerView) {
                    getChildAt(i).offsetTopAndBottom(offset * -1);
                }
            }

            recyclerView.layout(recyclerView.getLeft(), recyclerView.getTop() - offset,
                recyclerView.getRight(), recyclerView.getBottom());
            consumed[1] = offset;
        }
        //向下滑动，且recycleview已经滑倒顶部， header没有滑倒原始位置前，让头部滑动
        else if (dy < 0 && isFirstItemVisible && recyclerView.getTop() < mOriginBottom) {
//            Log.d("dodo", "向下滑动，且recycleview已经滑倒顶部， header没有滑倒原始位置前，让头部滑动");
            int offset = Math.min(mOriginBottom - recyclerView.getTop(), dy * -1);
            for(int i=0; i<this.getChildCount();i++) {
                if(getChildAt(i) != recyclerView) {
                    getChildAt(i).offsetTopAndBottom(offset);
                }
            }
            recyclerView.layout(recyclerView.getLeft(), recyclerView.getTop() + offset,
                recyclerView.getRight(), recyclerView.getBottom());
            consumed[1] = -1 * offset;

            //向下滑动，且recycleview已经滑倒顶部， header回到原始位置后还在滑动
        }else if(dy <= 0 && isFirstItemVisible && recyclerView.getTop() >= mOriginBottom){
//            Log.d("dodo", "向下滑动，且recycleview已经滑倒顶部， header回到原始位置后还在滑动");
            int[] offset = new int[2];
            if( startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type)
            && dispatchNestedPreScroll(dx,dy,consumed,offset,type)){

            }

        }

    }

    @Override
    public boolean startNestedScroll(int i, int i1) {
        return  mNestedScrollingChildHelper.startNestedScroll(i, i1);
    }

    @Override
    public void stopNestedScroll(int i) {
        mNestedScrollingChildHelper.stopNestedScroll(i);
    }

    @Override
    public boolean hasNestedScrollingParent(int i) {
        return mNestedScrollingChildHelper.hasNestedScrollingParent(i);
    }

    @Override
    public boolean dispatchNestedScroll(int i, int i1, int i2, int i3, @Nullable int[] ints, int i4) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(i,i1,i2,i3,ints,i4);
    }

    @Override
    public boolean dispatchNestedPreScroll(int i, int i1, @Nullable int[] ints, @Nullable int[] ints1, int i2) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(i,i1,ints,ints1,i2);
    }

}
