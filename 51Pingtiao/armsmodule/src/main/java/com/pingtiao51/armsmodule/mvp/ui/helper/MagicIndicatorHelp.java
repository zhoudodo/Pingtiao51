package com.pingtiao51.armsmodule.mvp.ui.helper;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.pingtiao51.armsmodule.R;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTextTransitionPagerTitlteView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class MagicIndicatorHelp {

    public static int getResColor(int ids) {
        return ContextCompat.getColor(Utils.getApp(), ids);
    }

    public static void initIndicatorView(final ViewPager mViewPager, net.lucode.hackware.magicindicator.MagicIndicator tabLayout) {
        CommonNavigator commonNavigator = new CommonNavigator(Utils.getApp());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setBackgroundColor(Color.TRANSPARENT);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mViewPager.getAdapter().getCount();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTextTransitionPagerTitlteView colorTransitionPagerTitleView = new ColorTextTransitionPagerTitlteView(context);
                colorTransitionPagerTitleView.setNormalColor(getResColor(R.color.gray_color_6A6A6A));
//                colorTransitionPagerTitleView.setBackgroundColor(Color.WHITE); // IPagerIndicator 指示器 在ColorTransitionPagerTitleView 覆盖下
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                colorTransitionPagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                colorTransitionPagerTitleView.setNormalSp(16);
                colorTransitionPagerTitleView.setSelectSp(18);
                colorTransitionPagerTitleView.setSelectedColor(getResColor(R.color.tv_main_color_FF814D));


                colorTransitionPagerTitleView.setText(mViewPager.getAdapter().getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return getCommonLineIndicator(context,20,2);
            }
        });
        tabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabLayout, mViewPager);
    }

    public static void initMyPingtiaoIndicator(final ViewPager mViewPager, net.lucode.hackware.magicindicator.MagicIndicator tabLayout) {
        CommonNavigator commonNavigator = new CommonNavigator(Utils.getApp());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setBackgroundColor(Color.TRANSPARENT);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mViewPager.getAdapter().getCount();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTextTransitionPagerTitlteView colorTransitionPagerTitleView = new ColorTextTransitionPagerTitlteView(context);
                colorTransitionPagerTitleView.setNormalColor(getResColor(R.color.gray_color_7C7C7C));
//                colorTransitionPagerTitleView.setBackgroundColor(Color.WHITE); // IPagerIndicator 指示器 在ColorTransitionPagerTitleView 覆盖下
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                colorTransitionPagerTitleView.setNormalSp(13);
                colorTransitionPagerTitleView.setSelectSp(15);
                colorTransitionPagerTitleView.setSelectedColor(getResColor(R.color.orange_color_FF7F0D));


                colorTransitionPagerTitleView.setText(mViewPager.getAdapter().getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return getCommonLineIndicator(context,67,2);
            }
        });
        tabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabLayout, mViewPager);
    }

    public static LinePagerIndicator getCommonLineIndicator(Context ctx,int width,int height) {
        LinePagerIndicator indicator = new LinePagerIndicator(ctx);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setLineHeight(AutoSizeUtils.dp2px(Utils.getApp(),height));
        indicator.setLineWidth(AutoSizeUtils.dp2px(Utils.getApp(),width));
        indicator.setRoundRadius(1);
        indicator.getPaint().setColor(getResColor(R.color.tv_main_color_FF814D));
        return indicator;
    }

}