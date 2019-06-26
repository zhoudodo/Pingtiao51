package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.pojospeical.NewsListInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;

import java.util.List;

public class HomeNewsAdapter extends BaseQuickAdapter<NewsListInterface, BaseViewHolder> {

    public HomeNewsAdapter(int layoutResId, @Nullable List<NewsListInterface> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, NewsListInterface item) {
        GlideProxyHelper.loadImgForUrl(helper.getView(R.id.img), item.getImg());
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.content1,item.getLable1());
        helper.setText(R.id.content2,"");
    }
}
