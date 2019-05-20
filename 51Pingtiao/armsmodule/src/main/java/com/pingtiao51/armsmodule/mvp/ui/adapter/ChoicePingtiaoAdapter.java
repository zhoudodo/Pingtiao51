package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.helputils.SelectorUtil;

import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class ChoicePingtiaoAdapter  extends BaseQuickAdapter<String,BaseViewHolder> {

    public ChoicePingtiaoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name,item);
        GradientDrawable gd =  SelectorUtil.getDrawable(mContext.getResources().getColor(R.color.gray_color_E9E9E9),0,0,AutoSizeUtils.dp2px(mContext,3));
        helper.getView(R.id.name).setBackground(gd);
    }
}
