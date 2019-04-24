package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseHolder;
import com.pingtiao51.armsmodule.R;

import java.util.List;

public class NormalAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public NormalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.name,item);
    }
}
