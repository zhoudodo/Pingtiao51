package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.util.TypedValue;

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;

public class ColorTextTransitionPagerTitlteView extends SimplePagerTitleView {
    private int normalSp = 15;
    private int selectSp = 18;

    public ColorTextTransitionPagerTitlteView(Context context) {
        super(context);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index,totalCount);

        setTextSize(TypedValue.COMPLEX_UNIT_SP,selectSp);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index,totalCount);
        setTextSize(TypedValue.COMPLEX_UNIT_SP,normalSp);
    }

    public void setSelectSp(int sp){
        selectSp = sp;
    }

    public void setNormalSp(int sp){
        normalSp = sp;
    }



}
