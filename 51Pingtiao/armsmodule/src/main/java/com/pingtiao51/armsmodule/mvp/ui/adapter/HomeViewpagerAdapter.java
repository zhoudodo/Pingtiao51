package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pingtiao51.armsmodule.mvp.ui.fragment.BaseArmFragment;

import java.util.List;

public class HomeViewpagerAdapter extends FragmentPagerAdapter {

    protected Fragment[] mFragments = null;
    protected List<String> mTitles = null;

    public HomeViewpagerAdapter(FragmentManager fm, Fragment[] fg, List<String> t) {
        super(fm);
        mFragments = fg;
        mTitles = t;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment ret;
        if (mFragments != null && i < mFragments.length) {
            ret = mFragments[i];
        } else {
            ret = null;
        }
        return ret;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
