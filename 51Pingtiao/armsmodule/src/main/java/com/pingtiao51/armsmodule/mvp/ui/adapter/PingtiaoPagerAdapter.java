package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PingtiaoPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFgs;
    private List<String> mTls;

    public PingtiaoPagerAdapter(FragmentManager fm, List<Fragment> fgs, List<String> tls) {
        super(fm);
        this.mFgs = fgs;
        this.mTls = tls;
    }

    @Override
    public Fragment getItem(int i) {
        return mFgs.get(i);
    }

    @Override
    public int getCount() {
        return mFgs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTls.get(position);
    }
}
