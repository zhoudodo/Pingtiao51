package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class PingtiaoXqImgAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public PingtiaoXqImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public final static String ADD_BTN = "add_btn";

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView img= helper.getView(R.id.pingtiao_xq_img);
        if(ADD_BTN.equals(item)) {
            img.setBackground(null);
            GlideProxyHelper.loadImgForRes(img,R.drawable.tianjia_tupian);
        }else{
            img.setBackground(mContext.getResources().getDrawable(R.drawable.tianjia_item_bg));
            if(UrlEncoderUtils.hasUrlEncoded(item)){
                try {
                    item = URLDecoder.decode(item,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            Glide.with(img.getContext()).load(item).into(img);
        }
    }
}
